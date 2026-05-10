package DAO;

import jdbc.Conexion;
import objetos.Alumno;
import objetos.AlumnoBachillerato;
import objetos.AlumnoFP;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDAOImpl implements AlumnoDAO {

    // Este metodo devuelve una lista de las notas con el dni asignado para que sea más fácil obtenerla
    // y asignarla a cada objeto creado
    @Override
    public List<Double> obtenerNotas(String dni) {
        String sql = "SELECT nota FROM notas WHERE dni = ?";
        List<Double> lista = new ArrayList<>();

        try(Connection conn = Conexion.getConexion()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, dni);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                lista.add(rs.getDouble("nota"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return lista;
    }

    @Override
    public List<Alumno> obtenerTodo() {
        // Este metodo usa los JOINS en la query para poder determinar directamente si un alumno es de fp o de bachillerato para crear
        // instancias de cada tipo y no tener que comprobar más adelante si un alumno es de fp o bachillerato
        // Teniendo en cuenta que un alumno de FP no puede estar en Bachillerato y viceversa
        String sql = "SELECT dni, nombre, edad, fp.ciclo, b.modalidad FROM alumnos LEFT JOIN alumno_fp fp USING(dni) LEFT JOIN alumno_bach b USING(dni)";
        List<Alumno> lista = new ArrayList<>();

        try (Connection conn = Conexion.getConexion()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while(rs.next()) {
                String ciclo = rs.getString("ciclo");
                String modalidad = rs.getString("modalidad");

                String dni = rs.getString("dni");

                Alumno alumno;
                List<Double> notas = obtenerNotas(dni);

                if(ciclo == null) {
                    alumno = new AlumnoBachillerato(
                            dni,
                            rs.getString("nombre"),
                            rs.getInt("edad"),
                            notas,
                            modalidad
                    );
                } else {
                    alumno = new AlumnoFP(
                            dni,
                            rs.getString("nombre"),
                            rs.getInt("edad"),
                            notas,
                            ciclo
                    );
                }

                lista.add(alumno);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return lista;
    }

    @Override
    public void agregarAlumno(Alumno alumno) {
        String sql = "INSERT INTO alumnos VALUES (?,?,?)";

        try(Connection conn = Conexion.getConexion()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, alumno.getDni());
            ps.setString(2, alumno.getNombre());
            ps.setInt(3, alumno.getEdad());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // Comprueba si el objeto alumno se ha creado utilizando el constructor de FP o de Bachillerato
        // y se hace el casting del objeto alumno al tipo de objeto que se comprueba
        if(alumno instanceof AlumnoFP) {
            AlumnoFP fp = (AlumnoFP) alumno;
            String sql2 = "INSERT INTO alumno_fp VALUES(?,?)";

            try(Connection conn = Conexion.getConexion()) {
                PreparedStatement ps = conn.prepareStatement(sql2);
                ps.setString(1, fp.getDni());
                ps.setString(2, fp.getCiclo());

                ps.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else if (alumno instanceof AlumnoBachillerato) {
            AlumnoBachillerato bach = (AlumnoBachillerato) alumno;
            String sql2 = "INSERT INTO alumno_bach VALUES(?,?)";

            try(Connection conn = Conexion.getConexion()) {
                PreparedStatement ps = conn.prepareStatement(sql2);
                ps.setString(1, bach.getDni());
                ps.setString(2, bach.getModalidad());

                ps.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void eliminarAlumno(Alumno alumno) {
        String sqlnotas = "DELETE FROM notas WHERE dni = ?";
        String sql = "DELETE FROM alumnos WHERE dni = ?";

        try(Connection conn = Conexion.getConexion()) {
            PreparedStatement psnotas = conn.prepareStatement(sqlnotas);
            psnotas.setString(1, alumno.getDni());
            psnotas.executeUpdate();

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, alumno.getDni());
            ps.executeUpdate();

            if (alumno instanceof AlumnoFP) {
                String sqlfp = "DELETE FROM alumno_fp WHERE dni = ?";
                PreparedStatement psfp = conn.prepareStatement(sqlfp);
                psfp.setString(1, alumno.getDni());
                psfp.executeUpdate();
            } else {
                String sqlbach = "DELETE FROM alumno_bach WHERE dni = ?";
                PreparedStatement psbach = conn.prepareStatement(sqlbach);
                psbach.setString(1, alumno.getDni());
                psbach.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void actualizarAlumno(String dni, String nombre, int edad, String curso, String extra) {
        String sql = "UPDATE alumnos SET nombre = ?, edad = ? WHERE dni = ?";

        try(Connection conn = Conexion.getConexion()) {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, nombre);
            ps.setInt(2, edad);
            ps.setString(3, dni);

            ps.executeUpdate();


            String sql1;
            if(curso.equals("FP")) {
                sql1 = "UPDATE alumno_fp SET ciclo = ? WHERE dni = ?";
            } else {
                sql1 = "UPDATE alumno_bach SET modalidad = ? WHERE dni = ?";
            }

            PreparedStatement preparedStatement = conn.prepareStatement(sql1);
            preparedStatement.setString(1, extra);
            preparedStatement.setString(2, dni);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
