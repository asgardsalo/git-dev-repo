#!groovy
//import resources.templates.services.base-paths

def call() {
    stage ("testing_gcp") {
        export PATH='$PATH:/usr/local/google-cloud-sdk/bin'
        echo $PATH
        sh '''
        gcloud config list
        '''
    }
}
