def lintChecks() {
    sh "echo Installing Lint Checker"
    sh "npm i jslint"
    sh "node_modules/jslint/bin/jslint.js server.js || true"
}



def call() {
    pipeline { 
    agent {
        label 'ws'
    }
    stages {
        stage('Lint Checks') {
            steps {
                nodejs.lintChecks()

            }
        }
        stage('Static Code Analysis') {
            steps {
                sh "echo Static Checks ...."
            }
        }
    }
}}
