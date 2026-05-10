package objetos.nota;

public class Nota {
    int id;
    String asignatura;
    double nota;

    public Nota(String asignatura, double nota) {
        this.asignatura = asignatura;
        this.nota = nota;
    }

    public Nota(int id, String asignatura, double nota) {
        this.id = id;
        this.asignatura = asignatura;
        this.nota = nota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

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
}
