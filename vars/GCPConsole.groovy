#!groovy
import resources.templates.services.base_paths

def call() {
    stage ("testing_gcp") {
        sh '''
        gcloud config list
        '''
    }
}
