
//def PYTHON_VENV = "${WORKSPACE}/venv"
    
    stage ('Validate Python Version') {
            
            steps {
                script {
                    echo "Validating Python environment"
                    sh '''
                    sudo bash
                    echo "python3 -h"
                    '''
                }
            }
    }
    post {
    failure {
        echo "Error"
    }
    always {
        cleanWs()
    }
}