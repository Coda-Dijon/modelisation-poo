package chrono.domain;

public class JumpRefused extends RuntimeException {
    public JumpRefused(String reason) {
        super(reason);
    }
}
