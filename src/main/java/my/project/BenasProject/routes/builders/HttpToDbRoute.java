package my.project.BenasProject.routes.builders;

import java.util.Date;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import my.project.BenasProject.domain.ContactsInfo;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HttpToDbRoute extends RouteBuilder {

    final String DESTINATION_FOLDER = "data/route_output";

    @Autowired
    private Processor validationProcessor;

    @Autowired
    private Processor xmlEnrichProcessor;

    @Autowired
    private Processor dbProcessor;


    @Override
    public void configure() throws JAXBException {

        JacksonDataFormat jacksonDataFormat = new JacksonDataFormat(ContactsInfo.class);

        JaxbDataFormat jaxbDataFormat = new JaxbDataFormat();
        JAXBContext context = JAXBContext.newInstance(ContactsInfo.class);
        jaxbDataFormat.setContext(context);

        from("jetty:http://0.0.0.0:8090/contactsInfo")
            .routeId("HttpToDbRoute")
            .log("Payload is being send to validationProcessor at" + new Date().getTime())
            .process(validationProcessor)
            .unmarshal(jaxbDataFormat)
            .process(xmlEnrichProcessor)
            .marshal(jaxbDataFormat)
            .process(dbProcessor).id("DbProcessor")
            .unmarshal(jaxbDataFormat)
            .marshal(jacksonDataFormat)
            .to("file://" + DESTINATION_FOLDER);
    }
}

