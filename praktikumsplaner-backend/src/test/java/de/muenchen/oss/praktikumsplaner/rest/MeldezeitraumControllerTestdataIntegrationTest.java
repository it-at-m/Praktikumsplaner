package de.muenchen.oss.praktikumsplaner.rest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.muenchen.oss.praktikumsplaner.domain.dtos.MeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.ZeitraumDto;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class MeldezeitraumControllerTestdataIntegrationTest extends AbstractTestdataIntegrationTest {

    @Nested
    class GetMeldezeitraeume {

        @Test
        void shouldReturnMeldezeitraumOfNextYearsWhenPeriodIsFuture() throws Exception {
            val request = createGetRequestWithPeriod("future");

            val requestResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
            val responseBody = objectMapper.readValue(requestResult.getResponse().getContentAsByteArray(), MeldezeitraumDto[].class);

            Assertions.assertThat(responseBody).allMatch(meldezeitraumDto -> meldezeitraumIsAfterCurrentYear(meldezeitraumDto.zeitraum()));
        }

        @Test
        void shouldReturnMeldezeitraumOfPreviousYearsWhenPeriodIsPast() throws Exception {
            val request = createGetRequestWithPeriod("past");

            val requestResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
            val responseBody = objectMapper.readValue(requestResult.getResponse().getContentAsByteArray(), MeldezeitraumDto[].class);

            Assertions.assertThat(responseBody).allMatch(meldezeitraumDto -> meldezeitraumIsBeforeCurrentYear(meldezeitraumDto.zeitraum()));
        }

        @Test
        void shouldReturnMeldezeitraumOfCurrentYearWhenPeriodIsCurrent() throws Exception {
            val request = createGetRequestWithPeriod("current");

            val requestResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
            val responseBody = objectMapper.readValue(requestResult.getResponse().getContentAsByteArray(), MeldezeitraumDto[].class);

            val expectedName = "Jahr " + (LocalDate.now().getYear() % 100);
            val expectedZeitraum = new ZeitraumDto(LocalDate.now().with(TemporalAdjusters.firstDayOfYear()),
                    LocalDate.now().with(TemporalAdjusters.lastDayOfYear()));
            val expectedMeldezeitraum = new MeldezeitraumDto(UUID.fromString("00000000-0000-0000-0000-000000000001"), expectedName, expectedZeitraum);

            Assertions.assertThat(responseBody).hasSize(1);
            Assertions.assertThat(responseBody[0]).isEqualTo(expectedMeldezeitraum);
        }

        @Test
        void shouldReturnAllMeldezeitraeumeWhenPeriodIsNull() throws Exception {
            val request = createGetRequestWithPeriod(null);

            val requestResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
            val responseBody = objectMapper.readValue(requestResult.getResponse().getContentAsByteArray(), MeldezeitraumDto[].class);

            val idsOfResponse = Arrays.stream(responseBody).map(MeldezeitraumDto::id).collect(Collectors.toSet());
            Assertions.assertThat(idsOfResponse).hasSize(7);
        }

        private MockHttpServletRequestBuilder createGetRequestWithPeriod(final String periodParameterValue) {
            val getRequest = MockMvcRequestBuilders.get("/meldezeitraum");
            if (periodParameterValue != null) {
                getRequest.param("period", periodParameterValue);
            }

            return getRequest;
        }

        private boolean meldezeitraumIsAfterCurrentYear(final ZeitraumDto zeitraum) {
            val currentYear = LocalDate.now().getYear();
            return zeitraum.startZeitpunkt().getYear() > currentYear && zeitraum.endZeitpunkt()
                    .getYear() > currentYear;
        }

        private boolean meldezeitraumIsBeforeCurrentYear(final ZeitraumDto zeitraum) {
            val currentYear = LocalDate.now().getYear();
            return zeitraum.startZeitpunkt().getYear() < currentYear && zeitraum.endZeitpunkt()
                    .getYear() < currentYear;
        }
    }

}
