package chrono.infrastructure.web;

import chrono.application.dto.TravellerDto;
import chrono.application.usecase.GetTraveller;
import chrono.application.usecase.ListTravellers;
import chrono.application.usecase.RegisterTraveller;
import chrono.domain.TravellerId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/travellers")
class TravellerController {
    private final RegisterTraveller registerTraveller;
    private final GetTraveller getTraveller;
    private final ListTravellers listTravellers;

    TravellerController(RegisterTraveller registerTraveller, GetTraveller getTraveller, ListTravellers listTravellers) {
        this.registerTraveller = registerTraveller;
        this.getTraveller = getTraveller;
        this.listTravellers = listTravellers;
    }

    @PostMapping
    ResponseEntity<TravellerCreatedResponse> register(@RequestBody RegisterTravellerRequest request) {
        var id = registerTraveller.handle(request.toCommand());
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id.value())
                .toUri();
        return ResponseEntity.created(location).body(new TravellerCreatedResponse(id.value()));
    }

    @GetMapping
    List<TravellerDto> list() {
        return listTravellers.handle();
    }

    @GetMapping("/{id}")
    TravellerDto get(@PathVariable UUID id) {
        return getTraveller.handle(new TravellerId(id));
    }
}
