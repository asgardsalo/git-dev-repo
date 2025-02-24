//import utils.Awscomms

def regiSel = ""

 def call (String availabilityzone, String regiSel) {
    case 'N.Virginia':
        regiSel = 'us-east-1'
    break
    case 'Ohio':
        regiSel = 'us-east-2'
    break
    case 'N.Carolina':
        regiSel = 'us-west-1'
    break
    case 'Oregon':
        regiSel = 'us-west-2'
    break
    case 'Mumbai':
        regiSel = 'ap-south-1'
    break
    case 'Singapore':
        regiSel = 'ap-southeast-1'
    break
    case 'Sydney':
        regiSel = 'ap-southeast-2'
    break
    case 'Tokyo':
        regiSel = 'ap-northeast-1'
    break
    case 'Frankfurt':
        regiSel = 'eu-central-1'
    break
    case 'London':
        regiSel = 'eu-west-2'
    break
    case 'Stockholm':
        regiSel = 'eu-north-1'
    break
    case 'São Paulo':
        regiSel = 'sa-east-1'
    break
    }

    print(regiSel)

    stage ("exportpath") {
        Baseurl="https://awssalo.signin.aws.amazon.com/console"
        sh '''
            export PATH=$PATH:/usr/local/bin
        '''
    }
    stage("aws_configure") {
        echo "PATH: ${env.PATH}"
        echo "Region selected: ${regiSel}"
        //sh "aws configure set region ${region}"
        }
    }
    
    