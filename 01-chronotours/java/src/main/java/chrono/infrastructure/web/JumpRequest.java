package chrono.infrastructure.web;

import chrono.application.command.JumpInTimeCommand;
import chrono.domain.TimeMachineType;
import chrono.domain.TravellerId;

record JumpRequest(TimeMachineType machine, int targetYear) {
    JumpInTimeCommand toCommand(TravellerId travellerId) {
        return new JumpInTimeCommand(travellerId, machine, targetYear);
    }
}
