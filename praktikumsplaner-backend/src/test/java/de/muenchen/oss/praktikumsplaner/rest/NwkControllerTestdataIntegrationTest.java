package de.muenchen.oss.praktikumsplaner.rest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.muenchen.oss.praktikumsplaner.domain.dtos.NwkDto;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsrichtung;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiengang;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class NwkControllerTestdataIntegrationTest extends AbstractTestdataIntegrationTest {

    @Nested
    class GetNwks {

        @Test
        void hasNwksForAllDirectionsWhenRequestingWithStatusActive() throws Exception {
            val request = createGetRequestWithPeriod("aktiv", null);

            val requestResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
            val responseBody = objectMapper.readValue(requestResult.getResponse().getContentAsString(), NwkDto[].class);

            val ausbildungsrichtungen = Arrays.stream(responseBody)
                    .map(NwkDto::ausbildungsrichtung)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            val studiumsrichtungen = Arrays.stream(responseBody)
                    .map(NwkDto::studiengang)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            Assertions.assertThat(ausbildungsrichtungen).containsOnly(Ausbildungsrichtung.values());
            Assertions.assertThat(studiumsrichtungen).containsOnly(Studiengang.values());
        }

        @Test
        void hasJahrgangOfTheCurrentAndUpToLastTwoYearsWhenRequestingWithStatusActive() throws Exception {
            val request = createGetRequestWithPeriod("aktiv", null);

            val requestResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
            val responseBody = objectMapper.readValue(requestResult.getResponse().getContentAsString(), NwkDto[].class);

            val jahrgaengeOfNwks = Arrays.stream(responseBody).map(NwkDto::jahrgang).collect(Collectors.toSet());
            val expectedJahrgaenge = new String[] { createJahrgangStarting(LocalDate.now()),
                    createJahrgangStarting(LocalDate.now().minusYears(1)),
                    createJahrgangStarting(LocalDate.now().minusYears(2)) };

            Assertions.assertThat(jahrgaengeOfNwks).containsOnly(expectedJahrgaenge);
        }

        private MockHttpServletRequestBuilder createGetRequestWithPeriod(final String optionalStatusParameter, final String optionalUnassignedParameter) {
            val getRequest = MockMvcRequestBuilders.get("/nachwuchskraft");

            if (optionalStatusParameter != null) {
                getRequest.param("status", optionalStatusParameter);
            }
            if (optionalUnassignedParameter != null) {
                getRequest.param("unassigned", optionalUnassignedParameter);
            }

            return getRequest;
        }

        private String createJahrgangStarting(final LocalDate startingDate) {
            return startingDate.getYear() % 100 + "/" + startingDate.plusYears(3).getYear() % 100;
        }

    }

}
