package chrono.infrastructure.persistence;

import chrono.domain.TimeTraveller;
import chrono.domain.TimeTravellerSnapshot;
import chrono.domain.TravellerId;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

/**
 * Le domaine expose des accesseurs "à la record" (name(), chronitons()...) que MapStruct
 * ne sait pas lire sur une classe : on passe donc par le snapshot (un record) du voyageur.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
interface TravellerEntityMapper {
    TravellerEntity toEntity(TimeTravellerSnapshot snapshot);

    TimeTravellerSnapshot toSnapshot(TravellerEntity entity);

    default TravellerEntity toEntity(TimeTraveller traveller) {
        return toEntity(traveller.snapshot());
    }

    default TimeTraveller toDomain(TravellerEntity entity) {
        return TimeTraveller.restore(toSnapshot(entity));
    }

    default UUID toUuid(TravellerId id) {
        return id.value();
    }

    default TravellerId toTravellerId(UUID id) {
        return new TravellerId(id);
    }
}
