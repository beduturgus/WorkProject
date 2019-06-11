package my.project.BenasProject;

import org.apache.http.HttpStatus;
import org.apache.http.protocol.HTTP;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class SpringSecurityTest {

//    TestRestTemplate restTemplate;
//    URL base;
//
//    int port = 8080;
//
//    @Before
//    public void setUp() throws MalformedURLException {
//        restTemplate = new TestRestTemplate("user", "password");
//        base = new URL("http://localhost:" + port);
//    }
//
//    @Test
//    public void whenLoggedUserRequestsHomePage_ThenSuccess() throws IllegalStateException, IOException {
//        ResponseEntity<String> response = restTemplate.getForEntity(base.toString(), String.class);
//
//        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
//        assertTrue(response.getBody().contains("banan"));
//    }
//
//    @Test
//    public void whenUserWithWrongCredentials() throws Exception {
//        restTemplate = new TestRestTemplate("user", "nogo");
//        ResponseEntity<String> response = restTemplate.getForEntity(base.toString(), String.class);
//
//        assertEquals(HttpStatus.SC_UNAUTHORIZED, response.getStatusCode());
//        assertTrue(response.getBody().contains("Unauthorized"));
//    }
}
