def call() {
    //export PATH="/usr/local/google-cloud-sdk/bin:$PATH"
    String jenkins_agent = ""

    node(jenkins_agent) {
        
        stage ("testing_gcp") {
           //Baseurl="https://awssalo.signin.aws.amazon.com/console"
            /*sh '''
            
            gcloud config list
            '''*/
            print("/usr/local/google-cloud-sdk/bin:$PATH")
        }
    }
    
}