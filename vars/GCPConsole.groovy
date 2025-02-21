def call() {
    
    String jenkins_agent = ""

    node(jenkins_agent) {
        
        stage ("testing_gcp") {
            sh '''
            export PATH="/usr/local/google-cloud-sdk/bin:$PATH"
            '''
        }
    }
    
}