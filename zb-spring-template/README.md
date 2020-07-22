# Introduction 
This template leverage Zeebe within your Spring or Spring Boot environment easily. It is basically a wrapper around the zeebe Java client which will interact with Zeebe, create a zeebe worker instance, executes the workflow and returns a response. The purpose of this task is to provide a template for anyone who wants to create their own micro-service.
 
## Getting Started
---

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites 

* IDE - **IntelliJ**
* Build Tool - **Maven 3.***
* GIT - **GitExtensions** to work in command line (optional)

Refer \\dfs.swissre.com\DFS\Global\AppData\SOT\DEVTOOLS for the above softwares

### zbctl setup
# Copy the Binary and set the path
copy \\scw000003499.corp.gwpnet.com\Zeebe\zbctl\zbctl.exe C:\srdev\
set PATH=%PATH%;C:\srdev\

> How to Deploy a Workflow, Create instance and create a worker
```
opy \\scw000003499.corp.gwpnet.com\Zeebe\zbctl\zbctl.exe C:\srdev\
set PATH=%PATH%;C:\srdev\
 
##Instruction to Deploy a Workflow, Create instance and create a worker
 
C:\dev\springTemplate\zb-spring-template\src\test\resources>zbctl deploy SpringOnboardingTemplate.bpmn --address zeebe.sco-np.swissre.com:8090
{
  "key": 2251799813695543,
  "workflows": [
    {
      "bpmnProcessId": "SpringOnboardingTemplate",
      "version": 1,
      "workflowKey": 2251799813695503,
      "resourceName": "SpringOnboardingTemplate.bpmn"
    }
  ]
}
 
C:\dev\springTemplate\zb-spring-template\src\test\resources>zbctl create instance SpringOnboardingTemplate --address zeebe.sco-np.swissre.com:8090 --variables "{\"messageText\":\"Hello\"}"
{
  "workflowKey": 2251799813695503,
  "bpmnProcessId": "SpringOnboardingTemplate",
  "version": 1,
  "workflowInstanceKey": 2251799813695554
}
 
 
C:\dev\springTemplate\zb-spring-template\src\test\resources> zbctl --address zeebe.sco-np.swissre.com:8090 create worker springTemplate --handler cat
C:\dev\springTemplate\zb-spring-template\src\test\resources>zbctl --address zeebe.sco-np.swissre.com:8090 create worker zeebeSpringTemplate --handler cat
```

