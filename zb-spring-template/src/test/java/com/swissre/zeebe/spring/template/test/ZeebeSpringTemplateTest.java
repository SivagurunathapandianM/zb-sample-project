/*
package com.swissre.zeebe.spring.template.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ZeebeSpringTemplateTest {
	
	private static final Logger LOG = LogManager.getLogger(ZeebeSpringTemplateTest.class);
	
	
	
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
            .addResourceFromClasspath("SpringOnboardingTemplate.bpmn")
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
*/
