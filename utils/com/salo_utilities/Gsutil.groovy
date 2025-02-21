package com.salo_utilities.utils
import groovy.json.JsonOutput

void uploadToBucket(String bucket, String artifactID) {
    String cmd = "gsutil cp ${artifactID} ${bucket}"
    String response = sh(script:cmd, returnStdout:true)
    print(response)
}