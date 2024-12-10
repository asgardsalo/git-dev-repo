pipeline {
    agent any
    stages {
        stage('Github Connection Testing') {
            steps {
                sh '''
                whoami
                sudo -S bash
                curl https://api.github.com/repos/asgardsalo/git-dev-repo
                '''
            }
        }
    }
}