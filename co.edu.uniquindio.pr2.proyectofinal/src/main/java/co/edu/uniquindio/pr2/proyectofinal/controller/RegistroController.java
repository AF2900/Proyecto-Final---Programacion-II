package co.edu.uniquindio.pr2.proyectofinal.controller;

import co.edu.uniquindio.pr2.proyectofinal.LogisticaApplication;
import co.edu.uniquindio.pr2.proyectofinal.builder.AdministradorBuilder;
import co.edu.uniquindio.pr2.proyectofinal.builder.UsuarioBuilder;
import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import co.edu.uniquindio.pr2.proyectofinal.model.Administrador;
import co.edu.uniquindio.pr2.proyectofinal.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.IOException;

public class RegistroController {

    @FXML private Label confirmPasswordError, emailError, nombreError, passwordError, telefonoError;
    @FXML private PasswordField confirmPasswordField, passwordField;
    @FXML private TextField emailField, nombreField, telefonoField;
    @FXML private HBox loadingBox;
    @FXML private Hyperlink loginLink;
    @FXML private Button registerButton;

    @FXML
    void handleGoToLogin(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LogisticaApplication.class.getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 900, 600);
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
            Stage currentStage = (Stage) ((Control) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleRegister(ActionEvent event) {
        String nombre = nombreField.getText();
        String correo = emailField.getText().toLowerCase();
        String password = passwordField.getText();
        String passwordConfirm = confirmPasswordField.getText();
        String telefono = telefonoField.getText();

        boolean valido = true;

        if (nombre.isBlank()) {
            nombreError.setText("Nombre requerido");
            nombreError.setVisible(true);
            valido = false;
        } else nombreError.setText("");

        if (correo.isBlank()) {
            emailError.setText("Correo requerido");
            emailError.setVisible(true);
            valido = false;
        } else emailError.setText("");

        if (password.isBlank()) {
            passwordError.setText("Contraseña requerida");
            passwordError.setVisible(true);
            valido = false;
        } else passwordError.setText("");

        if (!password.equals(passwordConfirm)) {
            confirmPasswordError.setText("Contraseñas no coinciden");
            confirmPasswordError.setVisible(true);
            valido = false;
        } else confirmPasswordError.setText("");

        if (telefono.isBlank()) {
            telefonoError.setText("Teléfono requerido");
            telefonoError.setVisible(true);
            valido = false;
        } else telefonoError.setText("");

        if (!valido) return;

        if (ModelFactory.getInstance().getEmpresaLogistica().buscarUsuarioPorCorreo(correo) != null ||
                ModelFactory.getInstance().getEmpresaLogistica().buscarAdministradorPorCorreo(correo) != null) {
            emailError.setText("Este correo ya está registrado");
            emailError.setVisible(true);
            return;
        }

        boolean esAdmin = correo.contains("admin");

        if (esAdmin) {
            Administrador admin = new AdministradorBuilder()
                    .idAdministrador("A" + (ModelFactory.getInstance().getEmpresaLogistica().getAdministradores().size() + 1))
                    .nombre(nombre)
                    .correo(correo)
                    .telefono(telefono)
                    .password(password)
                    .build();

            ModelFactory.getInstance().getEmpresaLogistica().agregarAdministrador(admin);
            ModelFactory.getInstance().setAdministradorActual(admin);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registro exitoso");
            alert.setHeaderText(null);
            alert.setContentText("Administrador registrado correctamente. Ahora puedes iniciar sesión.");
            alert.showAndWait();
        } else {
            Usuario nuevoUsuario = new UsuarioBuilder()
                    .idUsuario("U" + (ModelFactory.getInstance().getEmpresaLogistica().getUsuarios().size() + 1))
                    .nombre(nombre)
                    .correo(correo)
                    .telefono(telefono)
                    .password(password)
                    .build();
            ModelFactory.getInstance().getEmpresaLogistica().agregarUsuario(nuevoUsuario);
            ModelFactory.getInstance().setUsuarioActual(nuevoUsuario);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registro exitoso");
            alert.setHeaderText(null);
            alert.setContentText("Usuario registrado correctamente. Ahora puedes iniciar sesión.");
            alert.showAndWait();
        }

        handleGoToLogin(event);
    }
}