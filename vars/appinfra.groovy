def call() {
    properties([
        parameters([
            choice(name: 'ENV', choices: ['dev', 'prod'], description: 'Environment'),
            choice(name: 'ACTION', choices: ['apply', 'destroy'], description: 'Action'),
            string(name: 'APP_VERSION', defaultValue: '000', description: 'Enter Version To Be Deployed')                            
        ]),
    ])
    node {
        ansiColor('xterm') {
            git branch: 'main', url: "https://github.com/ashifanassar/${COMPONENT}.git"
            stage('Terraform Init') {
                sh ''' 
                    cd mutable-infra
                    terrafile -f env-${ENV}/Terrafile
                    terraform init -reconfigure --backend-config=env-${ENV}/${ENV}-backend.tfvars
                ''' 
            }
            stage('Terraform Plan') {
                sh ''' 
                    cd mutable-infra
                    terraform plan  --var-file=env-${ENV}/${ENV}.tfvars -var APP_VERSION=${APP_VERSION}
                ''' 
            }
            stage('Terraform Action') {
                sh ''' 
                    cd mutable-infra
                    terraform ${ACTION} -auto-approve --var-file=env-${ENV}/${ENV}.tfvars -var APP_VERSION=${APP_VERSION}
                ''' 
            }
        }
    }
}
