
def call() {
    
    stage('Github Connection Testing') {
        sh '''
        curl https://api.github.com/repos/asgardsalo/git-dev-repo
        curl https://api.github.com/repos/asgardsalo/web-app
        curl https://api.github.com/repos/asgardsalo/git-aws-jenkins
        curl https://api.github.com/repos/asgardsalo/azure-git-repo
        curl https://api.github.com/repos/asgardsalo/bruno_collections
        '''
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
