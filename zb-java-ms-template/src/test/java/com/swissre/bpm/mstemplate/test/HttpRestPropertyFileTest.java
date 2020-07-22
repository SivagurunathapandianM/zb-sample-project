package com.swissre.bpm.mstemplate.test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.swissre.bpm.mstemplate.MicroserviceTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;


public class HttpRestPropertyFileTest {
    private static final Logger LOG = LogManager.getLogger(HttpRestPropertyFileTest.class);


    public  Properties loadLocalProperties() throws IOException {
        Properties localProps = new Properties();
        localProps.load(MicroserviceTemplate.class.getClassLoader().getResourceAsStream("MicroServiceTemplateTest.properties"));
        return localProps;
    }

    @Ignore
    public void loadProperties() throws IOException, InterruptedException {
        Properties localProperties = loadLocalProperties();
        Properties appProps = new Properties();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(localProperties.getProperty("configServer.hostRoot")
                        + localProperties.getProperty("configServer.applicationName")
                        + localProperties.getProperty("configServer.environment")))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String propertiesAsString = response.body();

        LOG.info("Property file String: "+propertiesAsString);

        JsonObject jsonObject = new JsonParser().parse(propertiesAsString).getAsJsonObject();
        JsonArray arr = jsonObject.getAsJsonArray("propertySources");
        LOG.info(arr);
        String post_id = null;
        for (int i = 0; i < arr.size(); i++) {
            post_id = arr.get(i).getAsJsonObject().get("source").toString().replace(":", "=").replace("{","").replace("}","").replace(",","\n").replace("\"","");
            LOG.info("POST Array String : "+post_id);
        }
            StringReader reader = new StringReader(post_id);
            LOG.info("Reader: "+reader);
            appProps.load(reader);
            LOG.info(appProps);
            LOG.info("JSON String Property file "+ appProps.getProperty("zeebeBroker.timeoutSecs"));

    }

    @Ignore
    public void loadLocalAppProperties() throws IOException {
        Properties appProps = new Properties();
        MicroserviceTemplate microserviceTemplate = new MicroserviceTemplate();
        //appProps = microserviceTemplate.loadLocalAppProperties("saf");
        LOG.info("HOST ::: "+appProps.getProperty("zeebeBroker.host"));

    }

}
