package my.project.BenasProject.routes.processors;

import com.google.gson.Gson;
import java.sql.*;
import javax.annotation.PreDestroy;
import javax.xml.bind.JAXBException;
import my.project.BenasProject.Utils;
import my.project.BenasProject.domain.ContactsInfo;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.converter.stream.InputStreamCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Component responsible for storing received data to database, converting to JSON format and
 * sending it out.
 */
@Component
public class DbProcessor implements Processor {

    private final Logger LOGGER = LoggerFactory.getLogger(DbProcessor.class);

    @Value("${database.url}")
    String url;

    @Value("${database.user}")
    String user;

    @Value("${database.password}")
    String password;

    Connection con;

    private String insertQuery = "INSERT INTO contactsinfo (name, company, phone, empty, filler) " +
        "VALUES (?, ?, ?, ?, ?)";


    @Override
    public void process(Exchange exchange) throws SQLException {
        Message message = exchange.getIn();
        ContactsInfo contactsInfo = message.getBody(ContactsInfo.class);
        persistData(contactsInfo);
        message.setBody(contactsInfo);
    }

    public void persistData(ContactsInfo contactsInfo) throws SQLException {
        con = DriverManager.getConnection(url, user, password);
        PreparedStatement st = con.prepareStatement(insertQuery);
        st.setString(1, contactsInfo.getName());
        st.setString(2, contactsInfo.getCompany());
        st.setString(3, contactsInfo.getPhone().toString());
        st.setString(4, contactsInfo.getEmpty());
        st.setString(5, contactsInfo.getFiller());
        st.executeUpdate();
        st.close();
        LOGGER.info("ContactsInfo was saved into database" + contactsInfo.toString());
    }

//    public String convertToJsonString(Object object) {
//        Gson gson = new Gson();
//        return gson.toJson(object);
//    }

    public ContactsInfo checkMessageType(Message message) {
        ContactsInfo result = null;
        if (message.getBody() instanceof InputStreamCache) {
            try {
                result = Utils.convertXmlToContactsInfo(message.getBody(String.class));
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        } else {
            result = message.getBody(ContactsInfo.class);
        }
        System.out.println("sdf");
        return result;
    }

    @PreDestroy
    public void closeConnection() throws SQLException {
        con.close();
    }
}
