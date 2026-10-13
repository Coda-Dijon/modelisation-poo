package chrono.infrastructure.web;

import chrono.application.command.RegisterTravellerCommand;

record RegisterTravellerRequest(String name, int chronitons) {
    RegisterTravellerCommand toCommand() {
        return new RegisterTravellerCommand(name, chronitons);
    }
}
