package chrono.application;

import chrono.domain.TravellerId;

public class TravellerNotFound extends RuntimeException {
    public TravellerNotFound(TravellerId id) {
        super("aucun voyageur avec l'identifiant " + id);
    }
}
