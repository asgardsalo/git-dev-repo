
def call() {
    /*export PATH=$PATH:/usr/local/bin
        gcloud config list*/
    
    stage ("testing_gcp") {
        sh '''
        which gcloud
        '''
    }
}
