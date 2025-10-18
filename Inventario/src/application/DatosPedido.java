package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class DatosPedido {

    public LinkedList<PedidoAlba> getDatos() {
        LinkedList<PedidoAlba> data = new LinkedList<>();

        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe",
                    "ALBA_CACAO",
                    "ALBA_CACAO"
            );

            Statement st = conn.createStatement();
            String query = "SELECT * FROM vista_pedidos";
            ResultSet result = st.executeQuery(query);

            while (result.next()) {
                PedidoAlba pedido = new PedidoAlba(
                        result.getInt("ID"),
                        result.getString("PRODUCTO"),
                        result.getString("PROVEEDOR"),
                        result.getDate("FECHA_ENT").toString(),
                        result.getInt("CANTIDAD"),
                        result.getDate("FECHA_CRE").toString(),
                        result.getString("RECIBIDO"),
                        result.getString("OBSERVACIONES")
                );
                data.add(pedido);
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }
}
