def lintChecks() {
    sh "echo Installing Lint Checker"
    sh "npm i jslint"
    sh "echo Performing Lint Checks for $COMPONENT"
    sh "node_modules/jslint/bin/jslint.js server.js || true" 
}



def call(COMPONENT) {
    pipeline { 
    agent {
        label 'ws'
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
                sh "echo Static Checks ...."
            }
        }
    }
}}
