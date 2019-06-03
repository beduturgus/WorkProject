package my.project.BenasProject.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;

@Service
public class PayloadEnrichService {

    private final String GENERATED_VALUE = "Generated value";

    public String enrichPayload(String payload) throws SAXException, JAXBException {

//        File xmlFile = new File(payload);
//
//        JAXBContext jaxbContext = JAXBContext.newInstance(ContactsInfo.class);
//
//        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//
//        ContactsInfo contactsInfo = (ContactsInfo) jaxbUnmarshaller.unmarshal(new StringReader(payload));




        NodeList nodes = convertStringToDocument(payload).getDocumentElement().getElementsByTagName("*");
        Element element = convertStringToDocument(payload).getDocumentElement();
        ArrayList<String> names = new ArrayList<>();


        for (int i = 0; i < nodes.getLength(); i++){
            names.add(nodes.item(i).getNodeName());
        }

        for(int i = 0; i < names.size(); i++){

            try{
                if(element.getElementsByTagName(names.get(i)).item(0).getFirstChild() == null){
                    System.out.println("Value will generate");
                    element.getElementsByTagName(names.get(i)).item(0).setTextContent(GENERATED_VALUE);
                    System.out.println("Value was generated");
                }
            }catch(NullPointerException e){
                System.out.println(e);
            }
        }





        System.out.println("Are here");
        return convertElementToString(element);
    }

    public Document convertStringToDocument(String str){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = null;

        InputStream stream = new ByteArrayInputStream(str.getBytes());

        try
        {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();

            //Parse the content to Document object
            Document doc = builder.parse(stream);
            return doc;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public String convertElementToString(Element element){
//        Document doc = element;
        return "belekas";
    }

    public void mapContactsInfoDTO(String payload){
        ModelMapper mapper = new ModelMapper();
//        ContactsInfoDTO contactsInfoDTO = mapper.map();
    }

//    public void replaceEmptyNodes(ArrayList<String> names){
//
//
//    }
}
