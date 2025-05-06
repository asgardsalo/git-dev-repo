#!groovy
//import resources.templates.services.base-paths

def call() {
    stage ("testing_gcp") {
        sh '''
        gcloud config list
        '''
    }
}
