package chrono.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

final class TravellerFixture {
    private TravellerFixture() {
    }

    static UUID register(MockMvcTester mvc, String name, int chronitons) {
        var result = mvc.post().uri("/travellers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"name": "%s", "chronitons": %d}
                        """.formatted(name, chronitons))
                .exchange();
        assertThat(result).hasStatus(HttpStatus.CREATED);
        var location = result.getResponse().getHeader("Location");
        return UUID.fromString(location.substring(location.lastIndexOf('/') + 1));
    }

    static void jump(MockMvcTester mvc, UUID travellerId, String machine, int targetYear) {
        assertThat(mvc.post().uri("/travellers/{id}/jumps", travellerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jumpRequest(machine, targetYear)))
                .hasStatus(HttpStatus.NO_CONTENT);
    }

    static String jumpRequest(String machine, int targetYear) {
        return """
                {"machine": "%s", "targetYear": %d}
                """.formatted(machine, targetYear);
    }
}
