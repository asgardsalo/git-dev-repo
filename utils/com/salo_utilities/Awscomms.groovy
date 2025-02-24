package com.salo_utilities.utils
import groovy.json.JsonOutput


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

switch(reg-sel) {

    case 'N. Virginia' :
        region= 'us-east-1'
    case 'Ohio' :
        region= 'us-east-2'
    case 'N. Carolina' :
        region= 'us-west-1'
    case 'Oregon' :
        region= 'us-west-2'
    break

    case 'Mumbai' :
        region= 'ap-south-1'
    case 'Singapore' :
        region= 'ap-southeast-1'
    case 'Sydney' :
        region= 'ap-southeast-2'
    case 'Tokyo' :
        region= 'ap-northeast-1'
    break

    case 'Frankfurt' :
        region= 'eu-central-1'
    case 'London' :
        region= 'eu-west-2'
    case 'Stockholm' :
        region= 'eu-north-1'
    break
    
    case 'São Paulo' :
        region= 'sa-east-1'
    break

}
void setRegion(String region) {
    String cmd = "aws configure set region ${region}" 
    String response = sh(script:cmd, returnStdout:true)
    print(response)
}