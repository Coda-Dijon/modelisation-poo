package chrono;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Main {
    private static Logger log;

    public static void main(String[] args) {
        log = ConsoleLogger.create("chrono.scenario");

        var cabin = new Cabin();
        var deLorean = new DeLorean();
        var portal = new Portal();

        log.info("=== 1. Arrivée à l'agence ChronoTours ===");
        var marty = new TimeTraveller("Marty", 100);
        var doc = new TimeTraveller("Doc", 200);
        for (var traveller : List.of(marty, doc)) {
            log.info(traveller.name() + " : en " + traveller.currentYear() + ", " + traveller.chronitons() + " chronitons");
        }

        log.info("");
        log.info("=== 2. Devis pour Marty, de 2026 à 1955 ===");
        for (var machine : List.of(cabin, deLorean, portal)) {
            log.info(machine.name() + " : " + marty.quote(machine, 1955) + " chronitons");
        }

        log.info("");
        log.info("=== 3. Les sauts de Marty ===");
        jump(marty, 1955, deLorean);
        jump(marty, 1955, cabin);
        jump(marty, 1850, deLorean);
        jump(marty, 2026, cabin);
        jump(marty, 2026, deLorean);

        log.info("");
        log.info("=== 4. Les sauts de Doc ===");
        jump(doc, 1900, cabin);
        jump(doc, 1985, cabin);
        jump(doc, 1955, portal);
        jump(doc, 1985, deLorean);

        log.info("");
        log.info("=== 5. Carnets de voyage ===");
        for (var traveller : List.of(marty, doc)) {
            var journey = traveller.journey().stream().map(String::valueOf).collect(Collectors.joining(" → "));
            log.info(traveller.name() + " : " + journey + ", " + traveller.chronitons() + " chronitons");
        }
    }

    // The model refuses by throwing: Main only logs the reason
    private static void jump(TimeTraveller traveller, int targetYear, TimeMachine machine) {
        try {
            int cost = traveller.quote(machine, targetYear);
            traveller.jumpTo(targetYear, machine);
            log.info(traveller.name() + " saute en " + targetYear + " (" + machine.name() + ") : "
                    + cost + " chronitons, reste " + traveller.chronitons());
        } catch (JumpRefused refusal) {
            log.info("Saut refusé : " + refusal.getMessage());
        }
    }
}
