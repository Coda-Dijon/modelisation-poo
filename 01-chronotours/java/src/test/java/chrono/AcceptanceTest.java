package chrono;

import chrono.application.dto.QuoteDto;
import chrono.application.dto.TimeMachineDto;
import chrono.application.dto.TravellerDto;
import chrono.domain.TimeMachineType;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;
import tools.jackson.databind.json.JsonMapper;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Rejoue, à travers l'API HTTP, le scénario de démonstration de l'ancienne application console.
 * Le journal produit doit rester identique au fichier approuvé.
 */
@SpringBootTest
@AutoConfigureMockMvc
class AcceptanceTest {
    @Autowired
    private MockMvcTester mvc;
    @Autowired
    private JsonMapper json;

    private final StringBuilder log = new StringBuilder();

    @Test
    void scenario_de_demonstration() {
        log("=== 1. Arrivée à l'agence ChronoTours ===");
        var marty = register("Marty", 100);
        var doc = register("Doc", 200);
        for (var id : List.of(marty, doc)) {
            var traveller = traveller(id);
            log(traveller.name() + " : en " + traveller.currentYear() + ", " + traveller.chronitons() + " chronitons");
        }

        log("");
        log("=== 2. Devis pour Marty, de 2026 à 1955 ===");
        for (var machine : timeMachines()) {
            log(machine.name() + " : " + quote(marty, machine.type(), 1955).cost() + " chronitons");
        }

        log("");
        log("=== 3. Les sauts de Marty ===");
        jump(marty, 1955, TimeMachineType.DELOREAN);
        jump(marty, 1955, TimeMachineType.CABIN);
        jump(marty, 1850, TimeMachineType.DELOREAN);
        jump(marty, 2026, TimeMachineType.CABIN);
        jump(marty, 2026, TimeMachineType.DELOREAN);

        log("");
        log("=== 4. Les sauts de Doc ===");
        jump(doc, 1900, TimeMachineType.CABIN);
        jump(doc, 1985, TimeMachineType.CABIN);
        jump(doc, 1955, TimeMachineType.PORTAL);
        jump(doc, 1985, TimeMachineType.DELOREAN);

        log("");
        log("=== 5. Carnets de voyage ===");
        for (var id : List.of(marty, doc)) {
            var traveller = traveller(id);
            var journey = traveller.journey().stream().map(String::valueOf).collect(Collectors.joining(" → "));
            log(traveller.name() + " : " + journey + ", " + traveller.chronitons() + " chronitons");
        }

        Approvals.verify(log.toString());
    }

    private void jump(UUID travellerId, int targetYear, TimeMachineType machine) {
        var quote = quote(travellerId, machine, targetYear);
        var result = mvc.post().uri("/travellers/{id}/jumps", travellerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"machine": "%s", "targetYear": %d}
                        """.formatted(machine, targetYear))
                .exchange();

        if (result.getResponse().getStatus() == HttpStatus.NO_CONTENT.value()) {
            var traveller = traveller(travellerId);
            log(traveller.name() + " saute en " + targetYear + " (" + quote.machine() + ") : "
                    + quote.cost() + " chronitons, reste " + traveller.chronitons());
        } else {
            assertThat(result).hasStatus(HttpStatus.UNPROCESSABLE_CONTENT);
            log("Saut refusé : " + json.readTree(body(result)).get("detail").asString());
        }
    }

    private UUID register(String name, int chronitons) {
        var result = mvc.post().uri("/travellers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"name": "%s", "chronitons": %d}
                        """.formatted(name, chronitons))
                .exchange();
        assertThat(result).hasStatus(HttpStatus.CREATED);
        return UUID.fromString(json.readTree(body(result)).get("id").asString());
    }

    private TravellerDto traveller(UUID id) {
        return read(mvc.get().uri("/travellers/{id}", id).exchange(), TravellerDto.class);
    }

    private List<TimeMachineDto> timeMachines() {
        return Arrays.asList(read(mvc.get().uri("/time-machines").exchange(), TimeMachineDto[].class));
    }

    private QuoteDto quote(UUID travellerId, TimeMachineType machine, int targetYear) {
        return read(mvc.get().uri("/travellers/{id}/quotes", travellerId)
                .param("machine", machine.name())
                .param("targetYear", String.valueOf(targetYear))
                .exchange(), QuoteDto.class);
    }

    private <T> T read(MvcTestResult result, Class<T> type) {
        assertThat(result).hasStatusOk();
        return json.readValue(body(result), type);
    }

    private static String body(MvcTestResult result) {
        return new String(result.getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);
    }

    private void log(String line) {
        log.append(line).append(System.lineSeparator());
    }
}
