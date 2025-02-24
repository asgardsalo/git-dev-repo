//import utils.Awscomms
 def call (String availabilityzone) {
    switch(availabilityzone) {
        case 'N.Virginia' :
            region= 'us-east-1'
        case 'Ohio' :
            region= 'us-east-2'
        case 'N.Carolina' :
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
    print(region)

    stage ("exportpath") {
        Baseurl="https://awssalo.signin.aws.amazon.com/console"
        sh '''
            export PATH=$PATH:/usr/local/bin
        '''
    }
    stage("aws_configure") {
        echo ${PATH}
        //sh "aws configure set region ${region}"
        echo $AWS_DEFAULT_REGION
        }
    }
    
    