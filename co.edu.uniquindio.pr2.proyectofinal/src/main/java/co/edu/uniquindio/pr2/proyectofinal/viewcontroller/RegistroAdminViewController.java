package co.edu.uniquindio.pr2.proyectofinal.viewcontroller;

import co.edu.uniquindio.pr2.proyectofinal.controller.RegistroAdminController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

public class RegistroAdminViewController {

    @FXML private TextField nombreField, emailField, telefonoField;
    @FXML private PasswordField passwordField, confirmPasswordField;
    @FXML private Button registerButton;
    @FXML private Hyperlink linkLogin, linkUsuario;

    private final RegistroAdminController controller = new RegistroAdminController();

    @FXML
    public void initialize() {
        registerButton.setOnAction(this::registrarAdmin);
        linkLogin.setOnAction(this::irLogin);
        linkUsuario.setOnAction(e -> controller.abrirVentana("registroUsuario.fxml", "Registro Usuario"));
    }

    private void registrarAdmin(ActionEvent event) {
        String nombre = nombreField.getText().trim();
        String correo = emailField.getText().toLowerCase().trim();
        String telefono = telefonoField.getText().trim();
        String password = passwordField.getText();
        String confirm = confirmPasswordField.getText();

        controller.registrarAdministrador(nombre, correo, telefono, password, confirm, event);
    }

    private void irLogin(ActionEvent event) {
        controller.abrirVentana("loginAdmin.fxml", "Login Administrador");
        controller.cerrarVentana(event);
    }
}