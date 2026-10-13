package chrono.infrastructure.config;

import chrono.application.port.TravellerRepository;
import chrono.application.usecase.GetTraveller;
import chrono.application.usecase.JumpInTime;
import chrono.application.usecase.ListTimeMachines;
import chrono.application.usecase.ListTravellers;
import chrono.application.usecase.QuoteJump;
import chrono.application.usecase.RegisterTraveller;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Les use cases sont du Java pur : c'est ici, à l'extérieur de l'hexagone,
 * qu'on les branche sur les adaptateurs fournis par Spring.
 */
@Configuration
class UseCaseConfiguration {
    @Bean
    RegisterTraveller registerTraveller(TravellerRepository travellers) {
        return new RegisterTraveller(travellers);
    }

    @Bean
    GetTraveller getTraveller(TravellerRepository travellers) {
        return new GetTraveller(travellers);
    }

    @Bean
    ListTravellers listTravellers(TravellerRepository travellers) {
        return new ListTravellers(travellers);
    }

    @Bean
    ListTimeMachines listTimeMachines() {
        return new ListTimeMachines();
    }

    @Bean
    QuoteJump quoteJump(TravellerRepository travellers) {
        return new QuoteJump(travellers);
    }

    @Bean
    JumpInTime jumpInTime(TravellerRepository travellers) {
        return new JumpInTime(travellers);
    }
}
