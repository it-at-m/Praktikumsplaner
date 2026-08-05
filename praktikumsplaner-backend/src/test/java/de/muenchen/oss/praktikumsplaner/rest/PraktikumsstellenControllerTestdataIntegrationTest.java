package de.muenchen.oss.praktikumsplaner.rest;

import static de.muenchen.oss.praktikumsplaner.TestUtils.getJwtAuthenticationToken;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import de.muenchen.oss.praktikumsplaner.domain.dtos.PraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.enums.Bildungsrichtung;
import de.muenchen.oss.praktikumsplaner.security.Authorities;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class PraktikumsstellenControllerTestdataIntegrationTest extends AbstractTestdataIntegrationTest {

    @BeforeEach
    void setUp() {
        var authentication = getJwtAuthenticationToken(Authorities.AuthoritiesEnum.AUSBILDUNGSLEITUNG);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Nested
    class GetAllPraktiumsstellenInSpecificMeldezeitraum {

        final TypeReference<List<PraktikumsstelleDto>> praktikumsstellenRef = new TypeReference<>() {
        };

        @ParameterizedTest(name = "when meldezeitraum is {0}")
        @ValueSource(strings = { "current", "most_recent" })
        void hasStellenOfAllRichtungen(final String meldezeitraumAlias) throws Exception {
            final MockHttpServletRequestBuilder request = createGetRequestWithZeitraum(meldezeitraumAlias);

            final MvcResult requestResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
            final List<PraktikumsstelleDto> responseBody = objectMapper.readValue(
                    requestResult.getResponse().getContentAsByteArray(),
                    praktikumsstellenRef);

            final List<Bildungsrichtung> richtungen = responseBody.stream()
                    .map(PraktikumsstelleDto::richtung)
                    .toList();

            Assertions.assertThat(richtungen).doesNotContainNull();
            Assertions.assertThat(richtungen).containsOnly(Bildungsrichtung.values());
        }
    }

    private MockHttpServletRequestBuilder createGetRequestWithZeitraum(final String meldezeitraumAlias) {
        return MockMvcRequestBuilders.get("/praktikumsstellen").param("meldezeitraum", meldezeitraumAlias);
    }
}
