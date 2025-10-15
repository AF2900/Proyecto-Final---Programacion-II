package co.edu.uniquindio.pr2.proyectofinal.controller;

import co.edu.uniquindio.pr2.proyectofinal.LogisticaApplication;
import co.edu.uniquindio.pr2.proyectofinal.builder.UsuarioBuilder;
import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import co.edu.uniquindio.pr2.proyectofinal.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class RegistroUsuarioController {

    @FXML private Label confirmPasswordError, emailError, nombreError, passwordError, telefonoError;
    @FXML private PasswordField confirmPasswordField, passwordField;
    @FXML private TextField emailField, nombreField, telefonoField;
    @FXML private Button registerButton;
    @FXML private Hyperlink loginLink;
    @FXML private Hyperlink linkAdmin;

    @FXML
    void handleRegister(ActionEvent event) {
        String nombre = nombreField.getText().trim();
        String correo = emailField.getText().toLowerCase();
        String password = passwordField.getText();
        String confirm = confirmPasswordField.getText();
        String telefono = telefonoField.getText();

        if (nombre.isBlank() || correo.isBlank() || password.isBlank() || confirm.isBlank() || telefono.isBlank()) {
            mostrarErrorCampos(nombre, correo, password, confirm, telefono);
            return;
        }

        if (!correo.contains("@")) {
            emailError.setText("Inserte un correo válido");
            emailError.setVisible(true);
            return;
        }

        if (!password.equals(confirm)) {
            confirmPasswordError.setText("Las contraseñas no coinciden");
            confirmPasswordError.setVisible(true);
            return;
        }

        if (ModelFactory.getInstance().getEmpresaLogistica().buscarUsuarioPorCorreo(correo) != null) {
            emailError.setText("Este correo ya está registrado");
            emailError.setVisible(true);
            return;
        }

        Usuario nuevo = new UsuarioBuilder()
                .idUsuario("U" + (ModelFactory.getInstance().getEmpresaLogistica().getUsuarios().size() + 1))
                .nombre(nombre)
                .correo(correo)
                .telefono(telefono)
                .password(password)
                .build();

        ModelFactory.getInstance().getEmpresaLogistica().agregarUsuario(nuevo);
        ModelFactory.getInstance().setUsuarioActual(nuevo);

        new Alert(Alert.AlertType.INFORMATION, "Usuario registrado correctamente.").showAndWait();
        handleGoToLogin(event);
    }

    @FXML
    void handleGoToLogin(ActionEvent event) {
        abrirVentana("loginUsuario.fxml", "Login Usuario");
        ((Stage) ((Control) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    void handleGoToRegistroAdmin(ActionEvent event) {
        abrirVentana("registroAdmin.fxml", "Registro Administrador");
        ((Stage) ((Control) event.getSource()).getScene().getWindow()).close();
    }

    private void abrirVentana(String fxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(LogisticaApplication.class.getResource(fxml));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarErrorCampos(String n, String c, String p, String pc, String t) {
        nombreError.setVisible(n.isBlank());
        emailError.setVisible(c.isBlank());
        passwordError.setVisible(p.isBlank());
        confirmPasswordError.setVisible(pc.isBlank());
        telefonoError.setVisible(t.isBlank());
    }
}