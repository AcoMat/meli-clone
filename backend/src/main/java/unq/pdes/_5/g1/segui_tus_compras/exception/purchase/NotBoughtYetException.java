package unq.pdes._5.g1.segui_tus_compras.exception.purchase;

public class NotBoughtYetException extends RuntimeException {
    public NotBoughtYetException() {
        super("You need to buy the product before reviewing it");
    }
}
