package exceptions;

public class EmptyListException extends Exception {

    public EmptyListException() { }

    public EmptyListException(String msg) {
        super(msg);
    }
}
