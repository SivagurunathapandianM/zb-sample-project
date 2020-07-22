0. [ ] Successful pipeline run with:
    - All tests passed, SonarQube passed, Aqua passed, NexusIQ passed
    <br><br>
    
1. [ ] Maven
    - Zeebe microservices should be maven projects.
    - Name of the project should be understandable and should be related to the microservice requirements.
    - Increment version numbers in accordance to [SemanticVersioning](https://semver.org/).
    - Use properties tag in pom.xml file for versioning (jar, Docker image, etc.).
    <br><br>
    
2. [ ] Package and Classes
    - Package name should be com.swissre.bpm.<short name of microservices>.zeebe.
    - Class name, method names and variable names should follow the coding standards for better readability and maintainability. Please refer to [this](https://google.github.io/styleguide/javaguide.html#s5-naming ) for more information.
    - A logger should be used instead of println.
    - Health check should be implemented.
    <br><br>
    
3. [ ] Property file
    - Should not contain passwords and duplicate property values.
    <br><br>
    
4. [ ] Dockerfile
    - JDK should be zulu-openjdk:11 not openjdk:11.
    <br><br>
    
5. [ ] azure-pipelines.yaml
    - Should contain SonarQube task, Aqua Scan task, Nexus IQ task.
    <br><br>
        
6. [ ] CaaS Setup
    - Configuration files should not contain any commented lines (other than the explanation).
    - Unused ports and containers should be removed.
    <br><br>
    
7. [ ] README.md
    - Readme file should exist and it should contain useful and up-to-date information about the microservice.
    - End point ports should be added for healthcheck, glowroot, etc,.