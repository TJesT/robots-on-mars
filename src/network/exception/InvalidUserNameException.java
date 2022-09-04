package network.exception;

public class InvalidUserNameException extends InvalidNameException {

    public InvalidUserNameException() {
        super();
    }

    public InvalidUserNameException(String message) {
        super(message);
    }
}
