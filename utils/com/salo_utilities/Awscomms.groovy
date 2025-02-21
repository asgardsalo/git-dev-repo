package com.salo_utilities.utils
import groovy.json.JsonOutput


//---------Buckets------

void uploadToBucket(String bucket, String filenameID) {
    String cmd = "aws s3 cp ${filenameID} s3://${bucket}/${filenameID}" 
    String response = sh(script:cmd, returnStdout:true)
    print(response)
}
//----Download from Bucket

void downloadFromBucket(String bucket, String filenameID) {
    String cmd = "aws s3 cp s3://${bucket}/${filenameID} ${filenameID}" 
    String response = sh(script:cmd, returnStdout:true)
    print(response)
}

//---Delete files:

void downloadFromBucket(String bucket, String filenameID) {
    String cmd = "aws s3 rm s3://${bucket}/${filenameID}" 
    String response = sh(script:cmd, returnStdout:true)
    print(response)
}


