package cine;

import org.approvaltests.utils.ConsoleOutput;
import org.junit.jupiter.api.Test;

class AcceptanceTest {
    @Test
    void scenario_de_demonstration() {
        try (var console = new ConsoleOutput()) {
            Main.main(new String[0]);
            console.verifyOutput();
        }
    }
}
