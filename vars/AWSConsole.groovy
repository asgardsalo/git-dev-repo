def call() {
//String gcloudpath="def gcloudpath= export PATH="/usr/local/google-cloud-sdk/bin:$PATH""
//gcloud config list
String awspath=""

    String jenkins_agent = ""

    node(jenkins_agent) {
        
        stage ("testing_aws") {
           Baseurl="https://awssalo.signin.aws.amazon.com/console"
            sh '''
            export PATH=$PATH:/usr/local/bin
            
            aws ec2 describe-vpcs --output table
            aws ec2 describe-instances --output table
            aws s3 ls
            aws s3 
            aws iam list-users --output table
            '''
        }
    }
    
}

