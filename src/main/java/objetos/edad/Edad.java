package objetos.edad;

public class Edad {

    public static int parseInt(String valor) throws EdadInvalidaExcepcion {
        try {
            int result = Integer.parseInt(valor);

            if(result < 0 || result > 100) {
                throw new EdadInvalidaExcepcion("Introduce una edad válida");
            }

            return result;
        } catch (NumberFormatException e) {
            throw new EdadInvalidaExcepcion("La edad debe ser un número válido");
        }
    }
}
