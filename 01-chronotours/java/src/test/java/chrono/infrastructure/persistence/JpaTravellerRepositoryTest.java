package chrono.infrastructure.persistence;

import chrono.application.port.TravellerRepository;
import chrono.domain.DeLorean;
import chrono.domain.TimeTraveller;
import chrono.domain.TravellerId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JpaTravellerRepositoryTest {
    @Autowired
    private TravellerRepository travellers;

    @Test
    void un_voyageur_sauvegarde_est_reconstitue_a_l_identique() {
        var marty = new TimeTraveller(TravellerId.generate(), "Marty", 100);
        marty.jumpTo(1955, new DeLorean());
        marty.jumpTo(2026, new DeLorean());
        marty.jumpTo(1985, new DeLorean());

        travellers.save(marty);

        assertThat(travellers.findById(marty.id()))
                .hasValueSatisfying(found -> assertThat(found.snapshot()).isEqualTo(marty.snapshot()));
    }

    @Test
    void une_nouvelle_sauvegarde_met_a_jour_le_voyageur() {
        var doc = new TimeTraveller(TravellerId.generate(), "Doc", 200);
        travellers.save(doc);

        doc.jumpTo(1955, new DeLorean());
        travellers.save(doc);

        assertThat(travellers.getById(doc.id()).journey()).containsExactly(2026, 1955);
    }

    @Test
    void un_voyageur_inconnu_n_est_pas_trouve() {
        assertThat(travellers.findById(TravellerId.generate())).isEmpty();
    }
}
