package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReporteInventarioController {

    @FXML private TableView<ReporteInventario> tablaReporte;
    @FXML private TableColumn<ReporteInventario, Integer> colID;
    @FXML private TableColumn<ReporteInventario, String> colProducto;
    @FXML private TableColumn<ReporteInventario, Integer> colCantidad;
    @FXML private TableColumn<ReporteInventario, Float> colPeso;
    @FXML private TableColumn<ReporteInventario, String> colEstado;
    @FXML private TableColumn<ReporteInventario, Float> colHumedad;
    @FXML private TableColumn<ReporteInventario, String> colUltimoMov;
    @FXML private TableColumn<ReporteInventario, java.sql.Date> colFechaMov;
    @FXML private TableColumn<ReporteInventario, String> colProveedor;

    @FXML
    public void initialize() {
        // Configurar columnas
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProducto.setCellValueFactory(new PropertyValueFactory<>("producto"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colHumedad.setCellValueFactory(new PropertyValueFactory<>("humedad"));
        colUltimoMov.setCellValueFactory(new PropertyValueFactory<>("ultimoMov"));
        colFechaMov.setCellValueFactory(new PropertyValueFactory<>("fechaMov"));
        colProveedor.setCellValueFactory(new PropertyValueFactory<>("proveedor"));

        // Cargar datos
        tablaReporte.setItems(obtenerDatosReporte());
    }

    private ObservableList<ReporteInventario> obtenerDatosReporte() {
        ObservableList<ReporteInventario> lista = FXCollections.observableArrayList();
        String query = "SELECT * FROM reporte_inventario";

        try (Connection conn = DatosReporteInventario.conectar();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ReporteInventario item = new ReporteInventario(
                		rs.getInt("ID"),
                	    rs.getString("PRODUCTO"),
                	    rs.getInt("CANTIDAD"),
                	    rs.getFloat("PESO"),
                	    rs.getString("ESTADO"),
                	    rs.getFloat("HUMEDAD"),
                	    rs.getString("ULTIMO_MOV"),
                	    rs.getDate("FECHA_MOV"),
                	    rs.getString("PROVEEDOR")
                );
                lista.add(item);
            }

        } catch (SQLException e) {
            System.out.println(" Error al obtener datos del reporte: " + e.getMessage());
        }
        return lista;
    }

    @FXML
    private void cerrarVentana() {
        javafx.stage.Stage stage = (javafx.stage.Stage) tablaReporte.getScene().getWindow();
        stage.close();
    }
}
