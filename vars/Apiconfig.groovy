package com.equifax.apigee


void printAPIConfig(def proxyConfig) {

    if (!proxyConfig) {
        error('Proxy configuration undefined. Unable to print.')
    }

    def configData=readJSON text: proxyConfig
    configData.BackendCredentials="*****"
    print(apiProxyName + " configuration : \n*************************")
    print(configData.toString())
    print("*************************")
}


boolean configExists(String baseAPI, String credID, String proxyName) {
    print(baseAPI)
    if (!baseAPI) {
        error('Malformed platform proxy/mocker configuration URL.')
    }
    if (!credID) {
        error('Platform app authentication undefined.')
    }
    if (!proxyName) {
        error('Proxy name undefined.')
    }
    
    withCredentials([string(credentialsId: credID, variable: 'authHeader')]) {
        def response = httpRequest httpMode: 'GET', customHeaders: [[maskValue: true, name: 'Authorization', value: authHeader]],
                url: "${baseAPI}/${proxyName}",
                validResponseCodes: "100:500"
                print(response)
        if (response.status == 200) {
            return true
        } else {
            return false
        }
        return false
    }
}


def getConfig(String baseAPI, String credID, String proxyName) {

    if (!baseAPI) {
        error('Malformed platform proxy/mocker configuration URL.')
    }
    if (!credID) {
        error('Platform app authentication undefined.')
    }
    if (!proxyName) {
        error('Proxy name undefined.')
    }

    withCredentials([string(credentialsId: credID, variable: 'authHeader')]) {
        def response = httpRequest httpMode: 'GET', customHeaders: [[maskValue: true, name: 'Authorization', value: authHeader]],
                url: "${baseAPI}/${proxyName}",
                validResponseCodes: "100:300"
        return response.content
    }
}


def createConfig(String baseAPI, String credID, def reqPayload) {

    if (!baseAPI) {
        error('Malformed platform proxy configuration URL undefined.')
    }
    if (!credID) {
        error('Platform app authentication undefined.')
    }
    if (!reqPayload) {
        error('Proxy configuration payload undefined.')
    }

    withCredentials([string(credentialsId: credID, variable: 'authHeader')]) {
        def response = httpRequest customHeaders: [[maskValue: true, name: 'Authorization', value: authHeader]],
                httpMode: 'POST',
                contentType: 'APPLICATION_JSON',
                url: baseAPI,
                requestBody: reqPayload,
                validResponseCodes: "100:300"
        return response.status
    }
}


def deleteConfig(String baseAPI, String credID, String proxyName) {

    if (!baseAPI) {
        error('Malformed platform proxy configuration URL.')
    }
    if (!credID) {
        error('Platform app authentication undefined.')
    }
    if (!proxyName) {
        error('Proxy name undefined.')
    }

    withCredentials([string(credentialsId: credID, variable: 'authHeader')]) {
        def response = httpRequest httpMode: 'DELETE', customHeaders: [[maskValue: true, name: 'Authorization', value: authHeader]],
                url: "${baseAPI}/${proxyName}",
                validResponseCodes: "100:300"
        return response.status
    }
}


def updateConfig(String baseAPI, String credID, String proxyName, def reqPayload) {

    if (!baseAPI) {
        error('Malformed platform proxy configuration URL.')
    }
    if (!credID) {
        error('Platform app authentication undefined.')
    }
    if (!proxyName) {
        error('Proxy name undefined.')
    }
    if (!reqPayload) {
        error('Proxy configuration undefined.')
    }

    withCredentials([string(credentialsId: credID, variable: 'authHeader')]) {
        def response = httpRequest customHeaders: [[maskValue: true, name: 'Authorization', value: authHeader]],
                httpMode: 'PUT',
                contentType: 'APPLICATION_JSON',
                url: "${baseAPI}/${proxyName}",
                requestBody: reqPayload,
                validResponseCodes: "100:300"
        return response.status
    }
}


def generateAPIConfig(String proxyName, String backendURL, String scope, String basePath) {

    if (!proxyName) {
        error('Proxy name undefined.')
    }
    if (!scope) {
        error('Default scope URL undefined.')
    }
    if (!basePath) {
        error('Base path undefined.')
    }

    String customScope = scope.replace('/<your-proxy-basepath>', basePath)
    def proxyConfig = '''{
        "name": "#PROXY_NAME#",
        "EndPoints": {
            "backend": "#NGINX#"
        },
        "SpikeArrest": {
            "Rate": "50ps"
        },
        "Scope": "#PROXY_PROD_URL#",
        "Description": "API Proxy Configuration"
    }'''
    def proxyConfigReq = proxyConfig.replace('#PROXY_NAME#', proxyName)
            .replace('#NGINX#',backendURL)
            .replace('#PROXY_PROD_URL#', customScope)
    return proxyConfigReq
}


def generateMockerConfig(String swaggerURL, String proxyName, String mockerLocation) {

    if (!proxyName) {
        error('Proxy name undefined.')
    }
    if (!mockerLocation) {
        error('mockerLocation undefined.')
    }

    def mockerConfigTemplate = '''{
        "name": "#PROXY_NAME#",
        "mockerPath":"#MOCKER_PATH#"
    }'''
    String mockerPath = ''
    if (mockerLocation.contains("apidocs.equifax.com")) {
        mockerPath = mockerLocation.replace(swaggerURL, "/virts")
        print("mockerPath : " + mockerPath)
    }
    else {
        mockerPath = mockerLocation
        print(mockerPath)
    }
    def config = mockerConfigTemplate.replace('#PROXY_NAME#', proxyName).replace('#MOCKER_PATH#', mockerPath)
    return config
}


return this