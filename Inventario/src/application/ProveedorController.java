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

public class ProveedorController {
	@FXML
	private TableView<ProveedorAlba> tablaProveedores;
	@FXML
	private TableColumn<ProveedorAlba, Integer> colId;
	@FXML
	private TableColumn<ProveedorAlba, String> colNombre;
	@FXML
	private TableColumn<ProveedorAlba, String> colDireccion;
	@FXML
	private TableColumn<ProveedorAlba, Integer> colTelefono;
	@FXML
	private TableColumn<ProveedorAlba, String> colCorreo;
	@FXML
	private TableColumn<ProveedorAlba, String> colCiudad;
	@FXML
	private TableColumn<ProveedorAlba, String> colVereda;
	@FXML
	private TableColumn<ProveedorAlba, String> colObservaciones;
	@FXML
	private TableColumn<ProveedorAlba, String> colActivo;
	@FXML
	private Button btnSalirProveedor;
	@FXML
	private Button btnProveedores;

	// Event Listener on Button[#btnSalirProveedor].onAction
	@FXML
	void cerrarProveedor(ActionEvent event) throws IOException {
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
			colId.setCellValueFactory(new PropertyValueFactory<>("idProveedor"));
	        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
	        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
	        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
	        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
	        colCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
	        colVereda.setCellValueFactory(new PropertyValueFactory<>("vereda"));
	        colObservaciones.setCellValueFactory(new PropertyValueFactory<>("observaciones"));
	        colActivo.setCellValueFactory(new PropertyValueFactory<>("activo"));
	    }

	    // ✅ Cargar los datos al presionar el botón "Ver Pedidos"
	    @FXML
	    public void verProveedores(ActionEvent event) {
	        DatosProveedor datos = new DatosProveedor();
	        ObservableList<ProveedorAlba> lista = FXCollections.observableArrayList(datos.getDatos());
	        tablaProveedores.setItems(lista);
	    }
}
