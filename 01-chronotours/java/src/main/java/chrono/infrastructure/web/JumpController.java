package chrono.infrastructure.web;

import chrono.application.command.QuoteJumpQuery;
import chrono.application.dto.QuoteDto;
import chrono.application.usecase.JumpInTime;
import chrono.application.usecase.QuoteJump;
import chrono.domain.TimeMachineType;
import chrono.domain.TravellerId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/travellers/{travellerId}")
class JumpController {
    private final QuoteJump quoteJump;
    private final JumpInTime jumpInTime;

    JumpController(QuoteJump quoteJump, JumpInTime jumpInTime) {
        this.quoteJump = quoteJump;
        this.jumpInTime = jumpInTime;
    }

    @GetMapping("/quotes")
    QuoteDto quote(@PathVariable UUID travellerId,
                   @RequestParam TimeMachineType machine,
                   @RequestParam int targetYear) {
        return quoteJump.handle(new QuoteJumpQuery(new TravellerId(travellerId), machine, targetYear));
    }

    @PostMapping("/jumps")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void jump(@PathVariable UUID travellerId, @RequestBody JumpRequest request) {
        jumpInTime.handle(request.toCommand(new TravellerId(travellerId)));
    }
}
