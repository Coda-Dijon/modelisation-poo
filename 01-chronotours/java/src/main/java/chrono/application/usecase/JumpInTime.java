package chrono.application.usecase;

import chrono.application.command.JumpInTimeCommand;
import chrono.application.port.TravellerRepository;

/**
 * Faire sauter un voyageur vers une année avec une machine.
 * Le domaine refuse le saut (JumpRefused) si une règle n'est pas respectée.
 */
public class JumpInTime {
    private final TravellerRepository travellers;

    public JumpInTime(TravellerRepository travellers) {
        this.travellers = travellers;
    }

    public void handle(JumpInTimeCommand command) {
        var traveller = travellers.getById(command.travellerId());
        traveller.jumpTo(command.targetYear(), command.machine().machine());
        travellers.save(traveller);
    }
}
