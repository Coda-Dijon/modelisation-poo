package chrono.infrastructure.web;

import chrono.application.dto.TimeMachineDto;
import chrono.application.usecase.ListTimeMachines;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/time-machines")
class TimeMachineController {
    private final ListTimeMachines listTimeMachines;

    TimeMachineController(ListTimeMachines listTimeMachines) {
        this.listTimeMachines = listTimeMachines;
    }

    @GetMapping
    List<TimeMachineDto> list() {
        return listTimeMachines.handle();
    }
}
