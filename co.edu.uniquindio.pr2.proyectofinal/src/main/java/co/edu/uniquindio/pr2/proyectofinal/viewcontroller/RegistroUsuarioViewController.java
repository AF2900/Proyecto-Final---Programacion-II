package co.edu.uniquindio.pr2.proyectofinal.viewcontroller;

import co.edu.uniquindio.pr2.proyectofinal.controller.RegistroUsuarioController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

public class RegistroUsuarioViewController {

    @FXML private Label confirmPasswordError, emailError, nombreError, passwordError, telefonoError;
    @FXML private PasswordField confirmPasswordField, passwordField;
    @FXML private TextField emailField, nombreField, telefonoField;
    @FXML private Button registerButton;
    @FXML private Hyperlink loginLink;

    private final RegistroUsuarioController controller = new RegistroUsuarioController();

    @FXML
    public void initialize() {
        registerButton.setOnAction(this::registrarUsuario);
        loginLink.setOnAction(this::irLogin);
    }

    @FXML
    private void registrarUsuario(ActionEvent event) {
        String nombre = nombreField.getText().trim();
        String correo = emailField.getText().toLowerCase().trim();
        String telefono = telefonoField.getText().trim();
        String password = passwordField.getText();
        String confirm = confirmPasswordField.getText();

        boolean exito = controller.registrarUsuario(nombre, correo, telefono, password, confirm,
                nombreError, emailError, telefonoError, passwordError, confirmPasswordError);

        if (exito) {
            controller.cerrarVentana(event);
            controller.abrirVentana("loginUsuario.fxml", "Login Usuario");
        }
    }

    @FXML
    private void irLogin(ActionEvent event) {
        controller.abrirVentana("loginUsuario.fxml", "Login Usuario");
        controller.cerrarVentana(event);
    }

    private void irRegistroAdmin(ActionEvent event) {
        controller.abrirVentana("registroAdmin.fxml", "Registro Administrador");
        controller.cerrarVentana(event);
    }
}