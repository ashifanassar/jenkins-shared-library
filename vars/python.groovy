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






// def lintChecks() {
//         sh "echo Performing Lint Checks for $COMPONENT"
//         // sh "pip3 install pylint && pylint *.py"
//         sh "echo Style Checks Completed $COMPONENT"
// }

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
//                     sh "echo performing lintchecks for ${COMPONENT}"
//                     lintChecks()
//                 }
//                 sh "env"
//             }
//         }
//     stage('Static Code Analysis') {
//         steps {
//             script {
//                 env.ARGS=" -Dsonar.sources=."
//                 common.sonarchecks()
//                     }
//                 }
//         }
//     stage('Get Sonar Result') {
//         steps {
//             script {
//                 common.sonarresult()
//                 }
//             }
//         }
//     stage('Testing') {
//         steps {
//             script {
//                 common.testcases()
//                 }
//             }
//         }
//         }
//     }
// }