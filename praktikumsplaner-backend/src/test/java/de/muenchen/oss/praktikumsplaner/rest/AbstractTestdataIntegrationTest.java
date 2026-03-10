package de.muenchen.oss.praktikumsplaner.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.muenchen.oss.praktikumsplaner.MicroServiceApplication;
import de.muenchen.oss.praktikumsplaner.TestConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import static de.muenchen.oss.praktikumsplaner.TestConstants.SPRING_TEST_PROFILE;

@SpringBootTest(classes = MicroServiceApplication.class)
@ActiveProfiles({ SPRING_TEST_PROFILE, TestConstants.SPRING_NO_SECURITY_PROFILE })
@AutoConfigureMockMvc
public abstract class AbstractTestdataIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @ServiceConnection
    @SuppressWarnings("unused")
    private static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>(
            DockerImageName.parse(TestConstants.TESTCONTAINERS_POSTGRES_IMAGE));
}
