package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/*/
 * BASE DE DATOS PARA LOS USUARIOS
 */

public class DatosUsuarios {
	 private Connection conectar() {
	        Connection conexion = null;
	        try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            conexion = DriverManager.getConnection(
	                "jdbc:oracle:thin:@localhost:1521:xe", // ajuste de conexion(local o ip)
	                "usuario", // usuario
	                "usuario" // contraseña
	            );
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return conexion;
	    }
	 
	 public boolean validarLogin(String usuario, String contrasena) {
	        String sql = "SELECT usuario, contrasena FROM usuario WHERE usuario = ? AND contrasena = ?";
	        boolean existe = false;

	        try (Connection conn = conectar();
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            ps.setString(1, usuario);
	            ps.setString(2, contrasena);

	            ResultSet rs = ps.executeQuery();

	            // Verificar si hay al menos un resultado
	            if (rs.next()) {
	                existe = true;
	            }

	            rs.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return existe;
	    }

}
