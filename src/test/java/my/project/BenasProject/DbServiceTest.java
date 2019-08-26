package my.project.BenasProject;

import my.project.BenasProject.domain.ContactsInfo;
import my.project.BenasProject.services.DbService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbServiceTest {

    Logger log = LoggerFactory.getLogger(DbServiceTest.class);


    @Autowired
    DbService dbService;

    @Test
    public void testDatabaseConnection() throws SQLException {

        //Create contactInfo test object
        ContactsInfo contactsInfo = new ContactsInfo();
        contactsInfo.setName("Test");
        contactsInfo.setCompany("TestCompany");
        contactsInfo.setEmpty("");
        contactsInfo.setEmpty("TestFiller");
        contactsInfo.setPhone(234);


        //Database operations
        dbService.addEntry(contactsInfo);
        ResultSet findQueryResult = dbService.getExistingEntry("Test");

        while (findQueryResult.next()) {
            String name = findQueryResult.getString("name");
            if (name == "Test") {
                log.info("Database conectivity passed");
            }
        }
        dbService.removeEntryByName("Test");
    }
}
