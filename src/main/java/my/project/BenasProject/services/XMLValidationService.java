package my.project.BenasProject.services;

import my.project.BenasProject.rest.RestEnpoints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.net.URL;
import java.util.Objects;

@Service
public class XMLValidationService {

    private final Logger log = LoggerFactory.getLogger(XMLValidationService.class);

    public void validate(String xmlFile, String xsdFile) throws IOException, SAXException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Reader reader = new StringReader(xmlFile);
        Schema schema = schemaFactory.newSchema(new File(getResource(xsdFile)));
        Validator validator = schema.newValidator();
        validator.validate(new StreamSource(reader));
        log.info("Received XML is valid");
    }

    private String getResource(String fileName) {
        URL resource = getClass().getClassLoader().getResource(fileName);
        Objects.requireNonNull(resource);
        return resource.getFile();
    }
}
