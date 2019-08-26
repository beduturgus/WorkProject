package my.project.BenasProject.rest;

import my.project.BenasProject.domain.ContactsInfo;
import my.project.BenasProject.services.DbService;
import my.project.BenasProject.services.PayloadEnrichService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;


@RestController
@RequestMapping("/api")
public class RestEnpoints {

    @Autowired
    PayloadEnrichService payloadEnrichService;

    @Autowired
    DbService dbService;

    private final Logger log = LoggerFactory.getLogger(RestEnpoints.class);

    public static final String SCHEMA_FILE = "note.xsd";

    @GetMapping("/getNothing")
    public void getResponseEntity(){
        System.out.println("Get response went through");
    }

    @PostMapping("/postNothing")
    public void postResponseEntity(@RequestBody String body) throws IOException, JAXBException, SAXException, SQLException, IllegalAccessException {

        System.out.println(body);

//        //Write validated XML to file
//        FileWriter writer = new FileWriter("/Users/benas/PROJECTS/WorkProject/data/consumable.xml");
//        writer.write(body);
//        writer.close();
//
//        //Convert Received XML to object
//        ContactsInfo contactsInfo = payloadEnrichService.convertToObject();
//
//        //Check if entry exists in database
//        boolean entryExsits = dbService.entryExisists(contactsInfo.getName());
//
//        //Store in database if entry does not exsist
//        if(!entryExsits){
//            //Add generated value for fields with empty value
//            ContactsInfo enrichedContactsInfo = payloadEnrichService.enrichPayload(contactsInfo);
//            dbService.addEntry(enrichedContactsInfo);
//            //Save json in file system
//            payloadEnrichService.jacksonPojoToJson(enrichedContactsInfo);
//        }
    }
}
