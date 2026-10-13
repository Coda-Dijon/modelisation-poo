package chrono.application.dto;

import chrono.domain.TimeMachineType;

public record TimeMachineDto(TimeMachineType type, String name) {
    public static TimeMachineDto from(TimeMachineType type) {
        return new TimeMachineDto(type, type.machine().name());
    }
}
