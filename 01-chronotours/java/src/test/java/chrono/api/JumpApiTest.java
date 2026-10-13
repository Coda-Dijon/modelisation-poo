package chrono.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.util.UUID;

import static chrono.api.TravellerFixture.jumpRequest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class JumpApiTest {
    @Autowired
    private MockMvcTester mvc;

    @Test
    void un_saut_debite_les_chronitons_et_complete_le_carnet() {
        var marty = TravellerFixture.register(mvc, "Marty", 100);

        assertThat(jump(marty, "DELOREAN", 1955)).hasStatus(HttpStatus.NO_CONTENT);

        assertThat(mvc.get().uri("/travellers/{id}", marty))
                .hasStatusOk()
                .bodyJson()
                .isLenientlyEqualTo("""
                        {"currentYear": 1955, "chronitons": 70, "journey": [2026, 1955]}
                        """);
    }

    @Test
    void impossible_de_remonter_avant_l_invention_du_voyage_dans_le_temps() {
        var marty = TravellerFixture.register(mvc, "Marty", 100);

        assertRefused(jump(marty, "DELOREAN", 1850), "le voyage dans le temps n'est inventé qu'en 1885");
    }

    @Test
    void impossible_de_retourner_dans_une_annee_deja_visitee() {
        var marty = TravellerFixture.register(mvc, "Marty", 100);
        TravellerFixture.jump(mvc, marty, "DELOREAN", 1955);
        TravellerFixture.jump(mvc, marty, "DELOREAN", 2026);

        assertRefused(jump(marty, "DELOREAN", 1955), "Marty est déjà passé en 1955, il risque de se croiser");
    }

    @Test
    void le_retour_a_l_agence_est_toujours_possible() {
        var marty = TravellerFixture.register(mvc, "Marty", 100);
        TravellerFixture.jump(mvc, marty, "DELOREAN", 1955);

        assertThat(jump(marty, "DELOREAN", 2026)).hasStatus(HttpStatus.NO_CONTENT);
    }

    @Test
    void la_cabine_est_limitee_a_100_ans() {
        var doc = TravellerFixture.register(mvc, "Doc", 200);

        assertRefused(jump(doc, "CABIN", 1900), "la Cabine ne dépasse pas 100 ans de saut (126 demandés)");
    }

    @Test
    void impossible_de_sauter_sans_assez_de_chronitons() {
        var marty = TravellerFixture.register(mvc, "Marty", 70);

        assertRefused(jump(marty, "CABIN", 1955), "Marty n'a que 70 chronitons, il en faut 71");
    }

    @Test
    void un_saut_refuse_ne_modifie_pas_le_voyageur() {
        var marty = TravellerFixture.register(mvc, "Marty", 70);

        jump(marty, "CABIN", 1955).assertThat().hasStatus(HttpStatus.UNPROCESSABLE_CONTENT);

        assertThat(mvc.get().uri("/travellers/{id}", marty))
                .bodyJson()
                .isLenientlyEqualTo("""
                        {"currentYear": 2026, "chronitons": 70, "journey": [2026]}
                        """);
    }

    @Test
    void le_saut_d_un_voyageur_inconnu_renvoie_404() {
        assertThat(jump(UUID.randomUUID(), "CABIN", 2000)).hasStatus(HttpStatus.NOT_FOUND);
    }

    private MockMvcTester.MockMvcRequestBuilder jump(UUID travellerId, String machine, int targetYear) {
        return mvc.post().uri("/travellers/{id}/jumps", travellerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jumpRequest(machine, targetYear));
    }

    private static void assertRefused(MockMvcTester.MockMvcRequestBuilder request, String reason) {
        assertThat(request)
                .hasStatus(HttpStatus.UNPROCESSABLE_CONTENT)
                .bodyJson()
                .isLenientlyEqualTo("""
                        {"title": "Saut refusé", "detail": "%s"}
                        """.formatted(reason));
    }
}
