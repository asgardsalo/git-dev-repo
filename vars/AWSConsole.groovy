def call() {

    String jenkins_agent = ""

    node(jenkins_agent) {
        
        stage ("testing_aws") {
            Baseurl="https://awssalo.signin.aws.amazon.com/console"
            withCredentials('jenkins-aws')
            sh '''
            aws ec2 describe-vpcs --output table
            aws ec2 describe-instances --output table
            aws s3 ls
            aws iam list-users --output table
            '''

        }
    }
    
}

