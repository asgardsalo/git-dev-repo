#!groovy 

def call (String regiSel) {

    stage('Export Path') {
        steps {
            script {
                sh "/usr/local/bin/aws configure set region ${regiSel}"
                sh "/usr/local/bin/aws configure list"
            }
        }
    }
    
    post {
        always {
        cleanWs()
        }
    }
}

