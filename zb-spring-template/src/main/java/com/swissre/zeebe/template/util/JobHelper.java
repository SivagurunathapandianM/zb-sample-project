package com.swissre.zeebe.template.util;

import com.swissre.zeebe.template.TemplateWorkerApplication;
import io.zeebe.client.api.response.ActivatedJob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;

public class JobHelper
{
        private JobHelper()
        {
            throw new IllegalStateException("Utility class");
        }
        private static final Logger LOG = LogManager.getLogger(TemplateWorkerApplication.class);

        public static void logJob(final ActivatedJob job)
        {
            StringBuilder builder = new StringBuilder();
            builder.append("job infos >>> type: ");
            builder.append(job.getType());
            builder.append(", Worker: ");
            builder.append(job.getWorker());
            builder.append(", key: ");
            builder.append(job.getKey());
            builder.append(", element: ");
            builder.append(job.getElementId());
            builder.append(", workflow instance: ");
            builder.append(job.getWorkflowInstanceKey());
            builder.append(", deadline: ");
            builder.append(Instant.ofEpochMilli(job.getDeadline()));
            builder.append(", headers: ");
            builder.append(job.getCustomHeaders());
            builder.append(", variables: ");
            builder.append(job.getVariables());
            LOG.info("JobBuilder ::::"+builder.toString());
        }
}

