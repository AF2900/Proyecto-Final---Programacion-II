package co.edu.uniquindio.pr2.proyectofinal.controller;

import co.edu.uniquindio.pr2.proyectofinal.LogisticaApplication;
import co.edu.uniquindio.pr2.proyectofinal.builder.UsuarioBuilder;
import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import co.edu.uniquindio.pr2.proyectofinal.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import java.io.IOException;

public class RegistroUsuarioController {

    private final ModelFactory modelFactory = ModelFactory.getInstance();

    public boolean registrarUsuario(String nombre,
                                    String correo,
                                    String telefono,
                                    String password,
                                    String confirm,
                                    Label nombreError,
                                    Label emailError,
                                    Label telefonoError,
                                    Label passwordError,
                                    Label confirmPasswordError) {
        boolean valido = true;
        nombreError.setVisible(false);
        emailError.setVisible(false);
        telefonoError.setVisible(false);
        passwordError.setVisible(false);
        confirmPasswordError.setVisible(false);

        if (nombre.isBlank()) {
            nombreError.setText("Campo obligatorio");
            nombreError.setVisible(true);
            valido = false;
        }
        if (correo.isBlank()) {
            emailError.setText("Campo obligatorio");
            emailError.setVisible(true);
            valido = false;
        }
        if (telefono.isBlank()) {
            telefonoError.setText("Campo obligatorio");
            telefonoError.setVisible(true);
            valido = false;
        }
        if (password.isBlank()) {
            passwordError.setText("Campo obligatorio");
            passwordError.setVisible(true);
            valido = false;
        }
        if (confirm.isBlank()) {
            confirmPasswordError.setText("Campo obligatorio");
            confirmPasswordError.setVisible(true);
            valido = false;
        }

        if (!valido) return false;

        if (!correo.contains("@")) {
            emailError.setText("Inserte un correo válido");
            emailError.setVisible(true);
            return false;
        }

        if (!password.equals(confirm)) {
            confirmPasswordError.setText("Las contraseñas no coinciden");
            confirmPasswordError.setVisible(true);
            return false;
        }

        if (modelFactory.getEmpresaLogistica().buscarUsuarioPorCorreo(correo) != null) {
            emailError.setText("Este correo ya está registrado");
            emailError.setVisible(true);
            return false;
        }

        Usuario nuevo = new UsuarioBuilder()
                .idUsuario("U" + (modelFactory.getEmpresaLogistica().getUsuarios().size() + 1))
                .nombre(nombre)
                .correo(correo)
                .telefono(telefono)
                .password(password)
                .build();

        modelFactory.getEmpresaLogistica().agregarUsuario(nuevo);
        modelFactory.setUsuarioActual(nuevo);

        new Alert(AlertType.INFORMATION, "Usuario registrado correctamente.").showAndWait();
        return true;
    }

    public void abrirVentana(String fxml, String titulo) {
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

    public void cerrarVentana(ActionEvent event) {
        ((Stage) ((Control) event.getSource()).getScene().getWindow()).close();
    }
}