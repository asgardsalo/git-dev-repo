//import utils.Awscomms
 def call (String availabilityzone) {
    switch(availabilityzone) {
        case 'N.Virginia' :
            reg-sel= 'us-east-1'
        case 'Ohio' :
            reg-sel= 'us-east-2'
        case 'N.Carolina' :
            reg-sel= 'us-west-1'
        case 'Oregon' :
            reg-sel= 'us-west-2'
        break

        case 'Mumbai' :
            reg-sel= 'ap-south-1'
        case 'Singapore' :
            reg-sel= 'ap-southeast-1'
        case 'Sydney' :
            reg-sel= 'ap-southeast-2'
        case 'Tokyo' :
            reg-sel= 'ap-northeast-1'
        break

        case 'Frankfurt' :
            reg-sel= 'eu-central-1'
        case 'London' :
            reg-sel= 'eu-west-2'
        case 'Stockholm' :
            reg-sel= 'eu-north-1'
        break
    
        case 'São Paulo' :
            reg-sel= 'sa-east-1'
        break
    }
    print(reg-sel)

    stage ("exportpath") {
        Baseurl="https://awssalo.signin.aws.amazon.com/console"
        sh '''
            export PATH=$PATH:/usr/local/bin
        '''
    }
    stage("aws_configure") {
        echo PATH
        //sh "aws configure set region ${region}"
        echo reg-sel
        }
    }
    
    