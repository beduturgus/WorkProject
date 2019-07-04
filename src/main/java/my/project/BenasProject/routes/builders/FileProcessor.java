package my.project.BenasProject.routes.builders;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

public class FileProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

//        Message message = exchange.getMessage();
//        String body = message.getBody();
        Message payload = exchange.getIn();
        String str = payload.getBody().toString();
    }
}
