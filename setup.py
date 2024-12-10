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
                python3 --version
                '''
            }
        }
    }
}