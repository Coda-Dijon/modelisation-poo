package chrono.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface SpringDataTravellerRepository extends JpaRepository<TravellerEntity, UUID> {
}
