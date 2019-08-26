package my.project.BenasProject.routes.builders;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyRoute extends RouteBuilder {

    private static final String SOURCE_FILE = "Users/benas/PROJECTS/Playground/BenasProject/data/routes/route_input/cookies.txt";
    private static final String DESTINATION_FILE = "Users/benas/PROJECTS/Playground/BenasProject/data/routes/route_output";

    @Override
    public void configure() {
        from("quartz2://logMessageGroup/logMessageTimer?cron=0/5+*+*+*+*+?")
                .log(LoggingLevel.INFO, "Route was activated");
//                .to("file://" + DESTINATION_FILE);

    }
}
