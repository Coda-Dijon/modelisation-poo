package chrono.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class TravellerApiTest {
    @Autowired
    private MockMvcTester mvc;

    @Test
    void un_voyageur_inscrit_commence_en_2026_avec_sa_reserve() {
        var id = TravellerFixture.register(mvc, "Marty", 100);

        assertThat(mvc.get().uri("/travellers/{id}", id))
                .hasStatusOk()
                .bodyJson()
                .isLenientlyEqualTo("""
                        {"id": "%s", "name": "Marty", "currentYear": 2026, "chronitons": 100, "journey": [2026]}
                        """.formatted(id));
    }

    @Test
    void l_inscription_renvoie_l_url_du_voyageur() {
        assertThat(mvc.post().uri("/travellers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"name": "Doc", "chronitons": 200}
                        """))
                .hasStatus(HttpStatus.CREATED)
                .headers()
                .hasHeaderSatisfying("Location", values -> assertThat(values).singleElement().asString().contains("/travellers/"));
    }

    @Test
    void une_reserve_negative_est_refusee() {
        assertThat(mvc.post().uri("/travellers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"name": "Biff", "chronitons": -1}
                        """))
                .hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson()
                .extractingPath("$.detail")
                .isEqualTo("la réserve de chronitons ne peut pas être négative");
    }

    @Test
    void les_voyageurs_inscrits_sont_listes() {
        var jennifer = TravellerFixture.register(mvc, "Jennifer", 50);

        assertThat(mvc.get().uri("/travellers"))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$[*].id")
                .asArray()
                .contains(jennifer.toString());
    }

    @Test
    void un_voyageur_inconnu_renvoie_404() {
        assertThat(mvc.get().uri("/travellers/{id}", UUID.randomUUID()))
                .hasStatus(HttpStatus.NOT_FOUND);
    }
}
