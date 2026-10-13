package chrono.domain;

import java.util.List;

/**
 * Photo de l'état d'un voyageur, utilisée pour le persister puis le reconstituer
 * sans exposer ses invariants.
 */
public record TimeTravellerSnapshot(TravellerId id, String name, int chronitons, List<Integer> journey) {
}
