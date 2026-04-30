package DAO;

import objetos.Alumno;
import objetos.AlumnoBachillerato;
import objetos.AlumnoFP;

import java.util.List;

public interface AlumnoDAO {
    default List<Alumno> obtenerTodos() {return List.of();}
    default List<Double> obtenerNotas(String dni) {return List.of();}

    default void agregarAlumno(Alumno alumno) {}
    default void eliminarAlumno(Alumno alumno) {}
    default void actualizarAlumno(Alumno alumno) {}

    default List<String> obtenerDnis() {return List.of();}
}
