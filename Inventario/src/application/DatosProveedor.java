package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class DatosProveedor {
    public LinkedList<ProveedorAlba> getDatos() {
        LinkedList<ProveedorAlba> data = new LinkedList<>();

        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe",
                "ALBA_CACAO",
                "ALBA_CACAO"
            );

            Statement st = conn.createStatement();
            ResultSet result = st.executeQuery("SELECT * FROM proveedor");

            while (result.next()) {
                ProveedorAlba prov = new ProveedorAlba(
                	result.getBigDecimal("ID_PROVEEDOR"),
                	result.getString("NOMBRE"),
                	result.getString("DIRECCION"),
                	result.getBigDecimal("TELEFONO"),
                	result.getString("CORREO"),
                	result.getString("CIUDAD"),
                	result.getString("VEREDA"),
                	result.getString("OBSERVACIONES"),
                	result.getString("ACTIVO")
                );
                data.add(prov);
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }
}
