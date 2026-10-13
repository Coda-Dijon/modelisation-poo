package chrono.domain;

public class DeLorean extends TimeMachine {
    private static final int FIXED_COST = 30;

    public DeLorean() {
        super("DeLorean");
    }

    @Override
    public int cost(int years) {
        return FIXED_COST;
    }
}
