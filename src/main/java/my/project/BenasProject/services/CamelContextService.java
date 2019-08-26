package my.project.BenasProject.services;

import my.project.BenasProject.routes.builders.MyRoute;
import org.apache.camel.CamelContext;

public class CamelContextService {

    CamelContext context;

    public CamelContextService(CamelContext context) throws Exception {
        this.context = context;
        context.addRoutes(new MyRoute());
        context.start();
    }
}
