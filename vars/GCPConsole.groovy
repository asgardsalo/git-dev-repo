def call() {

    String jenkins_agent = ""

    node(jenkins_agent) {
        
        stage ("testing_aws") {
           //Baseurl="https://awssalo.signin.aws.amazon.com/console"
            sh '''
            export PATH="/usr/local/google-cloud-sdk/bin:$PATH"
            gcloud config list
            '''
        }
    }
    
}