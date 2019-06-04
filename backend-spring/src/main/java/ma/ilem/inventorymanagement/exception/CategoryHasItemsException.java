package ma.ilem.inventorymanagement.exception;

public class CategoryHasItemsException extends RuntimeException {
    public CategoryHasItemsException(String message) {
        super(message);
    }
}
