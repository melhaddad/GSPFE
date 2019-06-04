package ma.ilem.inventorymanagement.exception;

public class UserDuplicateEmailException extends RuntimeException {
    public UserDuplicateEmailException(String message) {
        super(message);
    }
}
