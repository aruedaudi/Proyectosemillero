package application;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class InventarioController {
	@FXML
	private TableView<InventarioAlba> tablaInventario;

	@FXML
	private TableColumn<InventarioAlba, Integer> colId;
	@FXML
	private TableColumn<InventarioAlba, String> colProducto;
	@FXML
	private TableColumn<InventarioAlba, Integer> colCantidad;
	@FXML
	private TableColumn<InventarioAlba, Double> colPeso;
	@FXML
	private TableColumn<InventarioAlba, String> colEstado;
	@FXML
	private TableColumn<InventarioAlba, Double> colHumedad;
	@FXML
	private TableColumn<InventarioAlba, String> colFecha;
	@FXML
	private TableColumn<InventarioAlba, String> colProveedor;

    @FXML
    private TextField txtCantidad;

    @FXML
    private TextField txtEstado;

    @FXML
    private TextField txtFecha;

    @FXML
    private TextField txtHumedad;

    @FXML
    private TextField txtPeso;

    @FXML
    private TextField txtProducto;

    @FXML
    private TextField txtProveedor;

    
    @FXML
    void cerrarInventario(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuPrincipal.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Menú Principal");
        stage.show();
    }

    @FXML
    void registrarEntrada(ActionEvent event) {
    	mostrarAlerta("Registro de entrada", "Entrada registrada correctamente.", Alert.AlertType.INFORMATION);
    }

	@FXML
    void registrarSalida(ActionEvent event) {
		  mostrarAlerta("Registro de salida", "Salida registrada correctamente.", Alert.AlertType.INFORMATION);
    }

    @FXML
    void verReporte(ActionEvent event) {
    	   mostrarAlerta("Reporte", "Generando reporte de inventario...", Alert.AlertType.INFORMATION);
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProducto.setCellValueFactory(new PropertyValueFactory<>("producto"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colHumedad.setCellValueFactory(new PropertyValueFactory<>("humedad"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaIngreso"));
        colProveedor.setCellValueFactory(new PropertyValueFactory<>("proveedor"));
        
        DatosInventario datos = new DatosInventario();
        ObservableList<InventarioAlba> lista = FXCollections.observableArrayList(datos.getDatos());
        tablaInventario.setItems(lista);
    }

}
