def lintChecks() {
    sh "echo Performing Lint Checks for $COMPONENT"
    //sh "mvn checkstyle:check || true"     // Uncomment this if you want to perform lintChecks
    sh "echo Lint Checks Completed for $COMPONENT"
}


def call(COMPONENT) {
    pipeline { 
    agent any
    environment {
        NEXUS_URL="34.227.14.107"
        SONAR_CRED  = credentials('SONAR_CRED')   // SONAR_CRED_USR , SONAR_CRED_PSW
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
        stage('compile the java script') {
            steps {
                sh "mvn clean compile"
                sh "ls -lt target/"
            }
        }
        stage('Static Code Analysis') {
            steps {
                    script {
                        env.ARGS="-Dsonar.java.binaries=./target/"
                        common.sonarchecks()
                    }
                }
            }
        }
    }
}