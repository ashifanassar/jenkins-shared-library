def lintChecks() {
    sh "echo Installing Lint Checker"
    sh "npm i jslint"
    sh "node_modules/jslint/bin/jslint.js server.js || true"
}

def sonarchecks() {
    sh '''
        echo sonar check start on the $COMPONENT
        sonar-scanner -Dsonar.projectKey=${COMPONENT} -Dsonar.host.url=http://34.227.14.107:9000 -Dsonar.sources=. -Dsonar.login=admin -Dsonar.password=password -Dsonar.verbose=true
        echo sonar checks starting on the $COMPONENT is completed
    '''
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
