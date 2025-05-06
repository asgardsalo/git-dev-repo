// Groovy script to interact with GCP Console
import resources.templates.services.base-paths

def call() {

    String jenkins_agent = ""

    node(jenkins_agent) {
        
        stage ("testing_gcp") {
            sh '''
            gcloud config list
            '''
        }
    }
    
}