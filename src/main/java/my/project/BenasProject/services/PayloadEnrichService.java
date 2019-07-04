package my.project.BenasProject.services;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import my.project.BenasProject.domain.Contactsinfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.util.FieldUtils;
import org.springframework.stereotype.Service;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.Date;

@Service
public class PayloadEnrichService {

    private final Logger log = LoggerFactory.getLogger(PayloadEnrichService.class);

    private final String jsonPath = "/Users/benas/PROJECTS/WorkProject/data/json/contactsinfo.json";
    private final String GENERATED_VALUE = "GeneratedValue";
    private final String[] fields = new String[]{"name", "company", "phone", "empty", "filler"};
    Date date = new Date();



    public Contactsinfo convertToObject() throws JAXBException {
        File file = new File("/Users/benas/PROJECTS/WorkProject/data/consumable.xml");
        JAXBContext jaxbContext = JAXBContext.newInstance(Contactsinfo.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Contactsinfo contactsInfo = (Contactsinfo) jaxbUnmarshaller.unmarshal(file);
        log.info("XML is converted to object");
        return contactsInfo;
    }

    public void jacksonPojoToJson(Contactsinfo contactsinfo) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        //Convert object to string
        String json = mapper.writeValueAsString(contactsinfo);

        //Write JSON string to file
        FileWriter fileWriter = new FileWriter(jsonPath, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(json + '\n' + date.getTime() + '\n' + '\n');

        bufferedWriter.close();
        fileWriter.close();
        log.info("File was saved as JSON");
    }

    public Contactsinfo enrichPayload(Contactsinfo contactsinfo) throws IllegalAccessException {
        for(String field : fields){
            if (FieldUtils.getFieldValue(contactsinfo, field).equals("")){
                contactsinfo.setValueByFieldName(field, GENERATED_VALUE);
                log.info("Generated value was added for: " + field);
            }
        }
        return contactsinfo;
    }

}
