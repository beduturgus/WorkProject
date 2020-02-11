package my.project.BenasProject;

import my.project.BenasProject.interceptor.ProcessorInterceptStrategy;
import my.project.BenasProject.routes.builders.HttpToDbRoute;
import my.project.BenasProject.routes.processors.DbProcessor;
import my.project.BenasProject.routes.processors.ValidationProcessor;
import my.project.BenasProject.routes.processors.XmlEnrichProcessor;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;


@SpringBootTest(webEnvironment = WebEnvironment.NONE,
    classes = {
        HttpToDbRoute.class,
        DbProcessor.class,
        XmlEnrichProcessor.class,
        ValidationProcessor.class,
        ProcessorInterceptStrategy.class
    })
@EnableAutoConfiguration
@RunWith(CamelSpringBootRunner.class)
public class HttpToDbRouteTest {

    @Autowired
    @Qualifier(value = "camelContext")
    CamelContext camelContext;

    @EndpointInject(uri = "mock:result")
    MockEndpoint mockEndpoint;

    @Autowired
    ValidationProcessor validationProcessor;

    @Autowired
    XmlEnrichProcessor xmlEnrichProcessor;

    @Autowired
    HttpToDbRoute httpToDbRoute;

    @Autowired
    DbProcessor dbProcessor;

    @Autowired
    ProcessorInterceptStrategy interceptStrategy;

    private final String START_ENDPOINT = "jetty:http://0.0.0.0:8090/contactsInfo";
    private final String EXPECTED_RESULT = "{\"name\":\"joo\",\"company\":\"Zenitech\",\"phone\":123,\"empty\":\"GeneratedValue\",\"filler\":\"kazkas kitas\"}";
    private final String PAYLOAD = Utils.getResource("xmlTestPayload.txt");

    @Before
    public void setUp() throws Exception {
        camelContext.getRouteDefinition("HttpToDbRoute").adviceWith(camelContext,
            new RouteBuilder() {
                @Override
                public void configure() {
                    interceptSendToEndpoint("file://data/route_output")
                        .skipSendToOriginalEndpoint()
                        .to("mock:result");
                    intercept().addInterceptStrategy(interceptStrategy);
                }
            });
    }

    @Test
    @DirtiesContext
    public void restRouteTest() {
        ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
        producerTemplate.setDefaultEndpointUri(START_ENDPOINT);
        producerTemplate.sendBody(PAYLOAD);

        String receviedTransformedText = mockEndpoint.getExchanges().get(0).getIn().getBody(String.class);
        Assert.assertEquals(EXPECTED_RESULT, receviedTransformedText);
    }
}
