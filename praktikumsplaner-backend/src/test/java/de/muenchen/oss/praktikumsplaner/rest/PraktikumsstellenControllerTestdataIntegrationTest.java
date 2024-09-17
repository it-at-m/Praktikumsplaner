package de.muenchen.oss.praktikumsplaner.rest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import de.muenchen.oss.praktikumsplaner.domain.dtos.AusbildungsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.StudiumsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsrichtung;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiengang;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class PraktikumsstellenControllerTestdataIntegrationTest extends AbstractTestdataIntegrationTest {

    @Nested
    class GetAllPraktiumsstellenInSpecificMeldezeitraum {

        final TypeReference<TreeMap<String, List<StudiumsPraktikumsstelleDto>>> studiumsstelleTreeMapRef = new TypeReference<>() {
        };
        final TypeReference<TreeMap<String, List<AusbildungsPraktikumsstelleDto>>> ausbildungsstelleTreeMapRef = new TypeReference<>() {
        };

        @ParameterizedTest(name = "when meldezeitraum is {0}")
        @ValueSource(strings = { "current", "most_recent" })
        void hasStelleOfAllStudiengang(final String meldezeitraumAlias) throws Exception {
            val request = createGetRequestWithZeitraum(meldezeitraumAlias);

            val requestResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
            val responseBody = objectMapper.readValue(requestResult.getResponse().getContentAsByteArray(), studiumsstelleTreeMapRef);

            val studiengaenge = responseBody.values().stream().flatMap(Collection::stream).map(StudiumsPraktikumsstelleDto::studiengang)
                    .filter(Objects::nonNull).toList();

            Assertions.assertThat(studiengaenge).containsOnly(Studiengang.values());
        }

        @ParameterizedTest(name = "when meldezeitraum is {0}")
        @ValueSource(strings = { "current", "most_recent" })
        void hasStellenOfAllAusbildungsrichtung(final String meldezeitraumAlias) throws Exception {
            val request = createGetRequestWithZeitraum("current");

            val requestResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
            val responseBody = objectMapper.readValue(requestResult.getResponse().getContentAsByteArray(), ausbildungsstelleTreeMapRef);

            val studiengaenge = responseBody.values().stream().flatMap(Collection::stream).map(AusbildungsPraktikumsstelleDto::ausbildungsrichtung)
                    .filter(Objects::nonNull).toList();

            Assertions.assertThat(studiengaenge).containsOnly(Ausbildungsrichtung.values());
        }
    }

    private MockHttpServletRequestBuilder createGetRequestWithZeitraum(final String meldezeitraumAlias) {
        return MockMvcRequestBuilders.get("/praktikumsstellen").param("meldezeitraum", meldezeitraumAlias);
    }

}
