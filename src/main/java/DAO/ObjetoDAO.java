package DAO;

import java.util.List;

public interface ObjetoDAO<T> {
    default List<T> obtenerTodo() {return List.of();}
    default List<T> obtenerTodo(String DNI) {return List.of();}
}
