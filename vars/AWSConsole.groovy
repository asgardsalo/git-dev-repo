def call() {
String gcloudpath=""
String awspath=""
/*
gcloudpath='export PATH=$PATH:/usr/local/bin'
            awspath='export PATH="/usr/local/google-cloud-sdk/bin:$PATH"'
            */
    String jenkins_agent = ""

    node(jenkins_agent) {
        
        stage ("testing_aws") {
           Baseurl="https://awssalo.signin.aws.amazon.com/console"
            sh '''
            def awspath= export PATH=$PATH:/usr/local/bin
            def gcloudpath= export PATH="/usr/local/google-cloud-sdk/bin:$PATH"
            gcloud config list
            aws ec2 describe-vpcs --output table
            aws ec2 describe-instances --output table
            aws s3 ls
            aws s3 
            aws iam list-users --output table
            '''
        }
    }
    
}

