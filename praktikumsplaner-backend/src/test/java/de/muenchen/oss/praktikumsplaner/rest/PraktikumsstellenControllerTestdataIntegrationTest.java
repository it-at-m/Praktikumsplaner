package de.muenchen.oss.praktikumsplaner.rest;

import static de.muenchen.oss.praktikumsplaner.TestUtils.getJwtAuthenticationToken;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import de.muenchen.oss.praktikumsplaner.domain.dtos.AusbildungsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.StudiumsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsrichtung;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiengang;
import de.muenchen.oss.praktikumsplaner.security.Authorities;
import java.util.List;
import java.util.Objects;
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
    public void setUp() {
        var authentication = getJwtAuthenticationToken(Authorities.AuthoritiesEnum.AUSBILDUNGSLEITUNG);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @AfterEach
    public void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Nested
    class GetAllPraktiumsstellenInSpecificMeldezeitraum {

        final TypeReference<List<StudiumsPraktikumsstelleDto>> studiumsstelleTreeMapRef = new TypeReference<>() {
        };
        final TypeReference<List<AusbildungsPraktikumsstelleDto>> ausbildungsstelleTreeMapRef = new TypeReference<>() {
        };

        @ParameterizedTest(name = "when meldezeitraum is {0}")
        @ValueSource(strings = { "current", "most_recent" })
        void hasStelleOfAllStudiengang(final String meldezeitraumAlias) throws Exception {
            final MockHttpServletRequestBuilder request = createGetRequestWithZeitraum(meldezeitraumAlias);

            final MvcResult requestResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
            final List<StudiumsPraktikumsstelleDto> responseBody = objectMapper.readValue(requestResult.getResponse().getContentAsByteArray(),
                    studiumsstelleTreeMapRef);

            final List<Studiengang> studiengaenge = responseBody.stream()
                    .map(StudiumsPraktikumsstelleDto::studiengang)
                    .filter(Objects::nonNull)
                    .toList();

            Assertions.assertThat(studiengaenge).containsOnly(Studiengang.values());
        }

        @ParameterizedTest(name = "when meldezeitraum is {0}")
        @ValueSource(strings = { "current", "most_recent" })
        void hasStellenOfAllAusbildungsrichtung(final String meldezeitraumAlias) throws Exception {
            final MockHttpServletRequestBuilder request = createGetRequestWithZeitraum(meldezeitraumAlias);

            final MvcResult requestResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
            final List<AusbildungsPraktikumsstelleDto> responseBody = objectMapper.readValue(requestResult.getResponse().getContentAsByteArray(),
                    ausbildungsstelleTreeMapRef);

            List<Ausbildungsrichtung> ausbildungsrichtungen = responseBody.stream()
                    .map(AusbildungsPraktikumsstelleDto::ausbildungsrichtung)
                    .filter(Objects::nonNull)
                    .toList();

            Assertions.assertThat(ausbildungsrichtungen).containsOnly(Ausbildungsrichtung.values());
        }
    }

    private MockHttpServletRequestBuilder createGetRequestWithZeitraum(final String meldezeitraumAlias) {
        return MockMvcRequestBuilders.get("/praktikumsstellen").param("meldezeitraum", meldezeitraumAlias);
    }

}
