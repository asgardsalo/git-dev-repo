package com.salo_utilities.utils
import groovy.json.JsonOutput

void setProject(String project) {
    sh(script: "gcloud config set project ${project}", redturnStatus:true, returnStdout: true)
}

void getProject() {
    String cmd = 'gcloud config get-value project'
    print(sh(script:cmd, returnStdout: ))
}

void describeProject() {
    String cmd = 'gcloud compute project-info describe'
    sh(script:cmd)
}