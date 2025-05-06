#!groovy
import base_paths

def call() {
    stage ("testing_gcp") {
        sh '''
        gcloud config list
        '''
    }
}
