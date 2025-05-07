def call (String regiSel) {

    stage('Select AWS Region') {
 
        switch (params.region) {
            case 'N. Virginia':
                regiSel='us-east-1'
            break
            case 'Ohio':
                regiSel='us-east-2'
            break
            case 'N. Carolina':
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
            default:
                echo "Region not recognized: ${params.region}"
                error("Invalid region specified.")
        }
    }

    stage('Export Path') {
        sh '''
        export PATH=$PATH:/usr/local/bin
        aws configure set region "${regiSel}"
        aws configure list
        '''
        /*sh "aws configure set region ${regiSel}"
        sh "aws configure list"
        sh "aws configure set region ${regiSel}"
        sh "aws configure list"*/
    }
}

