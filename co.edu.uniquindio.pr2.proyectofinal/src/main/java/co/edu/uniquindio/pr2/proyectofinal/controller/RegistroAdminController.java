package co.edu.uniquindio.pr2.proyectofinal.controller;

import co.edu.uniquindio.pr2.proyectofinal.LogisticaApplication;
import co.edu.uniquindio.pr2.proyectofinal.builder.AdministradorBuilder;
import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import co.edu.uniquindio.pr2.proyectofinal.services.ILogisticaMapping;
import co.edu.uniquindio.pr2.proyectofinal.mapping.mappers.LogisticaMappingImpl;
import co.edu.uniquindio.pr2.proyectofinal.model.Administrador;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Control;
import javafx.stage.Stage;
import java.io.IOException;

public class RegistroAdminController {

    private final ModelFactory modelFactory = ModelFactory.getInstance();
    private final ILogisticaMapping mapping = new LogisticaMappingImpl();

    public void registrarAdministrador(String nombre, String correo, String telefono, String password, String confirm, ActionEvent event) {
        if (nombre.isBlank() || correo.isBlank() || password.isBlank() || confirm.isBlank() || telefono.isBlank()) {
            new Alert(Alert.AlertType.ERROR, "Todos los campos son obligatorios.").show();
            return;
        }

        if (!correo.contains("@")) {
            new Alert(Alert.AlertType.ERROR, "El correo electrónico no es válido.").show();
            return;
        }

        if (!password.equals(confirm)) {
            new Alert(Alert.AlertType.ERROR, "Las contraseñas no coinciden.").show();
            return;
        }

        if (modelFactory.getEmpresaLogistica().buscarAdministradorPorCorreo(correo) != null) {
            new Alert(Alert.AlertType.ERROR, "Este correo ya está registrado.").show();
            return;
        }

        Administrador nuevo = new AdministradorBuilder()
                .idAdministrador("A" + (modelFactory.getEmpresaLogistica().getAdministradores().size() + 1))
                .nombre(nombre)
                .correo(correo)
                .telefono(telefono)
                .password(password)
                .build();

        modelFactory.getEmpresaLogistica().agregarAdministrador(nuevo);
        modelFactory.setAdministradorActual(nuevo);

        var adminMapped = mapping.mapFromAdministrador(nuevo);

        new Alert(Alert.AlertType.INFORMATION, "Administrador registrado correctamente.").showAndWait();
        abrirVentana("loginAdmin.fxml", "Login Administrador");
        cerrarVentana(event);
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