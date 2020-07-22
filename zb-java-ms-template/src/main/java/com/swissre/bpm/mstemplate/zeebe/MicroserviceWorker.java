package com.swissre.bpm.mstemplate.zeebe;

import com.swissre.bpm.mstemplate.zeebe.util.MicroserviceTemplateRequest;
import com.swissre.bpm.mstemplate.zeebe.util.MicroserviceTemplateResponse;
import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.response.ActivatedJob;
import io.zeebe.client.api.worker.JobClient;
import io.zeebe.client.api.worker.JobHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

;

public class MicroserviceWorker implements JobHandler {

    private static final Logger LOG = LogManager.getLogger(MicroserviceWorker.class);
    private ZeebeClient client;
    private Properties appProps;

    public MicroserviceWorker(Properties appProps) {
        this.appProps = appProps;
    }

    @Override
    public void handle(JobClient jobClient, ActivatedJob job) throws Exception {
        LOG.info("Handling job: " + job);
        LOG.info("Attaching worker for jobtype: " + appProps.getProperty("zeebeBroker.jobType"));

        MicroserviceTemplateRequest requestParams;
        try {
            requestParams = job.getVariablesAsType(MicroserviceTemplateRequest.class);
        } catch (Exception e) {
            LOG.error("Exception happened when parsing inputs from zeebe", e);
            throw e;
        }

        try {
            String statusCode = sendPOST(requestParams);

            MicroserviceTemplateResponse response = new MicroserviceTemplateResponse();
            response.setStatusCode(statusCode);
            jobClient.newCompleteCommand(job.getKey())
                    .variables(response)
                    .send()
                    .join();
        } catch (Exception e) {
            LOG.error("Exception occurred during sendPOST ", e);
            throw e;
        }
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