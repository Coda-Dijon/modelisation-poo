package chrono.infrastructure.persistence;

import chrono.application.port.TravellerRepository;
import chrono.domain.TimeTraveller;
import chrono.domain.TravellerId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adaptateur JPA du port TravellerRepository.
 * La base utilisée (H2 en mémoire par défaut) se choisit uniquement par configuration
 * (spring.datasource.*) : passer à PostgreSQL ne demande aucune modification de code.
 */
@Repository
class JpaTravellerRepository implements TravellerRepository {
    private final SpringDataTravellerRepository repository;
    private final TravellerEntityMapper mapper;

    JpaTravellerRepository(SpringDataTravellerRepository repository, TravellerEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void save(TimeTraveller traveller) {
        repository.save(mapper.toEntity(traveller));
    }

    @Override
    public Optional<TimeTraveller> findById(TravellerId id) {
        return repository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<TimeTraveller> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }
}
