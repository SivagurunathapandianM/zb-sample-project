package com.swissre.bpm.mstemplate.test;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.response.WorkflowInstanceEvent;
import io.zeebe.test.ZeebeTestRule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.fail;
import java.io.FileInputStream;
import java.time.Duration;
import java.io.IOException;
import java.util.Properties;

import com.swissre.bpm.mstemplate.zeebe.MicroserviceWorker;

public class MicroserviceTemplateTest {

	private static final Logger LOG = LogManager.getLogger(MicroserviceTemplateTest.class);

	@Rule
	public final ZeebeTestRule testRule = new ZeebeTestRule();

	private ZeebeClient client;

	private static Properties appProps;



	@BeforeClass
	public static void setup() {
		try {
			String appConfigPath = Thread.currentThread().getContextClassLoader().getResource("MicroServiceTemplateTest.properties").getPath();
			appProps = new Properties();
			appProps.load(new FileInputStream(appConfigPath));
		} catch (IOException e) {
			LOG.error(e);
			fail("Failed to load test properties");
		}
	}

	@AfterClass
	public static void tearDown() {
	}

	@Test
	public void testMicroServiceTemplate() {
        client = testRule.getClient();
        client.newDeployCommand()
            .addResourceFromClasspath("MSOnboardingTemplate.bpmn")
            .requestTimeout(Duration.ofSeconds(Integer.parseInt(appProps.getProperty("requestTimeout.sec"))))
            .send()
            .join();

        final WorkflowInstanceEvent workflowInstance = client
            .newCreateInstanceCommand()
            .bpmnProcessId("MSOnboardingTemplate")
            .latestVersion()
            .variables(
						"{\"messageText\":\"HelloTest\"}")
            .send()
            .join();

        MicroserviceWorker worker = new MicroserviceWorker(appProps);
        client.newWorker()
		    .jobType(appProps.getProperty("zeebeBroker.jobType"))
			.handler(worker)
			.requestTimeout(Duration.ofSeconds(Integer.parseInt(appProps.getProperty("requestTimeout.sec"))))
			.open();

        ZeebeTestRule.assertThat(workflowInstance).isEnded();
    }
}