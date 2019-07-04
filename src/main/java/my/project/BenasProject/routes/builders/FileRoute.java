package my.project.BenasProject.routes.builders;

import org.apache.camel.builder.RouteBuilder;


public class FileRoute extends RouteBuilder {

    private static final String SOURCE_FILE = "/Users/benas/PROJECTS/WorkProject/data/consumable.xml";

    @Override
    public void configure() {
        from("file://" + SOURCE_FILE + "?delete=true")
                .process(new FileProcessor());
//                .to("pgevent:dataSource[?options]");

    }
}
