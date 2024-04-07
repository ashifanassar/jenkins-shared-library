def sonarchecks() {
    sh ''' 
        echo Sonar Checks Starting for $COMPONENT
        sonar-scanner -Dsonar.projectKey=${COMPONENT} -Dsonar.host.url=http://${NEXUS_URL}:9000 ${ARGS} -Dsonar.sources=. -Dsonar.login=admin -Dsonar.password=password
        echo Sonar Checks Starting for $COMPONENT is Completed
     '''
}