package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

/**
 * BASE DE DATOS PARA LA CANTIDAD TOTAL DEL INVENTARIO
 */
public class DatosInventario {
    // Método que obtiene todos los registros del inventario
    public LinkedList<InventarioAlba> getDatos() {
        LinkedList<InventarioAlba> data = new LinkedList<>();

        try {
            // Conexión a Oracle
            Connection conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe",
                "ALBA_CACAO",
                "ALBA_CACAO"
            );

            Statement st = conn.createStatement();

            // Consulta a la vista
            String query = "SELECT * FROM vista_inventario";

            ResultSet result = st.executeQuery(query);

            while (result.next()) {
                InventarioAlba item = new InventarioAlba(
                    result.getInt("ID"),
                    result.getString("PRODUCTO"),
                    result.getInt("CANTIDAD"),
                    result.getFloat("PESO"),
                    result.getString("ESTADO"),
                    result.getFloat("HUMEDAD"),
                    result.getString("FECHA_ING"),
                    result.getString("PROVEEDOR")
                );
                data.add(item);
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }
    
    // 🧹 Método para eliminar un registro del inventario por su ID
    public boolean eliminarPorId(int id) {
        String sql = "DELETE FROM inventario WHERE id_inventario = ?";
        
        try (Connection conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe",
                "ALBA_CACAO",
                "ALBA_CACAO"
            );
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int filas = stmt.executeUpdate();
            
            return filas > 0; // ✅ true si eliminó al menos un registro
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
