pipeline {
    agent any

    environment {
        PYTHON_SCRIPT = 'create_snow_ticket.py'
    }

    stages {
        stage('Checkout Code') {
            steps {
                // Checkout the Python script from a Git repository
                git url: 'https://your-repo-url.git', branch: 'main'
            }
        }
        stage('Install Dependencies') {
            steps {
                // Install Python dependencies
                sh 'pip install requests'
            }
        }
        stage('Execute Python Script') {
            steps {
                // Execute the Python script with arguments
                sh """
                python ${env.PYTHON_SCRIPT} \
                    --requestor 'john.doe@example.com' \
                    --bu_team 'IT Operations' \
                    --cmdb 'Server_XYZ' \
                    --implementor_group 'Infrastructure Team' \
                    --implementor_name 'Jane Smith'
                """
            }
        }
    }
}