import resources.templates.services.basePaths


def call() {

    String jenkins_agent = ""

    node(jenkins_agent) {
        
        stage ("testing_aws") {
            sh '''
            aws ec2 describe-vpcs --output table
            aws ec2 describe-instances --output table
            aws s3 ls
            aws iam list-users --output table
            '''
        }
    }
    
}

