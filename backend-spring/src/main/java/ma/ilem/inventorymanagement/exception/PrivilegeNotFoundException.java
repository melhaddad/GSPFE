package ma.ilem.inventorymanagement.exception;

public class PrivilegeNotFoundException extends RuntimeException {
    public PrivilegeNotFoundException(String message) {
        super(message);
    }
}
