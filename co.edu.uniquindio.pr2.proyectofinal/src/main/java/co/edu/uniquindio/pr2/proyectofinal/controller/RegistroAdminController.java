package co.edu.uniquindio.pr2.proyectofinal.controller;

import co.edu.uniquindio.pr2.proyectofinal.LogisticaApplication;
import co.edu.uniquindio.pr2.proyectofinal.builder.AdministradorBuilder;
import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import co.edu.uniquindio.pr2.proyectofinal.model.Administrador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class RegistroAdminController {

    @FXML private Label confirmPasswordError, emailError, nombreError, passwordError, telefonoError;
    @FXML private PasswordField confirmPasswordField, passwordField;
    @FXML private TextField emailField, nombreField, telefonoField;
    @FXML private Button registerButton;
    @FXML private Hyperlink linkLogin;
    @FXML private Hyperlink linkUsuario;

    @FXML
    void handleRegisterAdmin(ActionEvent event) {
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

        if (ModelFactory.getInstance().getEmpresaLogistica().buscarAdministradorPorCorreo(correo) != null) {
            emailError.setText("Este correo ya está registrado");
            emailError.setVisible(true);
            return;
        }

        Administrador nuevo = new AdministradorBuilder()
                .idAdministrador("A" + (ModelFactory.getInstance().getEmpresaLogistica().getAdministradores().size() + 1))
                .nombre(nombre)
                .correo(correo)
                .telefono(telefono)
                .password(password)
                .build();

        ModelFactory.getInstance().getEmpresaLogistica().agregarAdministrador(nuevo);
        ModelFactory.getInstance().setAdministradorActual(nuevo);

        new Alert(Alert.AlertType.INFORMATION, "Administrador registrado correctamente.").showAndWait();
        handleGoToLoginAdmin(event);
    }

    @FXML
    void handleGoToLoginAdmin(ActionEvent event) {
        abrirVentana("loginAdmin.fxml", "Login Administrador");
        ((Stage) ((Control) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    void handleGoToRegistroUsuario(ActionEvent event) {
        abrirVentana("registroUsuario.fxml", "Registro Usuario");
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