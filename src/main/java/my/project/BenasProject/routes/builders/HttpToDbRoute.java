package my.project.BenasProject.routes.builders;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
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
            .routeId("HttpToDbRoute")
            .log("Payload went through")
            .process(validationProcessor)
            .process(xmlEnrichProcessor)
            .process(dbProcessor)
            .to("file://" + DESTINATION_FOLDER);
    }
}
