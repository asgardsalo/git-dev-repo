
def call() {
    stage ("testing_gcp") {
        sh '''
        export PATH=$PATH:/usr/local/bin
        gcloud config list
        '''
    }
}
