package com.swissre.configMap.zbconfigmap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ZeebeConfigServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZeebeConfigServerApplication.class, args);
    }
}