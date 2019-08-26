package my.project.BenasProject.routes.processors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import my.project.BenasProject.domain.ContactsInfo;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.util.FieldUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class XmlEnrichProcessor implements Processor {

    private final Logger LOGGER = LoggerFactory.getLogger(XmlEnrichProcessor.class);


    private final String jsonPath = "/Users/benas/PROJECTS/WorkProject/data/json/contactsinfo.json";
    private final String GENERATED_VALUE = "GeneratedValue";
    private final String[] fields = new String[]{"name", "company", "phone", "empty", "filler"};
    Date date = new Date();

    @Override
    public void process(Exchange exchange) {
        Message message = exchange.getIn();
        String body = (String) message.getBody();
    }

    public ContactsInfo convertToObject(String xmlPayload) throws JAXBException {
        File file = new File("/Users/benas/PROJECTS/WorkProject/data/consumable.xml");
        JAXBContext jaxbContext = JAXBContext.newInstance(ContactsInfo.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        ContactsInfo contactsInfo = (ContactsInfo) jaxbUnmarshaller.unmarshal(file);
        LOGGER.info("XML is converted to object");
        return contactsInfo;
    }

    public void jacksonPojoToJson(ContactsInfo contactsInfo) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        //Convert object to string
        String json = mapper.writeValueAsString(contactsInfo);

        //Write JSON string to file
        try (
            FileWriter fileWriter = new FileWriter(jsonPath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        ) {
            bufferedWriter.write(json + '\n' + date.getTime() + '\n' + '\n');
        }
        LOGGER.info("File was saved as JSON");
    }

    public ContactsInfo enrichPayload(ContactsInfo contactsInfo) throws IllegalAccessException {
        for (String field : fields) {
            if (FieldUtils.getFieldValue(contactsInfo, field).equals("")) {
                contactsInfo.setValueByFieldName(field, GENERATED_VALUE);
                LOGGER.info("Generated value was added for: " + field);
            }
        }
        return contactsInfo;
    }
}
