package my.project.BenasProject;

import com.google.common.io.Resources;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import my.project.BenasProject.domain.ContactsInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {

    private static Logger log = LoggerFactory.getLogger(Utils.class);

    public static ContactsInfo convertXmlToContactsInfo(String body) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(ContactsInfo.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        log.info("Converting body to ContactsInfo");
        return (ContactsInfo) unmarshaller.unmarshal(new StringReader(body));
    }

    public static String getResource(String filename) {
        URL url = Resources.getResource(filename);
        String text = null;
        try {
            text = Resources.toString(url, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Wrong resource name. Error: {}", e.getMessage());
        }
        return text;
    }
}
