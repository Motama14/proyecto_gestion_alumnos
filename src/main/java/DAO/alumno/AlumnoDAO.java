package DAO.alumno;

import DAO.ObjetoDAO;
import objetos.Alumno;

import java.util.List;

public interface AlumnoDAO extends ObjetoDAO<Alumno> {
    @Override
    default List<Alumno> obtenerTodo() {return List.of();}
    default List<Double> obtenerNotas(String dni) {return List.of();}

    default void agregarAlumno(Alumno alumno) {}
    default void eliminarAlumno(Alumno alumno) {}
    default void actualizarAlumno(String dni, String nombre, int edad, String curso, String extra) {}
}
