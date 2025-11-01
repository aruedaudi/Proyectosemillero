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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
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
    private Button btnVerReporte;

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
    private void registrarEntrada(ActionEvent event) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/RegistrarEntrada.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Registrar Pedido");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            
         // 🔄 Después de cerrar la ventana, recargar datos
            cargarDatosInventario();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void cargarDatosInventario() {
        DatosInventario datos = new DatosInventario();
        ObservableList<InventarioAlba> lista = FXCollections.observableArrayList(datos.getDatos());
        tablaInventario.setItems(lista);
    }
    
    @FXML
    private void seleccionarElemento() {
        InventarioAlba seleccionado = tablaInventario.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            txtProducto.setText(seleccionado.getProducto());
            txtCantidad.setText(String.valueOf(seleccionado.getCantidad()));
            txtPeso.setText(String.valueOf(seleccionado.getPeso()));
            txtEstado.setText(seleccionado.getEstado());
            txtHumedad.setText(String.valueOf(seleccionado.getHumedad()));
            txtFecha.setText(seleccionado.getFechaIngreso());
            txtProveedor.setText(seleccionado.getProveedor());
        }
    }

    @FXML
    void registrarSalida(ActionEvent event) {
        InventarioAlba seleccionado = tablaInventario.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta("Error", "Debes seleccionar un elemento del inventario.", Alert.AlertType.WARNING);
            return;
        }

        try {
            // 🔹 Elimina de la BD usando el id
            DatosInventario datos = new DatosInventario();
            boolean eliminado = datos.eliminarPorId(seleccionado.getId());

            if (eliminado) {
                mostrarAlerta("Registro de salida", "Salida registrada correctamente.", Alert.AlertType.INFORMATION);
                cargarDatosInventario(); // 🔄 refrescar tabla
            } else {
                mostrarAlerta("Error", "No se pudo eliminar el registro.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Ocurrió un error al eliminar el registro.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void verReporte(ActionEvent event) {
    	 try {
    	        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReporteInventario.fxml"));
    	        Parent root = loader.load();
    	        
    	        Stage stage = new Stage();
    	        stage.setTitle("Reporte de Inventario");
    	        stage.setScene(new Scene(root));
    	        stage.show();
    	    } catch (Exception e) {
    	        e.printStackTrace();
    	    }
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
        
        cargarDatosInventario();
        
        DatosInventario datos = new DatosInventario();
        ObservableList<InventarioAlba> lista = FXCollections.observableArrayList(datos.getDatos());
        tablaInventario.setItems(lista);
        
        tablaInventario.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                seleccionarElemento();
            }
        });
    }

}
