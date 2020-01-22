package my.project.BenasProject;

import my.project.BenasProject.routes.builders.HttpToDbRoute;
import my.project.BenasProject.routes.processors.DbProcessor;
import my.project.BenasProject.routes.processors.ValidationProcessor;
import my.project.BenasProject.routes.processors.XmlEnrichProcessor;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;


@SpringBootTest(webEnvironment = WebEnvironment.NONE,
    classes = {HttpToDbRoute.class,
        HttpToDbRouteTest.class,
        DbProcessor.class,
        XmlEnrichProcessor.class,
        ValidationProcessor.class
    })
@EnableAutoConfiguration
@RunWith(CamelSpringBootRunner.class)
public class HttpToDbRouteTest {

    @Autowired
    @Qualifier(value = "camelContext")
    CamelContext camelContext;



    private final String MOCK_ENDPOINT = "mock:accepted";
    private final String START_ENDPOINT = "jetty:http://0.0.0.0:8090/postNothing";
    private final String REST_ROUTE = "HttpToDbRoute";

    private final String EXPECTED_RESULT = "{\"name\":\"nii\",\"company\":\"Zenitech\",\"phone\":123,\"empty\":\"GeneratedValue\",\"filler\":\"kazkas kitas\"}";
    private final String PAYLOAD2 = "<contactsInfo>\n"
        + "    <name>nii</name>\n"
        + "    <company>Zenitech</company>\n"
        + "    <phone>123</phone>\n"
        + "    <empty></empty>\n"
        + "    <filler>kazkas kitas</filler>\n"
        + "</contactsInfo>";

    public void sendPayload(ProducerTemplate producerTemplate) {
        producerTemplate.sendBody(PAYLOAD2);
    }

    public MockEndpoint mockEndpoint(String routeId, String startEndpoint) throws Exception {
        camelContext.getRouteDefinition(routeId).adviceWith(camelContext, new HttpToDbRoute() {
            @Override
            public void configure() {
                interceptSendToEndpoint(startEndpoint).skipSendToOriginalEndpoint()
                    .to(MOCK_ENDPOINT);
            }
        });
        return camelContext.getEndpoint(MOCK_ENDPOINT, MockEndpoint.class);
    }

    @Ignore
    @Test
    @DirtiesContext
    public void restRouteTest() throws Exception {
        MockEndpoint mockEndpoint = mockEndpoint(REST_ROUTE, START_ENDPOINT);
        ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
        producerTemplate.setDefaultEndpointUri(START_ENDPOINT);
        sendPayload(producerTemplate);
        String receviedTransformedText = mockEndpoint.getExchanges().get(0).getIn().getBody()
            .toString();
        Assert.assertEquals(EXPECTED_RESULT, receviedTransformedText);
    }
}
