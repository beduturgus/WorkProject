package my.project.BenasProject;

import my.project.BenasProject.domain.ContactsInfo;
import my.project.BenasProject.routes.processors.DbProcessor;
import my.project.BenasProject.routes.processors.ValidationProcessor;
import my.project.BenasProject.routes.processors.XmlEnrichProcessor;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;


@SpringBootTest(webEnvironment = WebEnvironment.NONE,
    classes = {
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

    @EndpointInject(uri = "mock:result")
    MockEndpoint mockEndpoint;

    @Autowired
    ValidationProcessor validationProcessor;

    @Autowired
    XmlEnrichProcessor xmlEnrichProcessor;

    @Mock
    DbProcessor dbProcessor;

    private final String START_ENDPOINT = "jetty:http://0.0.0.0:8090/postNothing";
    private final String EXPECTED_RESULT = "{\"name\":\"joo\",\"company\":\"Zenitech\",\"phone\":123,\"empty\":\"GeneratedValue\",\"filler\":\"kazkas kitas\"}";
    private final String PAYLOAD = Utils.getResource("xmlTestPayload.txt");

    @Before
    public void setUp() throws Exception {
        Mockito.doCallRealMethod().when(dbProcessor).process(Mockito.any(Exchange.class));
        Mockito.doCallRealMethod().when(dbProcessor).convertToJsonString(Mockito.any(ContactsInfo.class));
        Mockito.doCallRealMethod().when(dbProcessor).checkMessageType(Mockito.any(Message.class));
        Mockito.doNothing().when(dbProcessor).persistData(Mockito.any(ContactsInfo.class));
        camelContext.addRoutes(new RouteBuilder() {
                                   @Override
                                   public void configure() {
                                       from(START_ENDPOINT)
                                           .process(validationProcessor)
                                           .process(xmlEnrichProcessor)
                                           .process(dbProcessor)
                                           .to(mockEndpoint);
                                   }
                               }

        );
        camelContext.start();
    }

    @Test
    @DirtiesContext
    public void restRouteTest() {
        ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
        producerTemplate.setDefaultEndpointUri(START_ENDPOINT);
        producerTemplate.sendBody(PAYLOAD);

        String receviedTransformedText = mockEndpoint.getExchanges().get(0).getIn().getBody().toString();
        Assert.assertEquals(EXPECTED_RESULT, receviedTransformedText);
    }
}
