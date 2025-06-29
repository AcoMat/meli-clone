package unq.pdes._5.g1.segui_tus_compras.exception.auth;

public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException() {
        super("Unauthorized access");
    }
}
