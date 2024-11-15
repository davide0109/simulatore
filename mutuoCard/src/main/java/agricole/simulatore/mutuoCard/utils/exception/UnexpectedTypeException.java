package agricole.simulatore.mutuoCard.utils.exception;

public class UnexpectedTypeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UnexpectedTypeException(Object object) {
        super("UnexpectedTypeException for object " + object.getClass().getName());
    }
}
