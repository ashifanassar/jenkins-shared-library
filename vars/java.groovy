def lintChecks() {
    sh "echo Performing Lint Checks for $COMPONENT"
    //sh "mvn checkstyle:check || true"     // Uncomment this if you want to perform lintChecks
    sh "echo Lint Checks Completed for $COMPONENT"
}


def call(COMPONENT) {
    pipeline { 
    agent any
    environment {
        NEXUS_URL="3.83.189.125"
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

        stage('Get Sonar Result') {
            steps {
                script {
                    common.sonarresult()
                }
                }
        }
        stage('Testing') {
            steps {
                script {
                    common.testcases()
            }
            }
        }
        }
    }
}