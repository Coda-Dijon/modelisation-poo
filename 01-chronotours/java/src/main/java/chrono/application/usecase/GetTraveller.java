package chrono.application.usecase;

import chrono.application.dto.TravellerDto;
import chrono.application.port.TravellerRepository;
import chrono.domain.TravellerId;

/**
 * Consulter un voyageur : année courante, réserve de chronitons et carnet de voyage.
 */
public class GetTraveller {
    private final TravellerRepository travellers;

    public GetTraveller(TravellerRepository travellers) {
        this.travellers = travellers;
    }

    public TravellerDto handle(TravellerId id) {
        return TravellerDto.from(travellers.getById(id));
    }
}
