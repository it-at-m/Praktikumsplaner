package de.muenchen.oss.praktikumsplaner.rest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.muenchen.oss.praktikumsplaner.domain.dtos.NwkDto;
import de.muenchen.oss.praktikumsplaner.domain.enums.Bildungsrichtung;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class NwkControllerTestdataIntegrationTest extends AbstractTestdataIntegrationTest {

    @Nested
    class GetNwks {

        @Test
        void hasNwksForAllDirectionsWhenRequestingWithStatusActive() throws Exception {
            val request = MockMvcRequestBuilders.get("/nachwuchskraft");

            val requestResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
            val responseBody = objectMapper.readValue(requestResult.getResponse().getContentAsString(), NwkDto[].class);

            val richtungen = Arrays.stream(responseBody)
                    .map(NwkDto::richtung)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            Assertions.assertThat(richtungen).containsOnly(Bildungsrichtung.values());
        }

        @Test
        void hasJahrgangOfTheCurrentAndUpToLastTwoYearsWhenRequestingWithStatusActive() throws Exception {
            val request = MockMvcRequestBuilders.get("/nachwuchskraft");

            val requestResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
            val responseBody = objectMapper.readValue(requestResult.getResponse().getContentAsString(), NwkDto[].class);

            val jahrgaengeOfNwks = Arrays.stream(responseBody).map(NwkDto::jahrgang).collect(Collectors.toSet());
            val expectedJahrgaenge = new String[] { createJahrgangStarting(LocalDate.now()),
                    createJahrgangStarting(LocalDate.now().minusYears(1)),
                    createJahrgangStarting(LocalDate.now().minusYears(2)) };

            Assertions.assertThat(jahrgaengeOfNwks).containsOnly(expectedJahrgaenge);
        }

        private String createJahrgangStarting(final LocalDate startingDate) {
            return startingDate.getYear() % 100 + "/" + startingDate.plusYears(3).getYear() % 100;
        }

    }

    @Nested
    class DeleteNwk {

        @Test
        void movesNwkToInactiveStateWhenDeleted() throws Exception {
            final UUID nwkId = UUID.fromString("00000000-0000-0000-0000-000000000001");

            mockMvc.perform(MockMvcRequestBuilders.delete("/nachwuchskraft/{nwkId}", nwkId))
                    .andExpect(status().isOk());

            final MockHttpServletRequestBuilder activeRequest = MockMvcRequestBuilders.get("/nachwuchskraft");
            final MvcResult activeResponse = mockMvc.perform(activeRequest)
                    .andExpect(status().isOk())
                    .andReturn();
            final NwkDto[] activeNwks = objectMapper.readValue(activeResponse.getResponse().getContentAsString(), NwkDto[].class);

            final MockHttpServletRequestBuilder inactiveRequest = MockMvcRequestBuilders.get("/nachwuchskraft").param("state", "INACTIVE");
            final MvcResult inactiveResponse = mockMvc.perform(inactiveRequest)
                    .andExpect(status().isOk())
                    .andReturn();
            final NwkDto[] inactiveNwks = objectMapper.readValue(inactiveResponse.getResponse().getContentAsString(), NwkDto[].class);

            Assertions.assertThat(activeNwks).extracting(NwkDto::id).doesNotContain(nwkId);
            Assertions.assertThat(inactiveNwks).extracting(NwkDto::id).contains(nwkId);
            Assertions.assertThat(inactiveNwks).filteredOn(nwk -> nwk.id().equals(nwkId)).extracting(NwkDto::active).containsOnly(false);
        }
    }

}
