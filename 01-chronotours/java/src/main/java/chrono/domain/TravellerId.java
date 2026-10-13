package chrono.domain;

import java.util.Objects;
import java.util.UUID;

public record TravellerId(UUID value) {
    public TravellerId {
        Objects.requireNonNull(value, "l'identifiant du voyageur est obligatoire");
    }

    public static TravellerId generate() {
        return new TravellerId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
