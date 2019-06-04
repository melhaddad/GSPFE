package ma.ilem.inventorymanagement.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
    private String url;
    private String message;
    private List<FieldError> fieldErrors;
}