##### Configure IntelliJ, JDK 11 and above and Maven to view/modify the code
1. Install **JDK 1.11 and above** to work with the zeebe java client. Configure JDK in IntelliJ as below
``` File --> Project Structure --> Platform Settings --> SDKs --> Select "C:\SRDEV\tool\sdk\jdk12" ``` .
2. Download latest version of [*settings.xml*](https://wiki.swissre.com/download/attachments/217964673/settings.xml?version=2&modificationDate=1479202245000&api=v2) and overwrite older version in Maven configuration  
    ``` C:\srdev\tool\maven-3.3.3\conf\ ```
3. Configure Maven in IntelliJ, if you haven't configured already
	``` File --> Settings --> Build, Execution, Deployment --> Build Tools --> Maven --> Select "C:/SRDEV/tool/maven/maven-3.3.3" ``` .

*Note: Latest version of settings.xml points to SwissRe Artifactory which has link to more repositories internally and to IBM and so on..*   
##### Clone AzureDevops GIT Repository into local disk
> Clone using Git Bash   

```
		mkdir -p /c/users/<user_id>/git/     
		cd /c/users/<user_id>/git/   
		git clone https://swissre@dev.azure.com/swissre/SCO-OrchestrationPlatform/_git/zb-spring-template
```

> Configure the **Maven Java Project** in IntelliJ

```
		1. Open IntelliJ by navigating to C:\....\IntelliJIDEA\bin and click on idea64.exe.
		2. Click on the 'Open' and navigate to the pom.xml location where is your project is checked out
		3. Select as 'Open Existing Project'
		4. IntelliJ now starts Resolving the maven dependencies
		5. Once Dependencies as resolved, open the MicroserviceTemplate.java, right click and run the MicroserviceTemplate.main()
```
## Pipeline Configuration
### dockerfile-maven-plugin in pom.xml --> A Maven plugin for building and pushing Docker images. 
Refer https://github.com/spotify/docker-maven-plugin to know more about it

Do the microservice specific changes in the below files  
### Dockerfile
 To the bottom of the file, do the following change 
```
ARG JAR_FILE
ADD target/${JAR_FILE} /<your jar file name here>.jar 

ENTRYPOINT java -jar /<your jar file name here>.jar

```
Example. zeebe-spring-template.jar

### azure-pipelines.yml
Update the below three properties as per your Java project name
	Example:
	* mavenPomFile: '<Java Project Name>/pom.xml'
	* PathtoPublish: './<Java Project Name>/target/classes/microservice-deploy.yaml'
	* PathtoPublish: './<Java Project Name>/target/classes/setup-caas.sh'

The build info remains the same in all the cases
```
inputs:
      filePath: '/home/tecscoci/bin/buildinfo.sh' # This seems very nasty and it is. ADO does not support $HOME it seems
      noProfile: false
      noRc: false
```

### caas-config folder
* Rename the file microservice-deploy.yaml as per the use-case Ex. <use-case name>-deploy.yaml

* Inside the above yaml file, modify the name with your Java project name wherever you find 'zb-spring-template'

##### Add *.-deploy.yaml & setup-caas.sh

Update the below lines as per the examples
* kubectl -n <namespace> delete  deployment.apps/<Java project name>
* kubectl -n <namespace> apply -f <use-case name>-deploy.yaml

> Execute **Zeebe Test Rule ** in IntelliJ
```
		1. Place *.bpmn and properties file inside src\main\test\resources
		2. Open the MicroServiceTemplateTest.java and edit accordingly
		3. Configure the variables as per your BPMN workflow
		3. Right click and run the MicroserviceTemplateTest.java
```

### How to do HealthCheck status configurations
* Add the below dependency to your pom file
```
    <dependency>
        <groupId>com.swissre.bpm.javautils</groupId>
        <artifactId>zeebe-worker-base</artifactId>
        <version>1.2.0</version>
    </dependency>
   <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>io.zeebe.spring</groupId>
            <artifactId>spring-zeebe-starter</artifactId>
            <version>0.23.0</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
```

* In the Main class (MicroserviceTemplate.java) use the ZeebeWorkerBase class to execute the worker and do the healthcheck configs as shown below

```
    MicroserviceWorker microserviceWorker = new MicroserviceWorker(appProps);

    ZeebeClient client = ZeebeClient.newClientBuilder()
        .brokerContactPoint(appProps.getProperty("zeebeBroker.host") + ":" + appProps.getProperty("zeebeBroker.port")).usePlaintext()
        .build();

    ZeebeWorkerBase baseWorker = ZeebeWorkerBase.newBuilder(client)
        .setHealthCheckHostRoot(appProps.getProperty("healthCheck.rootPath"))
        .setHealthCheckPort(Integer.parseInt(appProps.getProperty("healthCheck.port")))
        .addResourceToCheck(new MicroserviceStatusChecker(appProps))
        .attachWorker(appProps.getProperty("zeebeBroker.jobType"),microserviceWorker)
        .usePlainText()
        .build();

 Note: usePlainText() --> Use a plaintext connection between the client and the gateway.
```
> Refer the ingress yaml for the healthcheck url path config
```
		path: /health/*
```
### Helm Implementation 
* Install "HELM" in local and set environment variable by giving "helm.exe" file path to access helm script.
* Create helm chart using "helm create $Chartname" 

# JIRA Number:
https://jira.swissre.com/browse/MI-1457 

# Reference:
```
https://github.com/zeebe-io/spring-zeebe/tree/master/client/spring-zeebe-starter
https://ourspace.swissre.com/docs/DOC-179268 
https://dev.azure.com/swissre/SCO-OrchestrationPlatform/_releaseProgress?_a=release-environment-logs&releaseId=1474&environmentId=2656
https://dev.azure.com/swissre/SCO-OrchestrationPlatform/_git/zb-control-service?path=%2FzeebeIntegrationService%2Fhelm%2Fzb-control-service%2Ftemplates%2Fdeployment.yaml&version=GBMI-1395-SNOW-PoC&line=33&lineEnd=34&lineStartColumn=1&lineEndColumn=1&lineStyle=plain 
https://dev.azure.com/swissre/SCO-OrchestrationPlatform/_git/zb-control-service?path=%2FzeebeIntegrationService%2Fhelm%2Fzb-control-service%2Fvalues.yaml&version=GBMI-1395-SNOW-PoC&line=17&lineEnd=18&lineStartColumn=1&lineEndColumn=1&lineStyle=plain 
https://dev.azure.com/swissre/_git/MAAS-Ext-MonitoringAPI?path=%2Fsr-monapi-ext-backend%2Fsrc%2Fmain%2Fresources%2Fapplication.yml
https://stackoverflow.com/questions/2263929/regarding-application-properties-file-and-environment-variable 

nexusIQ : https://dpsapi.swissre.com/nexusiq/assets/index.html#/dashboard/violations

command to excute the spring boot project with profiling : mvn -q spring-boot:run -Dspring.profiles.active=dev 
```

