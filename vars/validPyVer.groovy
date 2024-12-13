
def PYTHON_VENV = "${WORKSPACE}/venv"
    
    stage ('Setup Environment and Validate Python Version') {
            echo "Setting up and validating Python environment"
            sh '''
            python3 -m venv $PYTHON_VENV
            $PYTHON_VENV/bin/python -V
            '''
            echo "Virtual environment path: ${WORKSPACE}/venv"
    }
    post {
    failure {
        echo "Error"
    }
    always {
        cleanWs()
    }
}