package co.edu.uniquindio.pr2.proyectofinal.viewcontroller;

import co.edu.uniquindio.pr2.proyectofinal.controller.RastrearEnvioController;
import co.edu.uniquindio.pr2.proyectofinal.model.Envio;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.lang.reflect.Field;

public class RastrearEnvioViewController {

    @FXML private ListView<Envio> listaEnvios;
    @FXML private Label lblEstado;
    @FXML private TextArea txtDetalles;
    @FXML private Button btnVolver;

    private RastrearEnvioController controller;

    @FXML
    private void initialize() {
        controller = new RastrearEnvioController();
        try {
            injectPrivateField("listaEnvios", listaEnvios);
            injectPrivateField("lblEstado", lblEstado);
            injectPrivateField("txtDetalles", txtDetalles);
            invokeInitialize();
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnVolver.setOnAction(event -> controller.volverMenuUsuario());
    }

    private void injectPrivateField(String name, Object value) throws Exception {
        Field f = RastrearEnvioController.class.getDeclaredField(name);
        f.setAccessible(true);
        f.set(controller, value);
    }

    private void invokeInitialize() {
        try {
            var method = RastrearEnvioController.class.getDeclaredMethod("initialize");
            method.setAccessible(true);
            method.invoke(controller);
        } catch (Exception ignored) {}
    }
}
