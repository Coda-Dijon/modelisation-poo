package chrono.infrastructure.persistence;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "time_traveller")
public class TravellerEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    private int chronitons;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "journey_step", joinColumns = @JoinColumn(name = "traveller_id"))
    @OrderColumn(name = "step")
    @Column(name = "visited_year", nullable = false)
    private List<Integer> journey = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getChronitons() {
        return chronitons;
    }

    public void setChronitons(int chronitons) {
        this.chronitons = chronitons;
    }

    public List<Integer> getJourney() {
        return journey;
    }

    public void setJourney(List<Integer> journey) {
        this.journey = journey;
    }
}
