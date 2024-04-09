def sonarchecks() {
    sh ''' 
        echo Sonar Checks Starting for $COMPONENT
        sonar-scanner -Dsonar.projectKey=${COMPONENT} -Dsonar.host.url=http://${NEXUS_URL}:9000 ${ARGS} -Dsonar.login=${SONAR_CRED_USR} -Dsonar.password=${SONAR_CRED_PSW}
        echo Sonar Checks Starting for $COMPONENT is Completed
     '''
}


def sonarresult() {
    sh '''
        curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/sonar-scanner/sonar-quality-gate? > gate.sh
        bash -x gate.sh ${SONAR_CRED_USR} ${SONAR_CRED_PSW} ${NEXUS_URL} ${COMPONENT}
        echo SCAN Looks Good
    '''
        }

def testcases() {
        stage('test cases') {
        def stages = [:]

        stages["Unit Testing"] = {
            echo "Unit Testing In Progress"
            echo "Unit Testing Is Completed"
        }

        stages["Integration Testing"] = {
            echo "Integration Testing In Progress"
            echo "Integration Testing Is Completed"
        }

        stages["Functional Testing"] = {
            echo "Functional Testing In Progress"
            echo "InteFunctionalration Testing Is Completed"
        }

        parallel(stages)
    }
}