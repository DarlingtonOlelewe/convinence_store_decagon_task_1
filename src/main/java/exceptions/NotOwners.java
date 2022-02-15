package exceptions;

public class NotOwners extends Exception{
    public NotOwners(String message) {
        super(message);
    }
}
