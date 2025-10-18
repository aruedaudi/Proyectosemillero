package application;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;

public class PedidoController {
	@FXML
	private Button btnSalir;
	@FXML
	private TableView<PedidoAlba> tablaPedidos;
	@FXML
	private TableColumn<PedidoAlba, Integer> colId;
	@FXML
	private TableColumn<PedidoAlba, Integer> colProducto;
	@FXML
	private TableColumn<PedidoAlba, Integer> colProveedor;
	@FXML
	private TableColumn<PedidoAlba, String> colFechaEnt;
	@FXML
	private TableColumn<PedidoAlba, Integer> colCantidad;
	@FXML
	private TableColumn<PedidoAlba, String> colFechaCre;
	@FXML
	private TableColumn<PedidoAlba, String> colRecibido;
	@FXML
	private TableColumn<PedidoAlba, String> colObs;
	@FXML
	private Button btnVerPedidos;

	// Event Listener on Button[#btnSalir].onAction
	@FXML
	void cerrarPedido(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuPrincipal.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Menú Principal");
        stage.show();
    }
	// Event Listener on Button[#btnVerPedidos].onAction
	@FXML
    public void initialize() {
        // Configurar las columnas
        colId.setCellValueFactory(new PropertyValueFactory<>("idPedido"));
        colProducto.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        colProveedor.setCellValueFactory(new PropertyValueFactory<>("idProveedor"));
        colFechaEnt.setCellValueFactory(new PropertyValueFactory<>("fechaEntrega"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colFechaCre.setCellValueFactory(new PropertyValueFactory<>("fechaCreacion"));
        colRecibido.setCellValueFactory(new PropertyValueFactory<>("recibido"));
        colObs.setCellValueFactory(new PropertyValueFactory<>("observaciones"));
    }

    // ✅ Cargar los datos al presionar el botón "Ver Pedidos"
    @FXML
    public void VerPedidos(ActionEvent event) {
        DatosPedido datos = new DatosPedido();
        ObservableList<PedidoAlba> lista = FXCollections.observableArrayList(datos.getDatos());
        tablaPedidos.setItems(lista);
    }
}
