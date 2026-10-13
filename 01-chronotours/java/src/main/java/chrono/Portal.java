package chrono;

public class Portal extends TimeMachine {
    private static final int COST_PER_YEAR = 2;

    public Portal() {
        super("Portail");
    }

    @Override
    public int cost(int years) {
        return COST_PER_YEAR * years;
    }
}
