package my.project.BenasProject.routes;

import my.project.BenasProject.routes.builders.FileRoute;
import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RouteServiceImpl {

    private CamelContext camelContext;

    @Autowired
    public RouteServiceImpl(final CamelContext camelContext) throws Exception {
        this.camelContext = camelContext;
        this.camelContext.addRoutes(new FileRoute());
    }
}
