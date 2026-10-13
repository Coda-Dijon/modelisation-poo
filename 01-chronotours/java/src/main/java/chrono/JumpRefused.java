package chrono;

public class JumpRefused extends RuntimeException {
    public JumpRefused(String reason) {
        super(reason);
    }
}
