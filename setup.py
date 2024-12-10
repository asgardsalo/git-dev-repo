pipeline {
    agent any

    environment {
        PYTHON_VENV = "${WORKSPACE}/venv"
    }
    echo "${WORKSPACE}/venv"
    echo "whoami"
    
    stages {
        stage('Setup Environment and Validate Python Version') {
            steps {
                echo "Setting up and validating Python environment"
                sh '''
                python3 -m venv $PYTHON_VENV
                $PYTHON_VENV/bin/python -V
                '''
                echo "Virtual environment path: ${WORKSPACE}/venv"
            }
        }
    }
}