package agricole.simulatore.mutuoCard.utils.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebClient500Exception extends RuntimeException {

    private static final long serialVersionUID = 1L;


    public WebClient500Exception(String msg) {
        super(msg);
    }
}