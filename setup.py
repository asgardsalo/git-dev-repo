pipeline {
    agent any
    environment {
        PYTHON_VENV = "${WORKSPACE}/venv"
    }
    stages {
        stage('Setup Environment') {
            steps {
                echo "Setting up Python virtual environment"
                sh '''
                python3 -m venv $PYTHON_VENV
                $PYTHON_VENV/bin/pip install -r requirements.txt
                $PYTHON_VENV/bin/python -V
                '''
            }
        }
        stage('Validate Python Version') {
            steps {
                script {
                    echo "Checking Python version..."
                    sh '''
                    python3 --version || echo "Python 3 is not installed"
                    '''
                }
            }
        }
    }
}