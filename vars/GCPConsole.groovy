
def call() {
    stage ("testing_gcp") {
        sh '''
        gcloud config list
        '''
    }
}
