package executor.service.exception;

public class StepNumberFormatException
        extends IllegalArgumentException {
    public StepNumberFormatException(
            final String message,
            final String value) {
        super(String.format("%s %s%n", message, value));
    }
}
