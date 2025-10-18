package application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class RegistrarEntradaController {

    @FXML private ComboBox<String> comboProducto;
    @FXML private ComboBox<String> comboProveedor;
    @FXML private TextField txtFechaEntrega;
    @FXML private TextField txtCantidad;
    @FXML private TextField txtFechaCreacion;
    @FXML private ComboBox<String> comboRecibido;
    @FXML private TextArea txtObservaciones;
    @FXML private Label lblEstado;

    private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private final String USER = "ALBA_CACAO";
    private final String PASSWORD = "ALBA_CACAO";

    @FXML
    private void initialize() {
        cargarCombos();
    }

    private void cargarCombos() {
        ObservableList<String> productos = FXCollections.observableArrayList();
        ObservableList<String> proveedores = FXCollections.observableArrayList();
        ObservableList<String> recibidoOpciones = FXCollections.observableArrayList("Sí", "No");

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // Cargar productos
            String sqlProd = "SELECT id_producto || ' - ' || descripcion AS producto FROM producto";
            PreparedStatement stmtProd = conn.prepareStatement(sqlProd);
            ResultSet rsProd = stmtProd.executeQuery();
            while (rsProd.next()) {
                productos.add(rsProd.getString("producto"));
            }

            // Cargar proveedores
            String sqlProv = "SELECT id_proveedor || ' - ' || nombre AS proveedor FROM proveedor";
            PreparedStatement stmtProv = conn.prepareStatement(sqlProv);
            ResultSet rsProv = stmtProv.executeQuery();
            while (rsProv.next()) {
                proveedores.add(rsProv.getString("proveedor"));
            }

        } catch (SQLException e) {
            lblEstado.setText("Error al cargar combos: " + e.getMessage());
            e.printStackTrace();
        }

        comboProducto.setItems(productos);
        comboProveedor.setItems(proveedores);
        comboRecibido.setItems(recibidoOpciones);
    }

    @FXML
    private void guardarEntrada() {
        String productoSel = comboProducto.getValue();
        String proveedorSel = comboProveedor.getValue();
        String fechaEntrega = txtFechaEntrega.getText();
        String cantidadTexto = txtCantidad.getText();
        String fechaCreacion = txtFechaCreacion.getText();
        String recibido = comboRecibido.getValue();
        String observaciones = txtObservaciones.getText();

        if (productoSel == null || proveedorSel == null || cantidadTexto.isEmpty()) {
            lblEstado.setText("Producto, proveedor y cantidad son obligatorios.");
            return;
        }

        try {
            int idProducto = Integer.parseInt(productoSel.split(" - ")[0]);
            int idProveedor = Integer.parseInt(proveedorSel.split(" - ")[0]);

            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "INSERT INTO vista_pedido (id_pedido, id_proveedor, id_producto, fecha_entrada, cantidad, fecha_creacion, recibido, observaciones) "
                           + "VALUES (pedido_seq.NEXTVAL, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?)";

                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, idProveedor);
                pstmt.setInt(2, idProducto);
                pstmt.setString(3, fechaEntrega);

                try {
                    pstmt.setInt(4, Integer.parseInt(cantidadTexto));
                } catch (NumberFormatException e) {
                    lblEstado.setText("La cantidad debe ser un número entero.");
                    return;
                }

                pstmt.setString(5, fechaCreacion);
                pstmt.setString(6, recibido != null && recibido.equals("Sí") ? "S" : "N");
                pstmt.setString(7, observaciones);

                pstmt.executeUpdate();
                lblEstado.setText("Entrada registrada correctamente.");
                limpiarCampos();

            } catch (SQLException e) {
                lblEstado.setText("Error al guardar entrada: " + e.getMessage());
                e.printStackTrace();
            }

        } catch (Exception e) {
            lblEstado.setText("Error: formato inválido en producto o proveedor.");
        }
    }

    @FXML
    private void limpiarCampos() {
        comboProducto.setValue(null);
        comboProveedor.setValue(null);
        txtFechaEntrega.clear();
        txtCantidad.clear();
        txtFechaCreacion.clear();
        comboRecibido.setValue(null);
        txtObservaciones.clear();
        lblEstado.setText("");
    }
}
