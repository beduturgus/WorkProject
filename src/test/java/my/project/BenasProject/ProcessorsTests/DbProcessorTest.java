package my.project.BenasProject.ProcessorsTests;

import my.project.BenasProject.Utils;
import my.project.BenasProject.domain.ContactsInfo;
import my.project.BenasProject.routes.processors.DbProcessor;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = WebEnvironment.NONE,
    classes = DbProcessor.class)
@EnableAutoConfiguration
@RunWith(CamelSpringBootRunner.class)
public class DbProcessorTest {

    private String PAYLOAD = Utils.getResource("xmlTestPayload.txt");
    private String EXPECTED = Utils.getResource("jsonExpectedPayload.txt");

    private final String START_ENDPOINT = "jetty:http://0.0.0.0:8090/postNothing";

    @Autowired
    private CamelContext camelContext;

    @Autowired
    DbProcessor dbProcessor;

    @EndpointInject(uri = "mock:result")
    private MockEndpoint mockEndpoint;

    @Produce(uri = START_ENDPOINT)
    ProducerTemplate producerTemplate;

    @Before
    public void setUp() throws Exception {
        DbProcessor dbProcessor1 = Mockito.mock(dbProcessor.getClass());
        Mockito.doCallRealMethod().when(dbProcessor1).process(Mockito.any(Exchange.class));
        Mockito.doCallRealMethod().when(dbProcessor1)
            .convertToJsonString(Mockito.any(ContactsInfo.class));
        Mockito.doCallRealMethod().when(dbProcessor1).checkMessageType(Mockito.any(Message.class));
        Mockito.doNothing().when(dbProcessor1).persistData(Mockito.any(ContactsInfo.class));

        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {
                from(START_ENDPOINT).process(dbProcessor1).to(mockEndpoint);
            }
        });
        camelContext.start();
    }

    @Test
    @DirtiesContext
    public void dbProcessorTest() {
        producerTemplate.sendBody(PAYLOAD);
        String result = mockEndpoint.getExchanges().get(0).getIn().getBody(String.class);
        Assert.assertEquals(EXPECTED, result);
    }
}
