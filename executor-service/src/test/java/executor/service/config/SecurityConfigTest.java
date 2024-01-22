package executor.service.config;

import executor.service.security.RsaManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@EnableWebSecurity
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RsaManager rsaManager;

    @Test
    @WithMockUser
    void testSecurityFilterChainWithValidApiToken() throws Exception {
        String validToken = "client-token";
        String encryptedToken = rsaManager.encrypt(validToken);

        mockMvc.perform(MockMvcRequestBuilders.get("/result")
                        .header("Authorization", "Token " + encryptedToken))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @WithMockUser
    void testSecurityFilterChainWithInvalidApiToken() throws Exception {
        String invalidToken = "invalidToken";

        mockMvc.perform(MockMvcRequestBuilders.get("/result")
                        .header("Authorization", "Token " + invalidToken))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser
    void testSecurityFilterChainWithMissingApiToken() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/result"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

}
