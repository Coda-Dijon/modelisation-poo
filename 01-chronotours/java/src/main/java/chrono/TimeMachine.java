package chrono;

public abstract class TimeMachine {
    private final String name;

    protected TimeMachine(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    public abstract int cost(int years);

    // By default, a machine can travel any distance
    public void checkRange(int years) {
    }
}
