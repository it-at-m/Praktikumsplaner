package de.muenchen.oss.praktikumsplaner.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.muenchen.oss.praktikumsplaner.MicroServiceApplication;
import de.muenchen.oss.praktikumsplaner.TestConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = MicroServiceApplication.class)
@ActiveProfiles({ "local", "db-h2", TestConstants.SPRING_NO_SECURITY_PROFILE })
@AutoConfigureMockMvc
public class AbstractTestdataIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
}
