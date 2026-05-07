package de.muenchen.oss.praktikumsplaner.rest;

import static de.muenchen.oss.praktikumsplaner.TestUtils.getJwtAuthenticationToken;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import de.muenchen.oss.praktikumsplaner.domain.dtos.PraktikumsstelleViewDto;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsrichtung;
import de.muenchen.oss.praktikumsplaner.domain.enums.Praktikumsart;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiengang;
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

        final TypeReference<List<PraktikumsstelleViewDto>> praktikumsstelleListRef = new TypeReference<>() {
        };

        @ParameterizedTest(name = "when meldezeitraum is {0}")
        @ValueSource(strings = { "current", "most_recent" })
        void hasStelleOfAllStudiengang(final String meldezeitraumAlias) throws Exception {
            final MockHttpServletRequestBuilder request = createGetRequestWithZeitraum(meldezeitraumAlias);

            final MvcResult requestResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
            final List<PraktikumsstelleViewDto> responseBody = objectMapper.readValue(requestResult.getResponse().getContentAsByteArray(),
                    praktikumsstelleListRef);

            final List<Studiengang> studiengaenge = responseBody.stream()
                    .filter(dto -> dto.art() == Praktikumsart.STUDIUM)
                    .map(dto -> Studiengang.valueOf(dto.richtung().name()))
                    .toList();

            Assertions.assertThat(studiengaenge).containsOnly(Studiengang.values());
        }

        @ParameterizedTest(name = "when meldezeitraum is {0}")
        @ValueSource(strings = { "current", "most_recent" })
        void hasStellenOfAllAusbildungsrichtung(final String meldezeitraumAlias) throws Exception {
            final MockHttpServletRequestBuilder request = createGetRequestWithZeitraum(meldezeitraumAlias);

            final MvcResult requestResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
            final List<PraktikumsstelleViewDto> responseBody = objectMapper.readValue(requestResult.getResponse().getContentAsByteArray(),
                    praktikumsstelleListRef);

            List<Ausbildungsrichtung> ausbildungsrichtungen = responseBody.stream()
                    .filter(dto -> dto.art() == Praktikumsart.AUSBILDUNG)
                    .map(dto -> Ausbildungsrichtung.valueOf(dto.richtung().name()))
                    .toList();

            Assertions.assertThat(ausbildungsrichtungen).containsOnly(Ausbildungsrichtung.values());
        }
    }

    private MockHttpServletRequestBuilder createGetRequestWithZeitraum(final String meldezeitraumAlias) {
        return MockMvcRequestBuilders.get("/praktikumsstellen").param("meldezeitraum", meldezeitraumAlias);
    }

}
