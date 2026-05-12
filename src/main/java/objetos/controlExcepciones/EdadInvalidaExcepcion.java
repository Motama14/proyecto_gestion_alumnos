package objetos.controlExcepciones;

public class EdadInvalidaExcepcion extends RuntimeException {
    public EdadInvalidaExcepcion(String message) {
        super(message);
    }
}
