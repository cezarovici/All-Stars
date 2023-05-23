package PaooGame.Exception;

public class CustomNullPointerException extends NullPointerException {
    public CustomNullPointerException() {
        super("Custom Null Pointer Exception: The object is null.");
    }
}
