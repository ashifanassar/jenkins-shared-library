def lintchecks() {
    sh "echo Performing Lint Checks for $COMPONENT"
    //sh "mvn checkstyle:check || true"     // Uncomment this if you want to perform lintChecks
    sh "echo Lint Checks Completed for $COMPONENT"
}

def sonarchecks() {
    sh ''' 
        echo Sonar Checks Starting for $COMPONENT
        sonar-scanner -Dsonar.projectKey=${COMPONENT} -Dsonar.host.url=http://${NEXUS_URL}:9000 ${ARGS} -Dsonar.sources=. -Dsonar.login=admin -Dsonar.password=password
        echo Sonar Checks Starting for $COMPONENT is Completed
     '''
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