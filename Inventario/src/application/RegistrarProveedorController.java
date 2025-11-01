package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;

public class RegistrarProveedorController {
	@FXML
	private Button btnSalir;
	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtDireccion;
	@FXML
	private TextField txtTelefono;
	@FXML
	private TextField txtCorreo;
	@FXML
	private TextField txtCiudad;
	@FXML
	private TextField txtVereda;
	@FXML
	private TextField txtObservaciones;
	@FXML
	private Button btnRegistrar;
	@FXML
	private Button btnLimpiar;
	@FXML
	private Label lblEstado;
	
    private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private final String USER = "ALBA_CACAO";
    private final String PASSWORD = "ALBA_CACAO";


    @FXML
	void cerrarProveedor(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
	// Event Listener on Button[#btnRegistrar].onAction
	@FXML
	public void registrarProveedor(ActionEvent event) {
		String nombre = txtNombre.getText();
		String direccion = txtDireccion.getText();
		String telefono = txtTelefono.getText();
		String correo = txtCorreo.getText();
		String ciudad = txtCiudad.getText();
		String vereda = txtVereda.getText();
		String observaciones = txtObservaciones.getText();

		if (nombre.isEmpty() || direccion.isEmpty() || telefono.isEmpty() || correo.isEmpty() || ciudad.isEmpty() || vereda.isEmpty() || observaciones.isEmpty()) {
		    lblEstado.setText("Todos los campos obligatorios deben estar completos.");
		    return;
		}

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
		    String sql = "INSERT INTO proveedor (nombre, direccion, telefono, correo, ciudad, vereda, observaciones) "
		               + "VALUES (?, ?, ?, ?, ?, ?, ?)";

		    PreparedStatement pstmt = conn.prepareStatement(sql);
		    pstmt.setString(1, nombre);
		    pstmt.setString(2, direccion);
		    pstmt.setLong(3, Long.parseLong(telefono)); // número largo
		    pstmt.setString(4, correo);
		    pstmt.setString(5, ciudad);
		    pstmt.setString(6, vereda);
		    pstmt.setString(7, observaciones);

		    pstmt.executeUpdate();
		    lblEstado.setText("Proveedor registrado correctamente.");
		    limpiarCampos();

		} catch (NumberFormatException e) {
		    lblEstado.setText("El teléfono debe ser un número válido.");
		} catch (SQLException e) {
		    lblEstado.setText("Error al registrar proveedor: " + e.getMessage());
		    e.printStackTrace();
		}

	}
	// Event Listener on Button[#btnLimpiar].onAction
	@FXML
	public void limpiarCampos() {
		txtNombre.clear();
		txtDireccion.clear();
		txtTelefono.clear();
		txtCorreo.clear();
		txtCiudad.clear();
		txtVereda.clear();
		txtObservaciones.clear();
	}
}
