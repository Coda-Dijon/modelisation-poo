package chrono.application.usecase;

import chrono.application.dto.TimeMachineDto;
import chrono.domain.TimeMachineType;

import java.util.Arrays;
import java.util.List;

/**
 * Le catalogue des machines proposées par l'agence.
 */
public class ListTimeMachines {
    public List<TimeMachineDto> handle() {
        return Arrays.stream(TimeMachineType.values())
                .map(TimeMachineDto::from)
                .toList();
    }
}
