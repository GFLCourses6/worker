package executor.service.model;

import executor.service.model.dto.ProxyCredentials;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
public class ProxyCredentialsTest {
    private ProxyCredentials credentials1;
    private ProxyCredentials credentials2;

    @BeforeEach
    void setUp() {
        credentials1 = new ProxyCredentials("username1", "password");
        credentials2 = new ProxyCredentials("username2", "password2");
    }

    @Test
    void getUsername() {
        assertEquals("username1", credentials1.getUsername());
        assertEquals("username2", credentials2.getUsername());
    }

    @Test
    void getPassword() {
        assertEquals("password", credentials1.getPassword());
        assertEquals("password2", credentials2.getPassword());
    }

    @Test
    void setUsername() {
        credentials1.setUsername("newUserName");
        assertEquals("newUserName", credentials1.getUsername());
    }

    @Test
    void setPassword() {
        credentials2.setPassword("newPassword");
        assertEquals("newPassword", credentials2.getPassword());
    }

    @Test
    void testEquals() {
        ProxyCredentials saveCredentials =
                new ProxyCredentials("username1", "password");
        assertEquals(credentials1, saveCredentials);
    }

    @Test
    void testEqualsDifferentCredentials(){
        assertNotEquals(credentials1, credentials2);
    }

    @Test
    void testHashCode() {
        int expectedHashCode = credentials1.hashCode();
        assertEquals(expectedHashCode, credentials1.hashCode());
    }

    @Test
    void testEqualsAndHashCode() {
        ProxyCredentials credentials2 = new ProxyCredentials();
        credentials2.setUsername("username1");
        credentials2.setPassword("password");

        assertEquals(credentials1, credentials2);
        assertEquals(credentials1.hashCode(), credentials2.hashCode());
    }
}
