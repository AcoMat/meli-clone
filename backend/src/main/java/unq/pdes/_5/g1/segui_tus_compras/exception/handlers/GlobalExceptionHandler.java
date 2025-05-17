package unq.pdes._5.g1.segui_tus_compras.exception.handlers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import unq.pdes._5.g1.segui_tus_compras.exception.AlreadyExistingUser;
import unq.pdes._5.g1.segui_tus_compras.exception.InvalidTokenException;
import unq.pdes._5.g1.segui_tus_compras.exception.MissingAuthorizationHeaderException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConfigDataResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ConfigDataResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage() + ",")
                .reduce((msg1, msg2) -> msg1 + "\n" + msg2)
                .orElse("Validation failed");
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> notRedeableHttp() {
        return new ResponseEntity<>("Request body is not readable", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AlreadyExistingUser.class})
    public ResponseEntity<String> alreadyExistingUser(AlreadyExistingUser ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<String> handleJWTVerificationException(JWTVerificationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<String> handleInvalidTokenException(InvalidTokenException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(MissingAuthorizationHeaderException.class)
    public ResponseEntity<String> handleMissingAuthorizationHeaderException(MissingAuthorizationHeaderException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleGenericRuntime(RuntimeException ex) {
        return ResponseEntity.status(500).body(ex.getMessage());
    }

}
