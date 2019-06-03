package my.project.BenasProject.rest;

import my.project.BenasProject.services.PayloadEnrichService;
import my.project.BenasProject.services.XMLValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api")
public class RestEnpoints {

    @Autowired
    XMLValidation validator;

    @Autowired
    PayloadEnrichService payloadEnrichService;

    private final Logger log = LoggerFactory.getLogger(RestEnpoints.class);

    public static final String XML_FILE = "note.xml";
    public static final String SCHEMA_FILE = "note.xsd";

    public RestEnpoints(){
        log.debug("Instance of Endpoints class created");
        System.out.println("Instance of Endpoints class created sout");
    }

    @PostMapping("/postNothing")
    public void getResponseEntity(@RequestBody String body) {

        boolean valid = validator.validate(body, SCHEMA_FILE);


//        System.out.println("XML is valid:" + valid);

        System.out.println(body);

//        payloadEnrichService.enrichPayload(body);





    }
}
