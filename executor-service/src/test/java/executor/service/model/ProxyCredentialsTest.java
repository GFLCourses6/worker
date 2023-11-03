package executor.service.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
}
