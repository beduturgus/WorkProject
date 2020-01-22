package my.project.BenasProject.ProcessorsTests;

import my.project.BenasProject.Utils;
import my.project.BenasProject.domain.ContactsInfo;
import my.project.BenasProject.routes.processors.XmlEnrichProcessor;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.commons.lang3.builder.EqualsBuilder;
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
    classes = XmlEnrichProcessor.class)
@EnableAutoConfiguration
@RunWith(CamelSpringBootRunner.class)
public class XmlEnrichProcessorTest {


    private final String PAYLOAD = Utils.getResource("xmlTestPayload.txt");
    private final String START_ENDPOINT = "jetty:http://0.0.0.0:8090/postNothing";

    @Autowired
    private CamelContext camelContext;

    @Autowired
    XmlEnrichProcessor xmlEnrichProcessor;

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
                from(START_ENDPOINT).process(xmlEnrichProcessor).to(mockEndpoint);
            }
        });
        camelContext.start();
    }

    @Test
    @DirtiesContext
    public void xmlEnrichProcessorTest() {
        producerTemplate.sendBody(PAYLOAD);
        ContactsInfo result = mockEndpoint.getExchanges().get(0).getIn().getBody(ContactsInfo.class);
        Assert.assertTrue(EqualsBuilder.reflectionEquals(createContactsInfo(), result));
    }

    private ContactsInfo createContactsInfo(){
        ContactsInfo contactsInfo = new ContactsInfo();
        contactsInfo.setName("joo");
        contactsInfo.setCompany("Zenitech");
        contactsInfo.setPhone(123);
        contactsInfo.setEmpty("GeneratedValue");
        contactsInfo.setFiller("kazkas kitas");
        return contactsInfo;
    }
}
