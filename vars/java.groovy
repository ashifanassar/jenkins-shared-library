def lintChecks() {
    sh "echo Performing Lint Checks for $COMPONENT"
    //sh "mvn checkstyle:check || true"     // Uncomment this if you want to perform lintChecks
    sh "echo Lint Checks Completed for $COMPONENT"
}


def call(COMPONENT) {
    pipeline { 
    agent any
    environment {
        NEXUS_URL="172.31.80.115"
    }
    stages {
        stage('Lint Checks') {
            steps {
                script {
                    sh "echo performing lintchecks for $COMPONENT"
                    lintChecks()
                }
            }
        }
        stage('compile the java script')
            steps {
                sh "mvn clean compile"
                sh "ls -lt target/"
            }
        stage('Static Code Analysis') {
            steps {
                sh "echo static checks"
                env.ARGS="-Dsonar.java.binaries=./target/"
                common.sonarchecks()
                }
            }
        }
    }
}