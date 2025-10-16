package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatosReporteInventario {
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USER = "ALBA_CACAO";
    private static final String PASSWORD = "ALBA_CACAO";

    public static Connection conectar() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println(" Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            System.out.println(" Error al conectar a la base de datos: " + e.getMessage());
        }
        return conn;
    }
}
