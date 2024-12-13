def call() {
    echo "Validating Python environment"
    sh '''
    python3 --version || exit 1
    '''
}