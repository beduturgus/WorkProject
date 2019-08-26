package my.project.BenasProject.services;

import my.project.BenasProject.domain.ContactsInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

    Connection con;

    private String findQuery = "SELECT name FROM contactsinfo WHERE name = ";

    private String insertQuery = "INSERT INTO contactsinfo (name, company, phone, empty, filler) " +
            "VALUES (?, ?, ?, ?, ?)";

    private String removeQuery = "DELETE FROM contactsinfo WHERE name= ";

    public void DbService() throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
    }


    public ResultSet addEntry(ContactsInfo contactsInfo) throws SQLException {
        con = DriverManager.getConnection(url, user,password);
        PreparedStatement st = con.prepareStatement(insertQuery);
        st.setString(1, contactsInfo.getName());
        st.setString(2, contactsInfo.getCompany());
        st.setInt(3, contactsInfo.getPhone());
        st.setString(4, contactsInfo.getEmpty());
        st.setString(5, contactsInfo.getFiller());
        ResultSet rs = st.executeQuery();
        con.close();
        log.info("New contactsInfo entry was added to database");
        return rs;
    }

    public ResultSet getExistingEntry(String name) throws SQLException{
        con = DriverManager.getConnection(url, user,password);
        PreparedStatement st = con.prepareStatement(findQuery + "'" + name + "'");
        ResultSet rs = st.executeQuery();

            if (rs.next()) {
                log.info("Entry for name {} found: ", name);
            } else {
                log.info("Entry for name {} does not exist in database", name);
            }
            con.close();
        return rs;
    }

    public void removeEntryByName(String name) throws SQLException {
        con = DriverManager.getConnection(url, user,password);
        PreparedStatement st = con.prepareStatement(removeQuery + name);
        st.executeQuery();
        con.close();
    }


    public Connection getConnection(){
        return this.con;
    }


    //TODO Implement to execute script on every launch
//    @PostConstruct
//    public void init() {
//        ProcessBuilder processBuilder = new ProcessBuilder();
//
//        try {
//            processBuilder.command("bash", "curl -i -X POST -d username=user -d password=userPass -c /Users/benas/Notes/security/cookies.txt  http://localhost:8089/login");
//
//            Process process = processBuilder.start();
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//
//            String line;
//
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
