def call() {

  stage ("testing_aws") {
    sh '''
        export PATH=$PATH:/usr/local/bin
        aws ec2 describe-vpcs --output table
        aws ec2 describe-instances --output table
        aws s3 ls
        aws iam list-users --output table
        '''
    }
 }

