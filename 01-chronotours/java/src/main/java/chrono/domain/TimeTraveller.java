package chrono.domain;

import java.util.ArrayList;
import java.util.List;

public class TimeTraveller {
    public static final int AGENCY_YEAR = 2026;
    public static final int INVENTION_YEAR = 1885;

    private final TravellerId id;
    private final String name;
    private int chronitons;
    private final List<Integer> journey;

    public TimeTraveller(TravellerId id, String name, int chronitons) {
        this(id, name, chronitons, List.of(AGENCY_YEAR));
    }

    private TimeTraveller(TravellerId id, String name, int chronitons, List<Integer> journey) {
        if (chronitons < 0) {
            throw new IllegalArgumentException("la réserve de chronitons ne peut pas être négative");
        }
        if (journey.isEmpty()) {
            throw new IllegalArgumentException("le carnet de voyage commence toujours à l'agence");
        }
        this.id = id;
        this.name = name;
        this.chronitons = chronitons;
        this.journey = new ArrayList<>(journey);
    }

    public static TimeTraveller restore(TimeTravellerSnapshot snapshot) {
        return new TimeTraveller(snapshot.id(), snapshot.name(), snapshot.chronitons(), snapshot.journey());
    }

    public TimeTravellerSnapshot snapshot() {
        return new TimeTravellerSnapshot(id, name, chronitons, journey());
    }

    public TravellerId id() {
        return id;
    }

    public String name() {
        return name;
    }

    public int chronitons() {
        return chronitons;
    }

    public int currentYear() {
        return journey.getLast();
    }

    public List<Integer> journey() {
        return List.copyOf(journey);
    }

    public int quote(TimeMachine machine, int targetYear) {
        return machine.cost(distanceTo(targetYear));
    }

    public void jumpTo(int targetYear, TimeMachine machine) {
        // Check every rule first: a refused jump changes nothing
        checkTargetYear(targetYear);
        machine.checkRange(distanceTo(targetYear));

        int cost = quote(machine, targetYear);
        checkChronitons(cost);

        // Then travel
        chronitons -= cost;
        journey.add(targetYear);
    }

    private void checkTargetYear(int targetYear) {
        if (targetYear < INVENTION_YEAR) {
            throw new JumpRefused("le voyage dans le temps n'est inventé qu'en " + INVENTION_YEAR);
        }
        if (targetYear != AGENCY_YEAR && journey.contains(targetYear)) {
            throw new JumpRefused(name + " est déjà passé en " + targetYear + ", il risque de se croiser");
        }
    }

    private void checkChronitons(int cost) {
        if (cost > chronitons) {
            throw new JumpRefused(name + " n'a que " + chronitons + " chronitons, il en faut " + cost);
        }
    }

    private int distanceTo(int targetYear) {
        return Math.abs(targetYear - currentYear());
    }
}
