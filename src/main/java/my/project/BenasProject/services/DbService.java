package my.project.BenasProject.services;

import my.project.BenasProject.domain.Contactsinfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class DbService {

    private final Logger log = LoggerFactory.getLogger(DbService.class);

    @Value("${database.url}")
    String url;

    @Value("${database.user}")
    String user;

    @Value("${database.password}")
    String password;

    private String findQuery = "SELECT name FROM contactsinfo WHERE name = ";

    private String insertQuery = "INSERT INTO contactsinfo (name, company, phone, empty, filler) " +
            "VALUES (?, ?, ?, ?, ?)";


    public void addEntry(Contactsinfo contactsinfo) throws SQLException {
        Connection con = DriverManager.getConnection(url, user, password);
        PreparedStatement st = con.prepareStatement(insertQuery);
        st.setString(1, contactsinfo.getName());
        st.setString(2, contactsinfo.getCompany());
        st.setInt(3, contactsinfo.getPhone());
        st.setString(4, contactsinfo.getEmpty());
        st.setString(5, contactsinfo.getFiller());
        st.execute();
        con.close();
        log.info("New contactsinfo entry was added to database");
    }

    public boolean entryExisists(String name) throws SQLException {
        Connection con = DriverManager.getConnection(url, user, password);
        PreparedStatement st = con.prepareStatement(findQuery + "'" + name + "'");
        ResultSet rs = st.executeQuery();
        if(rs.next()){
            log.info("Entry already exist for name: " + name);
            return true;
        }else{
            log.info("Entry does not exist for name: " + name);
            return false;
        }
    }
}
