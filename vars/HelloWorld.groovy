import groovy.json.JsonOutput
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

// Define API Proxy details
def proxyName = "helloworld-asgard-api-proxy"
def environment = "dev"
def region = "us-east-1"
def apiBasePath = "/hello"
def apiEndpoint = "https://mockbackend.example.com/hello"

// Read YAML template file
def yamlTemplatePath = "/path/to/helloworld-config.yaml"
def yamlTemplate = new File(yamlTemplatePath).text

// Replace placeholders in YAML template
def yamlConfig = yamlTemplate
    .replace('${apiEndpoint}', apiEndpoint)
    .replace('${apiBasePath}', apiBasePath)

// Write the final YAML configuration to a file
def yamlFilePath = "${proxyName}-config.yaml"
Files.write(Paths.get(yamlFilePath), yamlConfig.getBytes(), StandardOpenOption.CREATE)
println "YAML configuration file created: $yamlFilePath"

// Simulated deployment process
println "Deploying API Proxy: $proxyName in $region [$environment]..."
println "API Proxy successfully deployed."

// Simulated KVM Configuration
def kvmConfig = [
    environment: environment,
    proxyName: proxyName,
    region: region,
    keyValues: [
        "key1": "value1",
        "key2": "value2"
    ]
]

def kvmFilePath = "${proxyName}-kvm.json"
Files.write(Paths.get(kvmFilePath), JsonOutput.prettyPrint(JsonOutput.toJson(kvmConfig)).getBytes(), StandardOpenOption.CREATE)
println "KVM configuration file created: $kvmFilePath"

// Simulated API Testing
println "Testing API Proxy..."
def testResponse = [
    status: 200,
    message: "Hello World from API Proxy!",
    environment: environment,
    proxy: proxyName
]
println "API Test Response: " + JsonOutput.prettyPrint(JsonOutput.toJson(testResponse))

println "Test completed successfully."