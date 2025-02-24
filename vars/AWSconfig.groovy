//import utils.Awscomms
 def call (String availabilityzone) {
    switch(availabilityzone) {
        case 'N.Virginia' :
            regi-sel= 'us-east-1'
        case 'Ohio' :
            regi-sel= 'us-east-2'
        case 'N.Carolina' :
            regi-sel= 'us-west-1'
        case 'Oregon' :
            regi-sel= 'us-west-2'
        break

        case 'Mumbai' :
            regi-sel= 'ap-south-1'
        case 'Singapore' :
            regi-sel= 'ap-southeast-1'
        case 'Sydney' :
            regi-sel= 'ap-southeast-2'
        case 'Tokyo' :
            regi-sel= 'ap-northeast-1'
        break

        case 'Frankfurt' :
            regi-sel= 'eu-central-1'
        case 'London' :
            regi-sel= 'eu-west-2'
        case 'Stockholm' :
            regi-sel= 'eu-north-1'
        break
    
        case 'São Paulo' :
            regi-sel= 'sa-east-1'
        break
    }
    print(regi-sel)

    stage ("exportpath") {
        Baseurl="https://awssalo.signin.aws.amazon.com/console"
        sh '''
            export PATH=$PATH:/usr/local/bin
        '''
    }
    stage("aws_configure") {
        echo PATH
        //sh "aws configure set region ${region}"
        echo regi-sel
        }
    }
    
    