import utils.Awscomms
 def call(String region)
 

void setRegion(String region) {
    String cmd = "aws configure set region ${region}" 
    String response = sh(script:cmd, returnStdout:true)
    print(response)
}