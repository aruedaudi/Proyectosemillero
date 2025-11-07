package application;

import java.sql.*;
import java.util.LinkedList;

public class DatosReporteInventario {

    private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private final String USER = "ALBA_CACAO";
    private final String PASSWORD = "ALBA_CACAO";

    public LinkedList<ReporteInventario> getDatos() {
        LinkedList<ReporteInventario> data = new LinkedList<>();

        String sql = """
            SELECT 
                p.descripcion AS tipo_producto,
                COUNT(pe.id_pedido) AS total_pedidos,
                SUM(pe.cantidad) AS cantidad_total,
                SUM(p.peso * pe.cantidad) AS peso_total
            FROM producto p
            JOIN pedido pe ON p.id_producto = pe.id_producto
            GROUP BY p.descripcion
            ORDER BY p.descripcion
        """;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            int totalPedidosGlobal = 0;
            int cantidadGlobal = 0;
            double pesoGlobal = 0.0;

            while (rs.next()) {
                String tipoProducto = rs.getString("tipo_producto");
                int totalPedidos = rs.getInt("total_pedidos");
                int cantidad = rs.getInt("cantidad_total");
                double peso = rs.getDouble("peso_total");

                totalPedidosGlobal += totalPedidos;
                cantidadGlobal += cantidad;
                pesoGlobal += peso;

                data.add(new ReporteInventario(tipoProducto, totalPedidos, cantidad, peso));
            }

            // Agregar fila TOTAL al final
            data.add(new ReporteInventario("TOTAL", totalPedidosGlobal, cantidadGlobal, pesoGlobal));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }
}
