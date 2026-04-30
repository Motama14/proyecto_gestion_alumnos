package objetos;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Alumno {
    private String dni;
    private String nombre;
    private int edad;
    private List<Double> notas;

    public Alumno(String dni, String nombre, int edad, List<Double> notas) {
        this.dni = dni;
        this.nombre = nombre;
        this.edad = edad;
        this.notas = notas;
    }

    public double calcularMedia() {
        return notas.stream().mapToDouble(e -> e).average().orElse(0);
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public List<Double> getNotas() {
        return notas;
    }

    public void setNotas(List<Double> notas) {
        this.notas = notas;
    }

    @Override
    public String toString() {
        return "DNI: " +dni+ ", Nombre: " +nombre+ ", Edad: " +edad+ ", Nota media: " +calcularMedia();
    }
}
