package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private Button btnLogin;

    @FXML
    private TextField txtContraseña;

    @FXML
    private TextField txtUsuario;

    // Instancia de la clase que maneja la conexión y validación
    private DatosUsuarios datos = new DatosUsuarios();

    @FXML
    void iniciarSesion(ActionEvent event) {
        // Obtener valores del formulario
        String usuario = txtUsuario.getText().trim();
        String contrasena = txtContraseña.getText().trim();

        // Validar que los campos no estén vacíos
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            mostrarAlerta(AlertType.WARNING, "Campos vacíos", "Por favor ingrese usuario y contraseña.");
            return;
        }

        // Verificar credenciales con la base de datos
        if (datos.validarLogin(usuario, contrasena)) {
            mostrarAlerta(AlertType.INFORMATION, "Inicio de sesión exitoso", "Bienvenido, " + usuario + "!");
            
            

        } else {
            mostrarAlerta(AlertType.ERROR, "Error de autenticación", "Usuario o contraseña incorrectos.");
        }
    }

    // Método auxiliar para mostrar alertas
    private void mostrarAlerta(AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
