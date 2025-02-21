def call() {
    //
    String jenkins_agent = ""

    node(jenkins_agent) {
        
        stage ("testing_gcp") {
           //Baseurl="https://awssalo.signin.aws.amazon.com/console"
            sh '''
            export PATH="/usr/local/google-cloud-sdk/bin:$PATH"
            '''
            print("$PATH")
            print(sh(script:"gcloud config list", redturnStatus:true, returnStdout: true))
        }
    }
    
}