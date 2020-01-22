package my.project.BenasProject.routes.processors;

import java.lang.reflect.Field;
import javax.xml.bind.JAXBException;
import my.project.BenasProject.Utils;
import my.project.BenasProject.domain.ContactsInfo;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class XmlEnrichProcessor implements Processor {

    String GENERATED_VALUE = "GeneratedValue";

    @Override
    public void process(Exchange exchange)
        throws JAXBException, IllegalAccessException {

        Message message = exchange.getIn();
        ContactsInfo contactsInfo = Utils.convertXmlToContactsInfo(message.getBody(String.class));

        ContactsInfo enrichedPayload = addDefaultFields(contactsInfo);
        message.setBody(enrichedPayload, ContactsInfo.class);
        exchange.setOut(message);
    }

    private ContactsInfo addDefaultFields(ContactsInfo contactsInfo) throws IllegalAccessException {
        for (Field field : contactsInfo.getClass().getDeclaredFields()) {
            String value = field.get(contactsInfo).toString();
            if (value.equals("")) {
                field.set(contactsInfo, GENERATED_VALUE);
            }
        }
        return contactsInfo;
    }
}
