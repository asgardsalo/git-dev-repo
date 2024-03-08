#!groovy

//import com.equifax.utils.Gcloud

def pod_acct
def impersonacct
def message
def pod_tf_sa
def json_file

pipeline {
    agent any
    
    parameters { 
        choice (name: 'region', choices: ['US', 'CA', 'AU', 'UK', 'IN', 'LA', 'EU' ], description: 'Select the GCP Region where to perform the task ')
    }
    
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
                            impersonacct = sh(script: "gcloud config set auth/impersonate_service_account")
                        break
                        case 'AU':
                            pod_acct = 'build-pod-tf-apps-api-au-dev-npe'
                            impersonacct = sh(script: "gcloud config set auth/impersonate_service_account")
                        break
                        case 'UK':
                            pod_acct = 'build-pod-tf-apps-api-uk-dev-npe'
                            impersonacct = sh(script: "gcloud config set auth/impersonate_service_account")
                        break
                        case 'IN':
                            pod_acct = 'build-pod-tf-apps-api-nx-ind-uat-npe'
                            impersonacct = sh(script: "gcloud config set auth/impersonate_service_account")
                        break
                        case 'LA':
                            pod_acct = 'build-pod-tf-apps-api-la-dev-npe-new'
                            impersonacct = 'resource-publisher@apps-api-la-dev-npe-1033.iam.gserviceaccount.com'
                        break
                        case 'EU':
                            pod_acct = 'build-pod-tf-apps-api-nx-eu-uat-npe'
                            impersonacct = sh(script: "gcloud config set auth/impersonate_service_account")
                        break
                    }
                    
                    echo "selected info below:"
                }
            }
        }
        stage('Impersonate') {
            agent {
                label "${pod_acct}"
            }
            steps {
                script {
                    sh "gcloud config unset auth/impersonate_service_account"
                    //sh "gcloud iam service-accounts add-iam-policy-binding --role=roles/iam.serviceAccountTokenCreator --member=serviceAccount:${pod_tf_sa} ${impersonacct}"
                    //sh "gcloud iam service-accounts add-iam-policy-binding  --role=roles/compute.Admin --member=serviceAccount:${pod_tf_sa} ${impersonacct}"
                    //sh "gcloud iam service-accounts get-iam-policy ${impersonacct}"
                    //sh "gcloud config set auth/impersonate_service_account ${impersonacct}"
                    message = sh(script:"gcloud compute disks list --format='value(name,instance_id,creationTimestamp' --format json >'diskage-${params.region}.json'")
                    echo (message)
                    
                }
            }
        }
        stage('Topic') {
            agent {
                label "${pod_acct}" //"${impersonacct}"
            }   
            steps {
                script {
                    //Publish disk age information to Pub/Sub topic
                    sh "gcloud iam service-accounts add-iam-policy-binding --role=roles/iam.serviceAccountTokenCreator --member=serviceAccount:${pod_tf_sa} ${impersonacct}"
                    sh "gcloud iam service-accounts get-iam-policy ${impersonacct}"
                    sh "gcloud config set auth/impersonate_service_account ${impersonacct}"
                    def msgContent = sh(script: "cat 'diskage-${params.region}.json'", returnStdout: true).trim()
                    sh "gcloud pubsub topics publish 'resources_gcp_topic' --message = ${msgContent}"
                    sh "gcloud config unset auth/impersonate_service_account"
                }
            }
        }
        
        stage('Subscriptors') {
            agent {
                label "${pod_acct}"
            }
            steps {
                //Subscribers disk age information to Pub/Sub
                sh "gcloud iam service-accounts add-iam-policy-binding --role=roles/iam.serviceAccountTokenCreator --member=serviceAccount:${pod_tf_sa} ${impersonacct}"
                sh "gcloud iam service-accounts get-iam-policy ${impersonacct}"
                sh "gcloud config set auth/impersonate_service_account ${impersonacct}"
                sh "gcloud pubsub subscriptions pull --auto-ack --limit=1 'subscription_resources'"
            }
        }
    }
}
