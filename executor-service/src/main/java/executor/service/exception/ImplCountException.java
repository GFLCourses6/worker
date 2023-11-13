package executor.service.exception;

public class ImplCountException extends RuntimeException {

    public ImplCountException(Class<?> tClass) {
        super("The quantity of the implementations != 1 for " + tClass.getName());
    }
}
