pipeline {
    agent any
    environment {
        PYTHON_VENV = "${WORKSPACE}/venv"
    }
    stages {
        stage('Setup Environment and Validate Python Version') {
            steps {
                echo "Setting up and validating Python environment"
                sh '''
                python3 -m venv $PYTHON_VENV
                $PYTHON_VENV/bin/python -V
                '''
            }
        }
    }
}