package objetos;

import java.util.List;

public class AlumnoFP extends Alumno {
    private String ciclo;

    public AlumnoFP(String dni, String nombre, int edad, List<Double> notas, String ciclo) {
        super(dni, nombre, edad, notas);
        this.ciclo = ciclo;
    }

    @Override
    public String toString() {
        return super.toString() + ", Ciclo: " +ciclo;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }
}
