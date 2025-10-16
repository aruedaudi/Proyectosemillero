package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

/*/
 * BASE DE DATOS PARA LA CANTIDAD TOTAL DEL INVENTARIO
 */
public class DatosInventario {
	 //Metodo que obtiene todos los registros del inventario
    public LinkedList<InventarioAlba> getDatos() {
        LinkedList<InventarioAlba> data = new LinkedList<>();

        try {
            // Conexion a Oracle
            Connection conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", 
                    "ALBA_CACAO",
                    "ALBA_CACAO"
            );

            Statement st = conn.createStatement();

            // 
            String query = "SELECT * FROM reporte_inventario";

            ResultSet result = st.executeQuery(query);

            while (result.next()) {
                InventarioAlba item = new InventarioAlba(
                    result.getInt("ID"),
                    result.getString("Producto"),
                    result.getInt("Cantidad"),
                    result.getFloat("Peso"),
                    result.getString("Estado"),
                    result.getFloat("Humedad"),
                    result.getString("Ultimo Mov"),
                    result.getString("Proveedor")
                );
                data.add(item);
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }
}

