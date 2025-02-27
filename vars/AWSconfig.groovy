#!groovy 

def call () {
String regiSel = ""
 String jenkins_agent = ""

    node(jenkins_agent) {

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
}
