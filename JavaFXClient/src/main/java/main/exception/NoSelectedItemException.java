package main.exception;

public class NoSelectedItemException extends RuntimeException {
    public NoSelectedItemException(String message) {
        super(message);
    }
}
