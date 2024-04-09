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

         stage('Sonar Result') {
            steps {
                    sh "curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/sonar-scanner/sonar-quality-gate? > gate.sh"
                    sh "bash gate.sh ${SONAR_CRED_USR} ${SONAR_CRED_PSW} ${NEXUS_URL} ${COMPONENT}"
                    sh "echo SCAN Looks Good"
                }
        }
        stage('Testing') {
            steps {
                    sh "echo Testing in progress"
            }
        }
    }
}}
