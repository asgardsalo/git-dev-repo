
//def PYTHON_VENV = "${WORKSPACE}/venv"
    
    stage ('Validate Python Version') {
            
        echo "Validating Python environment"
        sh '''
        echo "python3 -h"
        '''
    }
    post {
    failure {
        echo "Error"
    }
    always {
        cleanWs()
    }
}