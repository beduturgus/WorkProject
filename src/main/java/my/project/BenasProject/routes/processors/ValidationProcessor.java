package my.project.BenasProject.routes.processors;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.Objects;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;


/**
 * Component responsible for validating against xsd scheema stored in
 * resources/contactsInfoSchema.xsd and sending it out.
 */
@Component
public class ValidationProcessor implements Processor {

    private final Logger LOGGER = LoggerFactory.getLogger(ValidationProcessor.class);

    @Override
    public void process(Exchange exchange) throws IOException {
        Message message = exchange.getIn();
        String body = message.getBody(String.class);
        boolean valid = isValid(body, "contactsInfoSchema.xsd");
        if (valid) {
            message.setBody(body);
            exchange.setOut(message);
        }
    }

    private boolean isValid(String xmlFile, String xsdFile) throws IOException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        StreamSource xmlStream = new StreamSource(new StringReader(xmlFile));
        try {
            Schema schema = schemaFactory.newSchema(new File(getResource(xsdFile)));
            Validator validator = schema.newValidator();
            validator.validate(xmlStream);
            LOGGER.info("Received XML is valid");
            return true;
        } catch (SAXException exception) {
            LOGGER.warn("XML is invalid. Reason: " + exception);
            return false;
        }
    }

    private String getResource(String fileName) {
        URL resource = getClass().getClassLoader().getResource(fileName);
        Objects.requireNonNull(resource);
        return resource.getFile();
    }
}
