package DAO;

import jdbc.Conexion;
import objetos.nota.Nota;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotaDAOImpl implements NotaDAO {

    // Este metodo va a devolver una lista con el objeto Nota el cual tiene tanto el nombre de la asignatura como
    // la nota que tiene cada alumno para luego mostrarlo en el perfil del alumno
    @Override
    public List<Nota> obtenerTodo(String dni) {
        String sql = "SELECT id, asignatura, nota FROM notas WHERE dni = ?";
        List<Nota> lista = new ArrayList<>();

        try(Connection conn = Conexion.getConexion()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, dni);

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Nota n = new Nota(
                        rs.getInt("id"),
                        rs.getString("asignatura"),
                        rs.getDouble("nota")
                );

                lista.add(n);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return lista;
    }

    @Override
    public void agregarNota(Nota nota, String dni) {
        String sql = "INSERT INTO notas(dni, asignatura, nota) VALUES (?,?,?)";

        try(Connection conn = Conexion.getConexion()) {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, dni);
            ps.setString(2, nota.getAsignatura());
            ps.setDouble(3, nota.getNota());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void actualizarNota(String asignatura, double nota, int id) {
        String sql = "UPDATE notas SET asignatura = ?, nota = ? WHERE id = ?";

        try(Connection conn = Conexion.getConexion()) {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, asignatura);
            ps.setDouble(2, nota);
            ps.setInt(3, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void eliminarNota(Nota nota) {
        String sql = "DELETE FROM notas WHERE id = ?";

        try(Connection conn = Conexion.getConexion()) {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, nota.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
