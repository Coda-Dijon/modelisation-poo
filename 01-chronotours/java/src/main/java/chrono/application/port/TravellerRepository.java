package chrono.application.port;

import chrono.application.TravellerNotFound;
import chrono.domain.TimeTraveller;
import chrono.domain.TravellerId;

import java.util.List;
import java.util.Optional;

/**
 * Port de sortie : là où l'agence range ses voyageurs.
 * L'application ne sait pas s'il s'agit d'H2, de PostgreSQL ou d'une simple Map.
 */
public interface TravellerRepository {
    void save(TimeTraveller traveller);

    Optional<TimeTraveller> findById(TravellerId id);

    List<TimeTraveller> findAll();

    default TimeTraveller getById(TravellerId id) {
        return findById(id).orElseThrow(() -> new TravellerNotFound(id));
    }
}
