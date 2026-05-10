package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL = "jdbc:sqlite:database/alumnos.db";

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
