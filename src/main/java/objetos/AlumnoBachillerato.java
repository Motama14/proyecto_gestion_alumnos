package objetos;

import java.util.List;

public class AlumnoBachillerato extends Alumno {
    private String modalidad;

    public AlumnoBachillerato(String dni, String nombre, int edad, List<Double> notas, String modalidad) {
        super(dni, nombre, edad, notas);
        this.modalidad = modalidad;
    }

    public AlumnoBachillerato(String dni, String nombre, int edad, String modalidad) {
        super(dni, nombre, edad);
        this.modalidad = modalidad;
    }

    @Override
    public String toString() {
        return super.toString() + ", Bachillerato - Modalidad: " +modalidad;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }
}
