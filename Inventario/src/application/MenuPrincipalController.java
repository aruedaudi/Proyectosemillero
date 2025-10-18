package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuPrincipalController {
	
	private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button btnInventario;

    @FXML
    private Button btnPedido;

    @FXML
    private Button btnProveedor;

    @FXML
    private Button btnReporte;

    @FXML
    void abrirInventario(ActionEvent event) throws IOException {
        cambiarVista(event, "Inventario.fxml", "Gestión de Inventario");
    }

	@FXML
	void abrirPedido(ActionEvent event) throws IOException {
        cambiarVista(event, "Pedido.fxml", "Gestión de Pedidos");
    }

    @FXML
    void abrirProveedor(ActionEvent event) throws IOException {
        cambiarVista(event, "Proveedor.fxml", "Gestión de Proveedores");
    }

    @FXML
    void abrirReporte(ActionEvent event) throws IOException {
        cambiarVista(event, "Reporte.fxml", "Reportes del Sistema");
    }

    @FXML
    void cerrarSesion(ActionEvent event) throws IOException {
    	// Regresar a la pantalla de login
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    private void cambiarVista(ActionEvent event, String archivoFXML, String titulo) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(archivoFXML));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(titulo);
        stage.show();
    }

}
