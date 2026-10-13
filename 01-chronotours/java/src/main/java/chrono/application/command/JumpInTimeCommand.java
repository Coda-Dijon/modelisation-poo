package chrono.application.command;

import chrono.domain.TimeMachineType;
import chrono.domain.TravellerId;

public record JumpInTimeCommand(TravellerId travellerId, TimeMachineType machine, int targetYear) {
}
