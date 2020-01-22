package my.project.BenasProject.routes.builders;

import java.io.IOException;
import java.sql.SQLException;
import javax.xml.bind.JAXBException;
import org.apache.camel.ErrorHandlerFactory;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spi.RouteContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HttpToDbRoute extends RouteBuilder {

    final String DESTINATION_FOLDER = "/Users/benas/PROJECTS/Playground/BenasProject/data/routes/route_output";

    @Autowired
    private Processor validationProcessor;

    @Autowired
    private Processor xmlEnrichProcessor;

    @Autowired
    private Processor dbProcessor;

    @Override
    public void configure() {
        from("jetty:http://0.0.0.0:8090/postNothing")
            //TODO error handling to be added
            .routeId("HttpToDbRoute")
            .log("Payload went through")
            .process(validationProcessor)
            .process(xmlEnrichProcessor)
            .process(dbProcessor)
            .to("file://" + DESTINATION_FOLDER);
    }
}

