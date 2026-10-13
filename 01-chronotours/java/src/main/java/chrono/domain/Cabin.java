package chrono.domain;

public class Cabin extends TimeMachine {
    private static final int MAX_RANGE = 100;

    public Cabin() {
        super("Cabine");
    }

    @Override
    public int cost(int years) {
        return years;
    }

    @Override
    public void checkRange(int years) {
        if (years > MAX_RANGE) {
            throw new JumpRefused("la Cabine ne dépasse pas " + MAX_RANGE + " ans de saut (" + years + " demandés)");
        }
    }
}
