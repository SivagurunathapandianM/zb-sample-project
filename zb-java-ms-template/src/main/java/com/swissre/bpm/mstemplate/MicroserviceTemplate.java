package com.swissre.bpm.mstemplate;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.swissre.bpm.javautils.zeebeworkerbase.zeebe.ZeebeWorkerBase;
import com.swissre.bpm.mstemplate.servicestatus.MicroserviceStatusChecker;
import com.swissre.bpm.mstemplate.zeebe.MicroserviceWorker;
import io.zeebe.client.ZeebeClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import java.util.Properties;

public class MicroserviceTemplate {

    private static final Logger LOG = LogManager.getLogger(MicroserviceTemplate.class);

    public static void main(String[] args) throws SSLException {

        LOG.info("MicroserviceTemplate is starting up!");
        Properties appProps;
        try {
            appProps = loadProperties();
            LOG.info("MicroserviceTemplate loaded the properties: {}", appProps);
        } catch (IOException | InterruptedException e) {
            LOG.error("MicroserviceTemplate can not load properties. Exiting. ", e);
            return;
        }

        MicroserviceWorker microserviceWorker = new MicroserviceWorker(appProps);

        ZeebeClient client = ZeebeClient.newClientBuilder()
                .brokerContactPoint(appProps.getProperty("zeebeBroker.host") + ":" + appProps.getProperty("zeebeBroker.port")).usePlaintext()
                .defaultRequestTimeout(Duration.ofSeconds(Long.valueOf(appProps.getProperty("zeebeBroker.timeoutSecs"))))
                .defaultJobTimeout(Duration.ofSeconds(Long.valueOf(appProps.getProperty("zeebeBroker.timeoutSecs"))))
                .build();

        ZeebeWorkerBase baseWorker = ZeebeWorkerBase.newBuilder(client)
                .setHealthCheckHostRoot(appProps.getProperty("healthCheck.rootPath"))
                .setHealthCheckPort(Integer.parseInt(appProps.getProperty("healthCheck.port")))
                .addResourceToCheck(new MicroserviceStatusChecker(appProps))
                .attachWorker(appProps.getProperty("zeebeBroker.jobType"), microserviceWorker)
                .usePlainText()
                .build();
    }

    // Loading property via zb-config-server or from local
    private static Properties loadProperties() throws IOException, InterruptedException {
        Properties localProperties = loadLocalProperties();
        String configServer = localProperties.getProperty("configServer.hostRoot");
        String applicationName = localProperties.getProperty("configServer.applicationName");
        String environment = (System.getenv("ENVIRONMENT")!=null)?System.getenv("ENVIRONMENT"):localProperties.getProperty("configServer.environment");

        Properties appProps = new Properties();
        HttpClient httpClient = HttpClient.newHttpClient();

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(configServer + "/" + applicationName + "/" + environment))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String propertiesAsString = response.body();
            LOG.info("Got properties from config server: \n{}", propertiesAsString);

            JsonObject jsonObject = new JsonParser().parse(propertiesAsString).getAsJsonObject();

            if ((jsonObject.getAsJsonArray("propertySources").size()) == 0) {
                LOG.info("Property file is loaded from resource path");
                appProps = loadLocalAppProperties(environment);
            } else {
                String filePath = jsonObject.getAsJsonArray("propertySources").get(0).getAsJsonObject().get("name").getAsString();
                LOG.info("File Path: " + filePath);
                HttpRequest appPropRequest = HttpRequest.newBuilder()
                        .GET()
                        .uri(URI.create(configServer+"/"+filePath))
                        .build();
                HttpResponse<String> appPropReponse = httpClient.send(appPropRequest, HttpResponse.BodyHandlers.ofString());
                String appPropResponseBody = appPropReponse.body();
                StringReader reader = new StringReader(appPropResponseBody);
                LOG.debug("Reader: " + reader);
                LOG.info("Property file is loaded from Config Server");
                appProps.load(reader);
            }
        }
        catch (Exception e){
            LOG.error("Exception with loading the property file from config server",e);
            LOG.info("Property file is loaded from resource path");
            appProps = loadLocalAppProperties(environment);
        }
        return appProps;
    }
    private static Properties loadLocalProperties() throws IOException {
        Properties localProps = new Properties();
        localProps.load(MicroserviceTemplate.class.getClassLoader().getResourceAsStream("MicroServiceTemplate.properties"));
        return localProps;
    }
    private static Properties loadLocalAppProperties(String environment) throws IOException {
        Properties appProps = new Properties();
        switch (environment)
        {
            case "DEV":
                appProps.load(MicroserviceTemplate.class.getClassLoader().getResourceAsStream("JMSTemplate-DEV.properties"));
                LOG.debug("Environment:: "+environment+"  is  supported");
                break;
            case "NONPROD":
                appProps.load(MicroserviceTemplate.class.getClassLoader().getResourceAsStream("JMSTemplate-NONPROD.properties"));
                LOG.debug("Environment:: "+environment+"  is  supported");
                break;
            case "PROD":
                appProps.load(MicroserviceTemplate.class.getClassLoader().getResourceAsStream("JMSTemplate-PROD.properties"));
                LOG.debug("Environment:: "+environment+"  is  supported");
                break;
            default:
                LOG.error("Environment:: "+environment+" is not supported");
                appProps.load(MicroserviceTemplate.class.getClassLoader().getResourceAsStream("JMSTemplate-DEV.properties"));
        }
        return appProps;
    }

}