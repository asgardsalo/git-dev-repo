
def call() {
    
    stage('Github Connection Testing') {
        sh '''
        //curl https://api.github.com/repos/asgardsalo/git-dev-repo
        curl -s -u https://api.github.com/user/repos?per_page=100 | grep '"full_name"' | cut -d '"' -f 4
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
