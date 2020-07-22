package com.swissre.zeebe.template.worker;

import com.swissre.zeebe.template.util.JobHelper;
import com.swissre.zeebe.template.util.MicroserviceTemplateRequest;
import com.swissre.zeebe.template.util.MicroserviceTemplateResponse;
import io.zeebe.client.api.response.ActivatedJob;
import io.zeebe.client.api.worker.JobClient;
import io.zeebe.spring.client.EnableZeebeClient;
import io.zeebe.spring.client.annotation.ZeebeWorker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;

@EnableZeebeClient
@Component
public class SpringTemplateWorker {

    private static final Logger LOG = LogManager.getLogger(SpringTemplateWorker.class);

    @Value("${zeebe.worker.defaultName}")
    private String defaultName;

    @Value("${zeebe.client.broker.contactPoint}")
    private String contactPoint;

    @Value("${zeebe.client.security.plaintext}")
    private String plaintext;

    @ZeebeWorker(type = "springTemplate")
    public void handleSpringTemplate(final JobClient client, final ActivatedJob job)
    {
        LOG.info("#####################################################################");
        LOG.info("Got a request to run the spring template job type");

        JobHelper.logJob(job);
        Gson gson = new Gson();
        MicroserviceTemplateRequest input = gson.fromJson(job.getVariables(), MicroserviceTemplateRequest.class);
        LOG.info("Message Text", input.getMessageText());

        LOG.info("Contact Point :::::::::: "+contactPoint);

        String statusCode = sendPOST(input);
        MicroserviceTemplateResponse response = new MicroserviceTemplateResponse();
        response.setStatusCode(statusCode);

        client.newCompleteCommand(job.getKey()).variables(response).send().join();
    }

    @ZeebeWorker(type = "zeebeSpringTemplate")
    public void handle(final JobClient client, final ActivatedJob job)
    {
        LOG.info("---------------------------------------------------------------------");
        LOG.info("Got a request to run the zeebespringtemplate job type");
        JobHelper.logJob(job);
        Gson gson = new Gson();
        MicroserviceTemplateRequest input = gson.fromJson(job.getVariables(), MicroserviceTemplateRequest.class);
        LOG.info("Message Text", input.getMessageText());

        LOG.info("Contact Point :::::::::: "+contactPoint);

        String statusCode = sendPOST(input);
        MicroserviceTemplateResponse response = new MicroserviceTemplateResponse();
        response.setStatusCode(statusCode);

        client.newCompleteCommand(job.getKey()).variables(response).send().join();
    }

    private String sendPOST(MicroserviceTemplateRequest request) {
        try {
            LOG.info("Input Params\n" + request);
            //do some logic here
            return "200";
        } catch (Exception ex) {
            LOG.error(ex);
            return "500";
        }
    }

    public String sendPOSTDuplicated(MicroserviceTemplateRequest request) {
        try {
            LOG.info("Input Params\n" + request);
            //do some logic here
            return "200";
        } catch (Exception ex) {
            LOG.error(ex);
            return "500";
        }
    }
}
