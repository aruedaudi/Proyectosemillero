package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class DatosMovimiento {

    public LinkedList<MovimientoAlba> getDatos() {
        LinkedList<MovimientoAlba> data = new LinkedList<>();

        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe",
                    "ALBA_CACAO",
                    "ALBA_CACAO"
            );

            Statement st = conn.createStatement();
            String query = "SELECT * FROM movimientos_inventario";
            ResultSet result = st.executeQuery(query);

            while (result.next()) {
                MovimientoAlba movimiento = new MovimientoAlba(
                        result.getInt("ID"),
                        result.getString("PRODUCTO"),
                        result.getString("TIPO_MOV"),
                        result.getInt("CANTIDAD"),
                        result.getDate("FECHA").toString()
                );
                data.add(movimiento);
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }
}
