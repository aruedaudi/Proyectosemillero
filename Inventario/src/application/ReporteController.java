package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;

public class ReporteController {
	@FXML
	private Button btnSalir;
	@FXML
	private Button btnVerReporte;
	@FXML
	private TableView<InventarioAlba> tablaInventario;
	@FXML
	private TableColumn<InventarioAlba, String> colProdInv;
	@FXML
	private TableColumn<InventarioAlba, Integer> colCantInv;
	@FXML
	private TableColumn<InventarioAlba, String> colEstadoInv;
	@FXML
	private TableColumn<InventarioAlba, Double> colHumedadInv;
	@FXML
	private TableView<MovimientoAlba> tablaMovimientos;
	@FXML
	private TableColumn<MovimientoAlba, Integer> colIdMov;
	@FXML
	private TableColumn<MovimientoAlba, String> colProdMov;
	@FXML
	private TableColumn<MovimientoAlba, String> colTipoMov;
	@FXML
	private TableColumn<MovimientoAlba, Integer> colCantMov;
	@FXML
	private TableColumn<MovimientoAlba, String> colFechaMov;
	@FXML
	private TableView<PedidoAlba> tablaPedidos;
	@FXML
	private TableColumn<PedidoAlba, Integer> colIdPedido;
	@FXML
	private TableColumn<PedidoAlba, String> colProveedor;
	@FXML
	private TableColumn<PedidoAlba, String> colFechaEntr;
	@FXML
	private TableColumn<PedidoAlba, String> colEstadoPedido;

	// Event Listener on Button[#btnSalir].onAction
	@FXML
	void cerrarReporte(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuPrincipal.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Menú Principal");
        stage.show();
    }
	
	@FXML
    public void initialize() {
        btnVerReporte.setOnAction(e -> cargarTablas());
        
        colProdInv.setCellValueFactory(new PropertyValueFactory<>("producto"));
        colCantInv.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colEstadoInv.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colHumedadInv.setCellValueFactory(new PropertyValueFactory<>("humedad"));
        colIdMov.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProdMov.setCellValueFactory(new PropertyValueFactory<>("producto"));
        colTipoMov.setCellValueFactory(new PropertyValueFactory<>("tipo_mov"));
        colCantMov.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colFechaMov.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colIdPedido.setCellValueFactory(new PropertyValueFactory<>("idPedido"));
        colProveedor.setCellValueFactory(new PropertyValueFactory<>("idProveedor"));
        colFechaEntr.setCellValueFactory(new PropertyValueFactory<>("fechaEntrega"));
        colEstadoPedido.setCellValueFactory(new PropertyValueFactory<>("recibido"));
    }
	// Event Listener on Button[#btnVerReporte].onAction
	@FXML
	// === Carga general ===
    private void cargarTablas() {
        cargarInventario();
        cargarMovimientos();
        cargarPedidos();
    }
	
	// --- Métodos individuales ---
    private void cargarInventario() {
        DatosInventario datosInv = new DatosInventario();
        ObservableList<InventarioAlba> listaInv = FXCollections.observableArrayList(datosInv.getDatos());
        tablaInventario.setItems(listaInv);
    }

    private void cargarMovimientos() {
        DatosMovimiento datosMov = new DatosMovimiento();
        ObservableList<MovimientoAlba> listaMov = FXCollections.observableArrayList(datosMov.getDatos());
        tablaMovimientos.setItems(listaMov);
    }

    private void cargarPedidos() {
        DatosPedido datosPed = new DatosPedido();
        ObservableList<PedidoAlba> listaPed = FXCollections.observableArrayList(datosPed.getDatos());
        tablaPedidos.setItems(listaPed);
    }
}
