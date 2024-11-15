package agricole.simulatore.mutuoCard.utils.errorhandling;

import agricole.simulatore.mutuoCard.utils.exception.*;

import javax.annotation.PostConstruct;
import javax.validation.ValidationException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ControllerAdvice
public class GenericExceptionHandler {

    @PostConstruct
    public void init() {
        Logger.getLogger(GenericExceptionHandler.class.getCanonicalName()).log(Level.INFO, "Generic Exception Handling active");
    }

    @ResponseBody
    @ExceptionHandler({UnauthorizedException.class, UnauthenticatedException.class})
    public ResponseEntity<ResponseError> handleAuthenticationError(Exception ex) {
        String message;
        HttpStatus status;
        if (ex instanceof UnauthorizedException) {
            message = "Accesso negato. Non hai i permessi necessari per questa operazione.";
            status = HttpStatus.FORBIDDEN;
        } else if (ex instanceof UnauthenticatedException) {
            message = "Utente non autenticato. Accedi per continuare.";
            status = HttpStatus.UNAUTHORIZED;
        } else {
            message = "Errore di autenticazione. Controlla le credenziali e riprova.";
            status = HttpStatus.UNAUTHORIZED;
        }
        ResponseError body = new ResponseError(message);
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseError> handleAccessDeniedException(AccessDeniedException ex) {
        ResponseError body = new ResponseError("Accesso negato: non hai i permessi necessari per accedere a questa risorsa. Error: " + ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }

    @ResponseBody
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ResponseError> handleResourceNotFoundError(Exception ex) {
        ResponseError body = new ResponseError(ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler({ValidationException.class, WebClient400Exception.class})
    public ResponseEntity<ResponseError> handleValidationError(Exception ex) {
        ResponseError body = new ResponseError(ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseError> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
            .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
            .collect(Collectors.joining(", "));
        ResponseError body = new ResponseError(message);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler({JwtValidationException.class})
    public ResponseEntity<ResponseError> handleJwtValidationError(Exception ex) {
        ResponseError body = new ResponseError(ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @ResponseBody
    @ExceptionHandler({UnsupportedOperationException.class})
    public ResponseEntity<ResponseError> handleUnsupportedOperationError(Exception ex) {
        ResponseError body = new ResponseError(ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_IMPLEMENTED);
    }

    @ResponseBody
    @ExceptionHandler({Exception.class, WebClient500Exception.class})
    public ResponseEntity<ResponseError> handleGerericError(Exception ex) {
        ResponseError body = new ResponseError(ex.getMessage());
        Logger.getLogger(GenericExceptionHandler.class.getSimpleName()).log(Level.SEVERE, ExceptionUtils.getStackTrace(ex));
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}