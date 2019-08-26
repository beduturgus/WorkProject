package my.project.BenasProject.routes.builders;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class RestRoute extends RouteBuilder {

    private Processor validationProcessor;

    public RestRoute(Processor validationProcessor){
        this.validationProcessor = validationProcessor;
    }

    @Override
    public void configure() {
        rest("/api")
                .post("/postNothing")
                .to("direct:validate");

        from("direct:validate")
            .process(validationProcessor)
            .to()
    }
}
