package ma.ilem.inventorymanagement.exception;

import ma.ilem.inventorymanagement.pojo.ExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
public class ExceptionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);

    @Value("${messages.server.error}")
    private String serverError;

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ExceptionResponse> handle(HttpServletRequest request, Exception exception) {
        return handle(request.getRequestURL().toString(), exception, serverError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> handle(HttpServletRequest request, AccessDeniedException exception) {
        return handle(request.getRequestURL().toString(), exception, exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<ExceptionResponse> handle(HttpServletRequest request, BindException exception) {
        return handle(request.getRequestURL().toString(), exception, null, exception.getBindingResult().getFieldErrors(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AssignmentNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handle(HttpServletRequest request, AssignmentNotFoundException exception) {
        return handle(request.getRequestURL().toString(), exception, exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = CategoryNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handle(HttpServletRequest request, CategoryNotFoundException exception) {
        return handle(request.getRequestURL().toString(), exception, exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ItemNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handle(HttpServletRequest request, ItemNotFoundException exception) {
        return handle(request.getRequestURL().toString(), exception, exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handle(HttpServletRequest request, UserNotFoundException exception) {
        return handle(request.getRequestURL().toString(), exception, exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = CategoryDuplicateNameException.class)
    public ResponseEntity<ExceptionResponse> handle(HttpServletRequest request, CategoryDuplicateNameException exception) {
        return handle(request.getRequestURL().toString(), exception, exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RoleDuplicateNameException.class)
    public ResponseEntity<ExceptionResponse> handle(HttpServletRequest request, RoleDuplicateNameException exception) {
        return handle(request.getRequestURL().toString(), exception, exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ItemInvalidQuantityException.class)
    public ResponseEntity<ExceptionResponse> handle(HttpServletRequest request, ItemInvalidQuantityException exception) {
        return handle(request.getRequestURL().toString(), exception, exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public ResponseEntity<ExceptionResponse> handle(HttpServletRequest request, MaxUploadSizeExceededException exception) {
        return handle(request.getRequestURL().toString(), exception, "La taille de la pièce jointe dépasse la limite max (1Mb).", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ImageInvalidFormatException.class)
    public ResponseEntity<ExceptionResponse> handle(HttpServletRequest request, ImageInvalidFormatException exception) {
        return handle(request.getRequestURL().toString(), exception, exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = CategoryHasItemsException.class)
    public ResponseEntity<ExceptionResponse> handle(HttpServletRequest request, CategoryHasItemsException exception) {
        return handle(request.getRequestURL().toString(), exception, exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ItemHasAssignmentException.class)
    public ResponseEntity<ExceptionResponse> handle(HttpServletRequest request, ItemHasAssignmentException exception) {
        return handle(request.getRequestURL().toString(), exception, exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UserHasAssignmentException.class)
    public ResponseEntity<ExceptionResponse> handle(HttpServletRequest request, UserHasAssignmentException exception) {
        return handle(request.getRequestURL().toString(), exception, exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UserDuplicateEmailException.class)
    public ResponseEntity<ExceptionResponse> handle(HttpServletRequest request, UserDuplicateEmailException exception) {
        return handle(request.getRequestURL().toString(), exception, exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

/*
todo: MultipartException -- no image
 */

    private ResponseEntity<ExceptionResponse> handle(String url, Exception exception, String message, HttpStatus status) {
        return handle(url, exception, message, null, status);
    }

    private ResponseEntity<ExceptionResponse> handle(String url, Exception exception, String message, List<FieldError> fieldErrors, HttpStatus status) {
        LOGGER.error("Request throw an error: " + url, exception);

        ExceptionResponse response = new ExceptionResponse();

        response.setUrl(url);
        response.setMessage(message);
        response.setFieldErrors(fieldErrors);

        return ResponseEntity.status(status).body(response);
    }

}
