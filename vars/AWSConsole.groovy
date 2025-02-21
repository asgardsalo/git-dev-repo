def call() {
String gcloudpath="export PATH=$PATH:/usr/local/bin"
String awspath="export PATH="/usr/local/google-cloud-sdk/bin:$PATH""
/*
gcloudpath='export PATH=$PATH:/usr/local/bin'
            awspath='export PATH="/usr/local/google-cloud-sdk/bin:$PATH"'
            */
    String jenkins_agent = ""

    node(jenkins_agent) {
        
        stage ("testing_aws") {
           Baseurl="https://awssalo.signin.aws.amazon.com/console"
            sh '''
            
            aws ec2 describe-vpcs --output table
            aws ec2 describe-instances --output table
            aws s3 ls
            aws s3 
            aws iam list-users --output table
            '''
        }
    }
    
}

