package my.project.BenasProject.routes.processors;

import java.lang.reflect.Field;
import java.util.Arrays;
import javax.xml.bind.JAXBException;
import my.project.BenasProject.Utils;
import my.project.BenasProject.domain.ContactsInfo;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Component responsible for finding empty fields in received payload, filling them with default
 * value and sending it out.
 */
@Component
public class XmlEnrichProcessor implements Processor {

    Logger LOGGER = LoggerFactory.getLogger(XmlEnrichProcessor.class);

    String GENERATED_VALUE = "GeneratedValue";

    @Override
    public void process(Exchange exchange)
        throws IllegalAccessException {

        Message message = exchange.getIn();
        ContactsInfo contactsInfo = message.getBody(ContactsInfo.class);

        ContactsInfo enrichedPayload = addDefaultFields(contactsInfo);
        message.setBody(enrichedPayload, ContactsInfo.class);
    }

    private ContactsInfo addDefaultFields(ContactsInfo contactsInfo) throws IllegalAccessException {
        int count = 0;

        for (Field field : contactsInfo.getClass().getDeclaredFields()) {
            String value = field.get(contactsInfo).toString();
            if (value.equals("")) {
                count++;
                field.set(contactsInfo, GENERATED_VALUE);
            }
        }
        LOGGER.info(count + " fields were empty and now filled with string: " + GENERATED_VALUE);
        return contactsInfo;
    }
}
