package chrono.application.usecase;

import chrono.application.command.RegisterTravellerCommand;
import chrono.application.port.TravellerRepository;
import chrono.domain.TimeTraveller;
import chrono.domain.TravellerId;

/**
 * Un voyageur se présente à l'agence avec sa réserve de chronitons.
 */
public class RegisterTraveller {
    private final TravellerRepository travellers;

    public RegisterTraveller(TravellerRepository travellers) {
        this.travellers = travellers;
    }

    public TravellerId handle(RegisterTravellerCommand command) {
        var traveller = new TimeTraveller(TravellerId.generate(), command.name(), command.chronitons());
        travellers.save(traveller);
        return traveller.id();
    }
}
