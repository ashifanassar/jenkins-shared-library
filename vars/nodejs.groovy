def lintChecks() {
    sh "echo Installing Lint Checker"
    sh "npm i jslint"
    sh "node_modules/jslint/bin/jslint.js server.js || true"
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
        stage('Static Code Analysis') {
            steps {
                script {
                    env.ARGS=" -Dsonar.sources=."
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
        stage("Testing") {
            steps {
                script {
                    common.testcases()
                    }
                }
            }

        stage("Making artifact") {
        when { 
            expression { env.TAG_NAME != null  } 
        }
            steps {
                sh "env"
                sh "echo preparing artifacts"
            }
        }
        stage("Publishing artifact") {
            steps {
                sh "echo Publishing artifacts"
                }
            }
        }
    }
}