package agricole.simulatore.mutuoCard.utils.exception;

public class JwtValidationException extends RuntimeException {
  public JwtValidationException(String message) {
    super(message);
  }
}