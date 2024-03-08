void setProject(String project) {
    sh(script: "gcloud config set project ${project}", redturnStatus:true, returnStdout: true)
}