package com.swissre.zeebe.template;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Properties;


@SpringBootApplication
public class TemplateWorkerApplication
{
    private static final Logger LOG = LogManager.getLogger(TemplateWorkerApplication.class);

    public static void main(String[] args) throws IOException {
        LOG.info("Starting application");
        // changeEnvironmentVariable();
        // changeIngressEnvVariable();
        SpringApplication.run(TemplateWorkerApplication.class, args);
    }


    /*private static void changeEnvironmentVariable() throws IOException
    {
        Properties localProperties = loadBootStrapProperties();
        String environment = System.getenv("ENVIRONMENT");
        if(environment != null && !environment.isEmpty()) {
            LOG.info("ENVIRONMENT env variable found: {}", environment);
        } else {
            environment = "DEV";
            LOG.info("ENVIRONMENT env variable was not found, using {} as default value.", environment);
        }
        LOG.info("Altering config file location to: {}",(localProperties.getProperty("spring.profiles.active")).replace("ENVIRONMENT",environment));
        localProperties.put("spring.profiles.active",(localProperties.getProperty("spring.profiles.active")).replace("ENVIRONMENT",environment));
        LOG.info(localProperties.getProperty("spring.profiles.active"));
    }
    private static void changeIngressEnvVariable() throws IOException
    {
        Properties localProperties = loadApplicationProperties();
        String ingressEnv = System.getenv("INGRESS_ENV");
        if(ingressEnv != null && !ingressEnv.isEmpty()) {
            LOG.info("INGRESS_ENV variable found: {}", ingressEnv);
        } else {
            ingressEnv = "sco-dev";
            LOG.info("INGRESS_ENV variable was not found, using {} as default value.", ingressEnv);
        }
        LOG.info("Altering ingress Environment location to: {}",(localProperties.getProperty("zeebe.broker.contactPoint")).replace("INGRESS_ENV",ingressEnv));
        LOG.info("Altering ingress Environment location to: {}",(localProperties.getProperty("zeebe.client.broker.contactPoint")).replace("INGRESS_ENV",ingressEnv));
        localProperties.put("zeebe.broker.contactPoint",(localProperties.getProperty("zeebe.broker.contactPoint")).replace("INGRESS_ENV",ingressEnv));
        localProperties.put("zeebe.client.broker.contactPoint",(localProperties.getProperty("zeebe.client.broker.contactPoint")).replace("INGRESS_ENV",ingressEnv));
    }

    public static Properties loadApplicationProperties() throws IOException {
        Properties appProps = new Properties();
        appProps.load(TemplateWorkerApplication.class.getClassLoader().getResourceAsStream("application.properties"));
        return appProps;
    }

    public static Properties loadBootStrapProperties() throws IOException {
        Properties appProps = new Properties();
        appProps.load(TemplateWorkerApplication.class.getClassLoader().getResourceAsStream("bootstrap.properties"));
        return appProps;
    }*/


}
