package my.project.BenasProject.services;

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
public class XMLValidation {

    public boolean validate(String xmlFile, String xsdFile){

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Reader reader = new StringReader(xmlFile);

        try{
            Schema schema = schemaFactory.newSchema(new File(getResource(xsdFile)));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(reader));
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (SAXException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getResource(String fileName) throws FileNotFoundException {
        URL resource = getClass().getClassLoader().getResource(fileName);
        Objects.requireNonNull(resource);

        return resource.getFile();
    }
}
