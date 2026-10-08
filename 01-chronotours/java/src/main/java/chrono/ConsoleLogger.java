package chrono;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/**
 * java.util.logging logger that writes the bare message (no timestamp, no level) to the standard output,
 * so that the scenario log can be read as is and compared by the acceptance test.
 */
final class ConsoleLogger {
    private ConsoleLogger() {
    }

    static Logger create(String name) {
        var logger = Logger.getLogger(name);
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.INFO);
        for (Handler handler : logger.getHandlers()) {
            logger.removeHandler(handler);
        }
        logger.addHandler(standardOutputHandler());
        return logger;
    }

    // Bound to the current System.out when the logger is created, which lets the test capture it
    private static Handler standardOutputHandler() {
        var handler = new StreamHandler(System.out, new MessageOnly()) {
            @Override
            public synchronized void publish(LogRecord logRecord) {
                super.publish(logRecord);
                flush();
            }
        };
        try {
            handler.setEncoding(StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            // UTF-8 is supported by every JVM
            throw new IllegalStateException(e);
        }
        return handler;
    }

    private static final class MessageOnly extends Formatter {
        @Override
        public String format(LogRecord logRecord) {
            return logRecord.getMessage() + System.lineSeparator();
        }
    }
}
