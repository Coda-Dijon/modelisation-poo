package chrono.application.usecase;

import chrono.application.command.QuoteJumpQuery;
import chrono.application.dto.QuoteDto;
import chrono.application.port.TravellerRepository;

/**
 * Établir le devis d'un saut, sans rien réserver.
 */
public class QuoteJump {
    private final TravellerRepository travellers;

    public QuoteJump(TravellerRepository travellers) {
        this.travellers = travellers;
    }

    public QuoteDto handle(QuoteJumpQuery query) {
        var traveller = travellers.getById(query.travellerId());
        var machine = query.machine().machine();
        return new QuoteDto(machine.name(), query.targetYear(), traveller.quote(machine, query.targetYear()));
    }
}
