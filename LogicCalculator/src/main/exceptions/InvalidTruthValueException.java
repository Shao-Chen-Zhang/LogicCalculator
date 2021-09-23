package exceptions;

public class InvalidTruthValueException extends Exception {

    public InvalidTruthValueException() { }

    public InvalidTruthValueException(String msg) {
        super(msg);
    }
}
