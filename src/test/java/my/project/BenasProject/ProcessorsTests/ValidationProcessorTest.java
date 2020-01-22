package my.project.BenasProject.ProcessorsTests;


import my.project.BenasProject.Utils;
import my.project.BenasProject.routes.processors.ValidationProcessor;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = WebEnvironment.NONE,
    classes = ValidationProcessor.class)
@EnableAutoConfiguration
@RunWith(CamelSpringBootRunner.class)
public class ValidationProcessorTest {

    private final String PAYLOAD = Utils.getResource("xmlTestPayload.txt");

    private final String START_ENDPOINT = "jetty:http://0.0.0.0:8090/postNothing";

    @Autowired
    private CamelContext camelContext;

    @Autowired
    ValidationProcessor validationProcessor;

    @EndpointInject(uri = "mock:result")
    private MockEndpoint mockEndpoint;

    @Produce(uri = START_ENDPOINT)
    ProducerTemplate producerTemplate;

    @Before
    public void setUp() throws Exception {
        camelContext.getRoutes().clear();
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {
                from(START_ENDPOINT).process(validationProcessor).to(mockEndpoint);
            }
        });
        camelContext.start();
    }

    @Test
    @DirtiesContext
    public void validationProcessorTest() {
        producerTemplate.sendBody(PAYLOAD);
        String result = mockEndpoint.getExchanges().get(0).getIn().getBody(String.class);
        Assert.assertEquals(PAYLOAD, result);
    }

}
