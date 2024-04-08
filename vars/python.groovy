def lintChecks() {
        sh "echo Performing Lint Checks for $COMPONENT"
        // sh "pip3 install pylint && pylint *.py"
        sh "echo Style Checks Completed $COMPONENT"
}

def call(COMPONENT) {
    pipeline { 
    agent any
    stages {
        stage('Lint Checks') {
            steps {
                script {
                    sh "echo performing lintchecks for ${COMPONENT}"
                    lintChecks()
                }
                sh "env"
            }
        }
        stage('Static Code Analysis') {
            steps {
                sh "echo static checks"
            }
        }
    }
}}