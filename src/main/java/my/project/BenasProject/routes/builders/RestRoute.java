package my.project.BenasProject.routes.builders;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class RestRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        rest("/api")
                .post("/postNothing")
                .to("file://Users/benas/PROJECTS/Playground/BenasProject/data/routes/route_input/output.txt");

    }
}
