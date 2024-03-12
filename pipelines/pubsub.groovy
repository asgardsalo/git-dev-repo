#!groovy

def pod_acct
def impersonacct
def message
def pod_tf_sa

pipeline {
    agent any
    
    parameters { 
        choice (name: 'region', choices: ['US', 'CA', 'AU', 'UK', 'IN', 'LA', 'EU' ], description: 'Select the GCP Region where to perform the task ')
    }
    
//    stage ('Init Pubsub') {
//        pubsub
//    }
stages {
        stage("Choose Service Account / Pod Template ") {
            steps {
                echo "Selected region: ${params.region}"
                script {
                    switch(params.region) {
                        case 'US':
                            pod_acct = 'build-pod-tf-apps-api-dp-us-npe'
                            pod_tf_sa='tf-apps-api-dp-us-npe@apps-api-dp-us-npe-ae82.iam.gserviceaccount.com'
                            impersonacct = 'resource-publisher@apps-api-dp-us-npe-ae82.iam.gserviceaccount.com'
                        break
                        case 'CA':
                            pod_acct = 'build-pod-tf-apps-api-ca-dev-npe'
                            pod_tf_sa='tf-apps-api-ca-dev-npe@apps-api-ca-dev-npe-11bd.iam.gserviceaccount.com'
                            impersonacct = 'pending-to-create'
                        break
                        case 'AU':
                            pod_acct = 'build-pod-tf-apps-api-au-dev-npe'
                            pod_tf_sa='tf-apps-api-au-dev-npe@apps-api-au-dev-npe-38d6.iam.gserviceaccount.com'
                            impersonacct = 'pending-to-create'
                        break
                        case 'UK':
                            pod_acct = 'build-pod-tf-apps-api-uk-dev-npe'
                            pod_tf_sa='tf-apps-api-uk-dev-npe@apps-api-uk-dev-npe-837e.iam.gserviceaccount.com'
                            impersonacct = 'pending-to-create'
                        break
                        case 'IN': //TO BE CONFIRMED
                            pod_acct = 'build-pod-tf-apps-api-nx-ind-uat-npe'
                            pod_tf_sa='tf-corpsvc-api-ind-uat-prd@corpsvc-api-ind-uat-prd-d057.iam.gserviceaccount.com'
                            impersonacct = 'pending-to-create'
                        break
                        case 'LA':
                            pod_acct = 'build-pod-tf-apps-api-la-dev-npe-new'
                            pod_tf_sa='tf-apps-api-la-dev-npe@apps-api-la-dev-npe-1033.iam.gserviceaccount.com'
                            impersonacct = 'resource-publisher@apps-api-la-dev-npe-1033.iam.gserviceaccount.com'
                        break
                        case 'EU': //TO BE CONFIRMED
                            pod_acct = 'build-pod-tf-apps-api-nx-eu-uat-npe'
                            pod_tf_sa='tf-corpsvc-api-eu-uat-prd@corpsvc-api-eu-uat-prd-9310.iam.gserviceaccount.com'
                            impersonacct = 'pending-to-create'
                        break
                    }
                    
                    echo "selected info below:"
                }
            }
        }
        stage('Publish Topic') {
            agent {
                label "${pod_acct}"
            }
            steps {
                script {
                    sh "gcloud config unset auth/impersonate_service_account"
                    message = sh(script:"gcloud compute disks list --format='value(name,instance_id,creationTimestamp' --format json >'diskage-${params.region}.json'")
                    sh "gcloud config set auth/impersonate_service_account ${impersonacct}"
                    def msgContent = sh(script: "cat 'diskage-${params.region}.json'", returnStdout: true).trim()
                    sh "gcloud pubsub topics publish 'projects/apps-api-dp-us-npe-ae82/topics/resources_gcp_topic' --message='$msgContent'"
                    sh "gcloud config unset auth/impersonate_service_account"
                }
            }
        }
        stage('Reader') {
            agent {
                label "build-pod-tf-apps-api-dp-us-npe"
            }
            steps {
            //Add US account
                script {
                    pod_tf_sa = 'tf-apps-api-dp-us-npe@apps-api-dp-us-npe-ae82.iam.gserviceaccount.com'
                    impersonacct='resource-publisher@apps-api-dp-us-npe-ae82.iam.gserviceaccount.com'
                }
            }
        }
        stage('Subscriptors') {
            agent {
                label "build-pod-tf-apps-api-dp-us-npe"
            }
            steps {
                //Subscribers disk age information to Pub/Sub
                echo "${pod_tf_sa}"
                echo "${impersonacct}"
                sh "gcloud config set auth/impersonate_service_account ${impersonacct}"
                sh "gcloud pubsub subscriptions pull --auto-ack --limit=1 'projects/apps-api-dp-us-npe-ae82/subscriptions/subscription_resources'"
            }
        }
    }
}
