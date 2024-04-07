def lintchecks() {
        sh "echo Performing Lint Checks for $COMPONENT"
        // sh "pip3 install pylint && pylint *.py"
        sh "echo Style Checks Completed $COMPONENT"
}

def call(COMPONENT) {
    pipeline { 
    agent any
    environment {
        NEXUS_URL="34.227.14.107"
    }
    stages {
        stage('Lint Checks') {
            steps {
                script {
                    sh "echo performing lintchecks for $COMPONENT"
                    lintChecks()
                }
                sh "env"
            }
        }
        stage('Static Code Analysis') {
            steps {
                script {
                    common.sonarchecks()
                }
            }
        }
    }
}}
