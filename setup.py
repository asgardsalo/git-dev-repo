pipeline {
    agent any
    stages {
        stage('Setup Environment') {
            steps {
                echo "Setting up Python virtual environment"
                sh '''
                python3 -m venv $PYTHON_VENV
                source $PYTHON_VENV/bin/activate
                pip install -r requirements.txt
                python -V
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