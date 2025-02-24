package com.salo_utilities.utils
import groovy.json.JsonOutput

void setProject(String project) {
    sh(script: "gcloud config set project ${project}", redturnStatus:true, returnStdout: true)
}