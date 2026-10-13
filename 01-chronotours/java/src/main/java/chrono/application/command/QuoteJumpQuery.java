package chrono.application.command;

import chrono.domain.TimeMachineType;
import chrono.domain.TravellerId;

public record QuoteJumpQuery(TravellerId travellerId, TimeMachineType machine, int targetYear) {
}
