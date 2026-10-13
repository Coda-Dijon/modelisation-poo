package chrono.domain;

import java.util.function.Supplier;

public enum TimeMachineType {
    CABIN(Cabin::new),
    DELOREAN(DeLorean::new),
    PORTAL(Portal::new);

    private final Supplier<TimeMachine> factory;

    TimeMachineType(Supplier<TimeMachine> factory) {
        this.factory = factory;
    }

    public TimeMachine machine() {
        return factory.get();
    }
}
