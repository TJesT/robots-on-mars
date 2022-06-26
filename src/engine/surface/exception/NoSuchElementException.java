package engine.surface.exception;

public class NoSuchElementException extends RuntimeException {
    public NoSuchElementException() {
        super("No such element presented in array");
    }
}
