package com.swissre.bpm.mstemplate.servicestatus;

import com.google.gson.annotations.Expose;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.swissre.bpm.javautils.zeebeworkerbase.servicestatus.ResourceStatusChecker;

import java.util.Properties;

public class MicroserviceStatusChecker implements ResourceStatusChecker {

    private static final Logger LOG = LogManager.getLogger(MicroserviceStatusChecker.class);
    private Properties appProps;

    @Expose
    private boolean isHealthy = false;

    public MicroserviceStatusChecker(Properties appProps) {
        this.appProps = appProps;
    }


    @Override
    public boolean isHealthy() {
        return isHealthy;
    }

    @Override
    public void evaluateStatus() {
        isHealthy = true;
        //TODO: Check health status of Rest APIs/DB Connections/.....etc
    }
}