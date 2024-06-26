def call() {
    node {
        git branch: 'main', url: "https://github.com/b57-clouddevops/${COMPONENT}.git"
        common.lintchecks()
        env.ARGS="-Dsonar.sources=."
        common.sonarchecks()
        common.testcases()
        if(env.TAG_NAME != null) {
            common.artifacts()
        }
    }
}


// def call(COMPONENT) {
//     pipeline { 
//     agent any
//     environment {
//         NEXUS_URL="3.83.189.125"
//         SONAR_CRED  = credentials('SONAR_CRED')   // SONAR_CRED_USR , SONAR_CRED_PSW
//     }
//     stages {
//         stage('Lint Checks') {
//             steps {
//                 script {
//                     sh "echo performing lintchecks for $COMPONENT"
//                     lintChecks()
//                 }
//             }
//         }
//         stage('Static Code Analysis') {
//             steps {
//                 script {
//                     env.ARGS=" -Dsonar.sources=."
//                     common.sonarchecks()
//                 }
//             }
//         }

//         stage('Get Sonar Result') {
//             steps {
//                 script {
//                     common.sonarresult()
//                 }
//                 }
//         }
//         stage("Testing") {
//             steps {
//                 script {
//                     common.testcases()
//                     }
//                 }
//             }
//             stage("Checking the Release") {
//             when { 
//                 expression { env.TAG_NAME != null  } 
//             }
//                 steps { 
//                     script {
//                         common.checkrelease()
//                     }
//                 }
//             }

//             stage("Making Artifact") {
//             when { 
//                 expression { env.TAG_NAME != null  } 
//             }
//                 steps {
//                     sh ''' 
//                         npm install 
//                         zip -r ${COMPONENT}-${TAG_NAME}.zip node_modules server.js 
//                     '''
//                 }
//             }

//             // Should Only Run Against A Tag
//             stage("Publishing Artifact") {
//             when { 
//                 expression { env.TAG_NAME != null  } 
//             }
//                 steps {
//                     sh '''
//                         echo Publishing Artifacts
//                         curl -f -v -u admin:password --upload-file ${COMPONENT}-${TAG_NAME}.zip http://172.31.43.143:8081/repository/${COMPONENT}/${COMPONENT}-${TAG_NAME}.zip
//                     '''

//                 }
//             }
//         }
//     }
// }