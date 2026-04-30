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
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while(rs.next()) {
                lista.add(rs.getDouble("nota"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return lista;
    }

    @Override
    public List<Alumno> obtenerTodos() {
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
        // y se hace el casting del objeto alumno al tipo de objeto que se comprueba, en java 16 se puede hacer el casting directamente
        // dentro del if
        if(alumno instanceof AlumnoFP) {
            AlumnoFP fp = (AlumnoFP) alumno;
            String sql2 = "INSERT INTO alumno_fp VALUES(?,?)";

            try(Connection conn = Conexion.getConexion()) {
                PreparedStatement ps = conn.prepareStatement(sql);
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
                PreparedStatement ps = conn.prepareStatement(sql);
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
        String sql = "DELTE FROM alumnos WHERE dni = ?";

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
    public void actualizarAlumno(Alumno alumno) {
        String sql = "UPDATE alumnos SET nombre = ?, edad = ? WHERE dni = ?";

        try(Connection conn = Conexion.getConexion()) {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, alumno.getNombre());
            ps.setInt(2, alumno.getEdad());
            ps.setString(3, alumno.getDni());
            ps.executeUpdate();


            String sql1;
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            if(alumno instanceof AlumnoFP) {
                sql1 = "UPDATE alumno_fp SET ciclo = ? WHERE dni = ?";
                preparedStatement.setString(1, ((AlumnoFP) alumno).getCiclo());
            } else {
                sql1 = "UPDATE alumno_bach SET modalidad = ? WHERE dni = ?";
                preparedStatement.setString(1, ((AlumnoBachillerato) alumno).getModalidad());
            }

            preparedStatement.setString(2, alumno.getDni());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> obtenerDnis() {
        String sql = "SELECT dni FROM alumnos";
        List<String> lista = new ArrayList<>();

        try(Connection conn = Conexion.getConexion()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while(rs.next()) {
                lista.add(rs.getString("dni"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return lista;
    }

}
