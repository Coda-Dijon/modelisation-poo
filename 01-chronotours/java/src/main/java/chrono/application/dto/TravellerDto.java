package chrono.application.dto;

import chrono.domain.TimeTraveller;

import java.util.List;
import java.util.UUID;

public record TravellerDto(UUID id, String name, int currentYear, int chronitons, List<Integer> journey) {
    public static TravellerDto from(TimeTraveller traveller) {
        return new TravellerDto(
                traveller.id().value(),
                traveller.name(),
                traveller.currentYear(),
                traveller.chronitons(),
                traveller.journey()
        );
    }
}
