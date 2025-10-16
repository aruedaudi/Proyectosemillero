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
            String query = "SELECT * FROM vista_inventario";

            ResultSet result = st.executeQuery(query);

            while (result.next()) {
                InventarioAlba item = new InventarioAlba(
                    result.getInt("ID"),
<<<<<<< HEAD
                    result.getString("Producto"),
                    result.getInt("Cantidad"),
                    result.getFloat("Peso"),
                    result.getString("Estado"),
                    result.getFloat("Humedad"),
                    result.getString("Ultimo_Mov"),
                    result.getString("Proveedor")
=======
                    result.getString("PRODUCTO"),
                    result.getInt("CANTIDAD"),
                    result.getFloat("PESO"),
                    result.getString("ESTADO"),
                    result.getFloat("HUMEDAD"),
                    result.getString("FECHA_ING"),
                    result.getString("PROVEEDOR")
>>>>>>> f5fa161f1ec18ddf89caf6e9a303e49e4d08983b
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

