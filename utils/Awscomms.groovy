package utils

//---------Buckets------//

void uploadToBucket(String bucket, String filenameID) {
    String cmd = "aws s3 cp ${filenameID} s3://${bucket}/${filenameID}" 
    String response = sh(script:cmd, returnStdout:true)
    print(response)
}
//----Download from Bucket------//

void downloadFromBucket(String bucket, String filenameID) {
    String cmd = "aws s3 cp s3://${bucket}/${filenameID} ${filenameID}" 
    String response = sh(script:cmd, returnStdout:true)
    print(response)
}

//---Delete files------//

void deleteFromBucket(String bucket, String filenameID) {
    String cmd = "aws s3 rm s3://${bucket}/${filenameID}" 
    String response = sh(script:cmd, returnStdout:true)
    print(response)
}

//---Download Speed------//

void downloadFromBucket(String bucket, String filenameID) {
    String cmd = "aws configure set default.s3.max_concurrent_request 10" 
    String response = sh(script:cmd, returnStdout:true)
    print(response)
}


//---AWS Configuration------//

void setRegion(String region) {
    String cmd = "aws configure set region ${region}" 
    String response = sh(script:cmd, returnStdout:true)
    print(response)
}