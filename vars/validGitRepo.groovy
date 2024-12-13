
def call() {
    
    stage('Github Connection Testing') {
        steps {
            sh '''
            whoami
            sudo -S bash
            curl https://api.github.com/repos/asgardsalo/git-dev-repo
            '''
        }
    }   
    println "Shared Library Function: validGitRepo executed!"
}
post {
    failure {
        echo "Error"
    }
    always {
        cleanWs()
        }
}