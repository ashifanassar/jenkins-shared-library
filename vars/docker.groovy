def call() {
    node {
        git branch: 'main', url: "https://github.com/ashifanassar/${COMPONENT}.git"
        common.lintchecks()
        env.ARGS="-Dsonar.java.binaries=./target/"
        // common.sonarchecks()
        common.testcases()
        if(env.TAG_NAME != null) {
            stage('Generating & Publishing Artifacts') {
            if(env.APPTYPE == "nodejs") {
                    sh "echo Generating Node Artifacts"
                    sh "npm install"               
                }         
            else if(env.APPTYPE == "python") {
                    sh "echo Generating Artifacts"
                    sh "zip -r ${COMPONENT}-${TAG_NAME}.zip *.py *.ini requirements.txt"        
                }
            else if(env.APPTYPE == "maven") {
                    sh "mvn clean package"
                    sh "mv target/${COMPONENT}-1.0.jar ${COMPONENT}.jar"
                    sh "zip -r ${COMPONENT}-${TAG_NAME}.zip  ${COMPONENT}.jar"      
                }
            else if(env.APPTYPE == "angularjs") {
                    sh "cd static/"
                    sh "zip -r ../${COMPONENT}-${TAG_NAME}.zip *"
                }
            else { sh "echo Selected Component Type Doesnt Exist" }                        
        }
            sh "echo Downloading the pen key file for DB Connectivity"
            sh "wget https://truststore.pki.rds.amazonaws.com/global/global-bundle.pem"
            sh "docker build -t 654654573754.dkr.ecr.us-east-1.amazonaws.com/${COMPONENT}:${TAG_NAME} ."
            sh "docker push 654654573754.dkr.ecr.us-east-1.amazonaws.com/${COMPONENT}:${TAG_NAME}"
        }
    }
}