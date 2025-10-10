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
public class DatosCantidad {
	LinkedList<InventarioAlba> getDatos(){
		InventarioAlba a=null;
		LinkedList<InventarioAlba> data= new LinkedList<>();
		try {
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost", "ALBA_CACAO","ALBA_CACAO");
			Statement st= conn.createStatement();
			String query = "SELECT SUM(p.Cantidad) AS cantidad_total_inventario"
					+ " FROM Inventario i"
					+ " JOIN Pedido p ON i.Id_pedido = p.Id_pedido";		
			ResultSet result = st.executeQuery(query);
			while(result.next()) {
				a = new InventarioAlba(result.getString(1));
				data.add(a);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
		
	}
	

}
