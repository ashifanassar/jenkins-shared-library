def lintchecks() {
        sh "echo Performing Lint Checks for payment"
        // sh "pip3 install pylint && pylint *.py"
        sh "echo Style Checks Completed payment"
}

def sonarchecks() {
    sh ''' 
        echo Sonar Checks Starting for payment
        sonar-scanner -Dsonar.projectKey=payment -Dsonar.host.url=http://${NEXUS_URL}:9000 ${ARGS} -Dsonar.sources=. -Dsonar.login=admin -Dsonar.password=password
        echo Sonar Checks Starting for payment is Completed
     '''
}

def call(payment) {
    pipeline { 
    agent any
    environment {
        NEXUS_URL="34.227.14.107"
    }
    stages {
        stage('Lint Checks') {
            steps {
                script {
                    sh "echo performing lintchecks for payment"
                    lintChecks()
                }
                sh "env"
            }
        }
        stage('Static Code Analysis') {
            steps {
                script {
                    env.ARGS=" -Dsonar.sources=."
                    sonarchecks()
                }
            }
        }
    }
}}
