package DAO;

import objetos.nota.Nota;

import java.util.List;

public interface NotaDAO extends ObjetoDAO<Nota> {
    @Override
    default List<Nota> obtenerTodo(String dni) {return List.of();}

    void agregarNota(Nota nota, String dni);
    void actualizarNota(String asignatura, double nota, int id);
    void eliminarNota(Nota nota);
}
