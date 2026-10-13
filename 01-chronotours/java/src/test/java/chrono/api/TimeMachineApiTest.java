package chrono.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class TimeMachineApiTest {
    @Autowired
    private MockMvcTester mvc;

    @Test
    void l_agence_propose_trois_machines() {
        assertThat(mvc.get().uri("/time-machines"))
                .hasStatusOk()
                .bodyJson()
                .isStrictlyEqualTo("""
                        [
                          {"type": "CABIN", "name": "Cabine"},
                          {"type": "DELOREAN", "name": "DeLorean"},
                          {"type": "PORTAL", "name": "Portail"}
                        ]
                        """);
    }
}
