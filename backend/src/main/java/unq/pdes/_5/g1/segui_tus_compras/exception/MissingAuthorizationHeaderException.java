package unq.pdes._5.g1.segui_tus_compras.exception;

public class MissingAuthorizationHeaderException extends RuntimeException {
    public MissingAuthorizationHeaderException() {
        super("Missing Authorization header");
    }
}
