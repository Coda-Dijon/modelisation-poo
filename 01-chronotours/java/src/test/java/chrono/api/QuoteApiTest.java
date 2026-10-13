package chrono.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class QuoteApiTest {
    @Autowired
    private MockMvcTester mvc;

    @ParameterizedTest
    @CsvSource({
            "CABIN,    Cabine,   71",
            "DELOREAN, DeLorean, 30",
            "PORTAL,   Portail,  142"
    })
    void le_devis_depend_de_la_machine(String machine, String machineName, int cost) {
        var marty = TravellerFixture.register(mvc, "Marty", 100);

        assertThat(mvc.get().uri("/travellers/{id}/quotes", marty)
                .param("machine", machine)
                .param("targetYear", "1955"))
                .hasStatusOk()
                .bodyJson()
                .isLenientlyEqualTo("""
                        {"machine": "%s", "targetYear": 1955, "cost": %d}
                        """.formatted(machineName, cost));
    }

    @Test
    void un_devis_ne_consomme_aucun_chroniton() {
        var marty = TravellerFixture.register(mvc, "Marty", 100);

        mvc.get().uri("/travellers/{id}/quotes", marty)
                .param("machine", "PORTAL")
                .param("targetYear", "1955")
                .exchange();

        assertThat(mvc.get().uri("/travellers/{id}", marty))
                .bodyJson()
                .extractingPath("$.chronitons")
                .isEqualTo(100);
    }

    @Test
    void une_machine_inconnue_renvoie_400() {
        var marty = TravellerFixture.register(mvc, "Marty", 100);

        assertThat(mvc.get().uri("/travellers/{id}/quotes", marty)
                .param("machine", "TARDIS")
                .param("targetYear", "1955"))
                .hasStatus(HttpStatus.BAD_REQUEST);
    }

    @Test
    void le_devis_d_un_voyageur_inconnu_renvoie_404() {
        assertThat(mvc.get().uri("/travellers/{id}/quotes", UUID.randomUUID())
                .param("machine", "CABIN")
                .param("targetYear", "1955"))
                .hasStatus(HttpStatus.NOT_FOUND);
    }
}
