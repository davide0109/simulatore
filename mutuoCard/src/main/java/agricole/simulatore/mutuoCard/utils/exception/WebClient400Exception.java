package agricole.simulatore.mutuoCard.utils.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebClient400Exception extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public WebClient400Exception(String msg) {
        super(msg);
    }
}