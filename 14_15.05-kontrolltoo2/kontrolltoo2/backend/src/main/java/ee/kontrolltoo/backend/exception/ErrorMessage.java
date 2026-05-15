package ee.kontrolltoo.backend.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ErrorMessage {
    private String message;
    private Date timestamp;
    private Integer status;
}
