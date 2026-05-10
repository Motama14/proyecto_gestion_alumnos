package objetos.nota;

public class NotaInvalidaExcepcion extends RuntimeException {
    public NotaInvalidaExcepcion(String message) {
        super(message);
    }
}
