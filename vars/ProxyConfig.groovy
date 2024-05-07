import groovy.json.JsonOutput
import com.equifax.utils.Settings
import com.equifax.apigee.APIConfig
import com.equifax.utils.Gsutil

/*
    This file will handle the API proxy configuration pipeline
*/


def call(Map orgCfg, Map envCfg, String operation, String apiProxyName, String payload) {
    Gsutil gsutilCli = new Gsutil()
    def configData
    String bucket = ""
    String jenkins_agent = ""
    String environment = envCfg.env
    def date = new Date().format('dd_MM_yy_HH_mm_ss')
    switch(environment){
        case 'sandbox':
        case 'uat':
        case 'prod':
            bucket='gs://resources-proxy-config-prd'
            jenkins_agent='prod-deploy-pod-tf-apps-in-api-prd'
            break
        case 'devsandbox':
        case 'dev':
        case 'qa':
        case 'load':
            bucket='gs://resources-proxy-config-npe'
            jenkins_agent='build-pod-tf-apps-api-dp-us-npe'
            break
        }
    node(jenkins_agent) {
        try {

            String baseAPI = envCfg['proxy_config_api'].toString()
            String credID = orgCfg['auth_header'].toString()
            APIConfig config = new APIConfig()
            

            stage("API Config") {
                
                if (operation == 'retrieve') {
                    configData = config.getConfig(baseAPI, credID, apiProxyName)
                    config.printAPIConfig(configData)
                    confjson = writeJSON(json: configData, pretty: 4, returnText: true)
                    }
                if (operation == 'upsert') {
                    if (config.configExists(baseAPI, credID, apiProxyName)) {
                        String backupFileName = "${apiProxyName}_backup_${snow_ticket}_${date}.json"
                        writeJSON(file:backupFileName ,json: config.getConfig(baseAPI, credID, apiProxyName), pretty: 4)
                        sh "ls -la && cat ${backupFileName}"
                        gsutilCli.uploadToBucket("${bucket}/${snow_ticket}/", backupFileName )
                        def response = config.updateConfig(baseAPI, credID, apiProxyName, payload)
                    }
                }
                if (operation == 'create') {
                    if (config.configExists(baseAPI, credID, apiProxyName)) {
                        error('Configuration exists. Please re-run pipeline with upsert/update operation.')
                    } else {
                        def response = config.createConfig(baseAPI, credID, payload)
                    }
                }
            }
                
        }
        catch (any){
            print(any.toString())
            currentBuild.result = 'FAILURE'
        }
    }       
}
