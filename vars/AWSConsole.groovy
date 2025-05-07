#!groovy

import rscs.templates.services.basepaths

def call() {

    String jenkins_agent = ""

    node(jenkins_agent) {
        
        stage ("testing_aws") {
            //export PATH=$PATH:/usr/local/bin
            sh '''
            
            aws ec2 describe-vpcs --output table
            aws ec2 describe-instances --output table
            aws s3 ls
            aws iam list-users --output table
            '''
        }
    }
    
}

