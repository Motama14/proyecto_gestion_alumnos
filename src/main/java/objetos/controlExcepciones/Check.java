package objetos.controlExcepciones;

public class Check {

    // Metodo para parsear de String a Integer y controlar dos excepciones a la vez
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


    // Metodo para parsear de String a double y controlar dos excepciones a la vez
    public static double parseDouble(String valor) {
        try {
            double result = Double.parseDouble(valor);

            if(result < 0 || result > 10) {
                throw new NotaInvalidaExcepcion("La nota debe estar entre 0 y 10");
            }

            return result;
        } catch (NumberFormatException e) {
            throw new NotaInvalidaExcepcion("La nota debe ser un número válido");
        }
    }

    // Metodos para comprobar que los campos de los formularios son correctos
    public static void checkDni(String dni) {
        if(!dni.matches("^\\d{8}[a-zA-Z]$")) {
            throw new StringInvalidoExcepcion("Debes introducir un DNI válido");
        }
    }

    public static void checkNombre(String nombre) {
        if(!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚ ,]+$")) {
            throw new StringInvalidoExcepcion("Debes introducir un nombre válido");
        }
    }

    public static void checkExtra(String extra) {
        if(!extra.matches("^[\\w ]+$")) {
            throw new StringInvalidoExcepcion("Debes introducir un ciclo o modalidad válido");
        }
    }

    public static void checkAsignatura(String asignatura) {
        if(!asignatura.matches("^[\\w ]+$")) {
            throw new StringInvalidoExcepcion("Debes introducir una asignatura válida, sin caracteres especiales");
        }
    }


}
