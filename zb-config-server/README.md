# Spring config server template

## Description
This microservice is responsible to externalise all the property file, Which will be accessed by other microservice in the zeebe project.
    * zb-Config-server
    * zb-Config-Client

Repository URL : https://swissre.visualstudio.com/SCO-OrchestrationPlatform/_git/zb-config-map
Config Property files : https://swissre.visualstudio.com/SCO-OrchestrationPlatform/_git/zb-config-propertyFiles
## Requirements
```text
* Java developer IDE like intellij/ Eclipse
* JDK and Maven setting 
* git bash
```

## Methodology to Pull file from different location is implemented in below classpath
```text
Path: classpath/application.properties
Spring config server supports below backend:
    * GIT HUB and repo
    * Classpath
    * JDBC
    * File Store
    * Vault
```

## HTTP service has resources in the form:
```text
/{application}/{profile}[/{label}]
/{application}-{profile}.yml
/{label}/{application}-{profile}.yml
/{application}-{profile}.properties
/{label}/{application}-{profile}.properties
```

## Steps to be followed to expose the property files:
```text
*  All the above implementation is done in the project .Kindly refer <ProjectRepo>/application.properties for the same.
   Path: classpath/application.properties
* Copy the property file in the backend. 
* This project will pull the property file from the backend and exposed in the below url.
  URL : http://zb-config.sco-dev.swissre.com/ 
*  File format should be like the below specified format
   {application}-{profile}.properties
* Property file can be access by the below format.
  URL : http://zb-config.sco-dev.swissre.com/<application>/<profile>
```


## Steps to be followed to invoke property file in the microservice:
```text
* Copy the property file in either of below backends. 
     * GIT HUB and repo
     * Classpath* (In the current system, we are using classpath methodology to load the property files)
     * JDBC
     * File Store
     * Vault
   
* Property file should be in the below format 
     {application}/{application}-{profile}.properties

* Property file can be access by the below format.
  URL : http://zb-config.sco-dev.swissre.com/<application>/<profile>

* In spring client (Microservice), please refer this project and change it accordingly
    bootstrap.properties
      spring.application.name={application}
      spring.cloud.config.uri=http://zb-config.sco-dev.swissre.com
      management.security.enabled=false
      management.endpoints.web.exposure.include=refresh
      spring.profiles.active=DEV,PROD,TEST
      #spring.cloud.config.name:{Property file name with comma seperated}

* For deployment profiling, Profile has to be dynamically passed to bootstrap properties where in based on the profile (DEV, TEST, PROD) the properties file are pulled from server.

spring.profiles.active = {environment}

```

## Steps to create Azure CI/CD Pipeline:
```text
* azure-pipelines.yaml file is created to achieve CICD pipeline in Azure devops environment.
```

## DOCKER Image and local testing
```text
docker run -d -p 8888:8888 docker-sco-local.artifact.swissre.com/zb-config-server:<artifact.Version>
```

## Configure Maven to run spring config server:
```xml
        <dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-config-server</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
```

Dependency for spring-cloud
```xml
    <dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>${spring-cloud.version}</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>

Dependency for maven-assembly-plugin
	        <plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-assembly-plugin</artifactId>
				<version>3.2.0</version>
				<executions>
					<execution>
						<phase>package</phase>
						<goals>
							<goal>single</goal>
						</goals>
						<configuration>
							<archive>
								<manifest>
									<mainClass>
										com.swissre.configMap.zbconfigmap.ZeebeConfigServerApplication
									</mainClass>
								</manifest>
							</archive>
							<descriptorRefs>
								<descriptorRef>jar-with-dependencies</descriptorRef>
							</descriptorRefs>
						</configuration>
					</execution>
				</executions>
			</plugin>
```

## URL Reference
```text

Spring Config and client server
    https://dzone.com/articles/spring-cloud-config-series-part-2-git-backend

How springBoot loads external configuration:
    https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-external-config
    https://projects.spring.io/spring-cloud/docs/1.0.3/spring-cloud.html#_spring_cloud_config_server
     
Centralized configuration:
    https://spring.io/guides/gs/centralized-configuration/ 

Auto Configure Proxy:
    https://github.com/Orange-OpenSource/spring-boot-autoconfigure-proxy    

Spring Cloud Configuration:
    https://cloud.spring.io/spring-cloud-static/spring-cloud-config/2.2.1.RELEASE/reference/html/ 
    https://github.com/spring-cloud/spring-cloud-config/releases 
    https://start.spring.io/ 
Maven Artifactory:
    https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-config-server 
    https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-config-client 
Docker image:
    https://hub.docker.com/r/hyness/spring-cloud-config-server/ 
Kubernete configuration:
    https://hub.helm.sh/charts/kiwigrid/spring-cloud-config-server 

```