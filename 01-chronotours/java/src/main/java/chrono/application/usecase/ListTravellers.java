package chrono.application.usecase;

import chrono.application.dto.TravellerDto;
import chrono.application.port.TravellerRepository;

import java.util.List;

/**
 * Lister tous les voyageurs inscrits à l'agence.
 */
public class ListTravellers {
    private final TravellerRepository travellers;

    public ListTravellers(TravellerRepository travellers) {
        this.travellers = travellers;
    }

    public List<TravellerDto> handle() {
        return travellers.findAll().stream()
                .map(TravellerDto::from)
                .toList();
    }
}
