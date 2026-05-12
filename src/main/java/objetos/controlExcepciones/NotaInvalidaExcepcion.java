package objetos.controlExcepciones;

public class NotaInvalidaExcepcion extends RuntimeException {
    public NotaInvalidaExcepcion(String message) {
        super(message);
    }
}
