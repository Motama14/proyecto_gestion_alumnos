package objetos.controlExcepciones;

public class StringInvalidoExcepcion extends RuntimeException {
    public StringInvalidoExcepcion(String message) {
        super(message);
    }
}
