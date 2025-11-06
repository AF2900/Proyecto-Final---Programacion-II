package co.edu.uniquindio.pr2.proyectofinal.viewcontroller;

import co.edu.uniquindio.pr2.proyectofinal.controller.LoginUsuarioController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;

public class LoginUsuarioViewController {

    @FXML private Button btnIniciarSesion;
    @FXML private Label lblMensaje;
    @FXML private Hyperlink linkOlvidePassword;
    @FXML private Hyperlink linkRegistrarse;
    @FXML private Hyperlink linkAdmin;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;

    private LoginUsuarioController controller;

    @FXML
    private void initialize() {
        controller = new LoginUsuarioController();
        try {
            injectPrivateField("btnIniciarSesion", btnIniciarSesion);
            injectPrivateField("lblMensaje", lblMensaje);
            injectPrivateField("linkOlvidePassword", linkOlvidePassword);
            injectPrivateField("linkRegistrarse", linkRegistrarse);
            injectPrivateField("linkAdmin", linkAdmin);
            injectPrivateField("txtEmail", txtEmail);
            injectPrivateField("txtPassword", txtPassword);
            invokeInitialize();
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnIniciarSesion.setOnAction(event -> controller.handleLogin());
        linkRegistrarse.setOnAction(event -> controller.handleRegister());
        linkOlvidePassword.setOnAction(event -> controller.handleForgotPassword());
        linkAdmin.setOnAction(event -> controller.handleGoToAdminLogin());
    }

    private void injectPrivateField(String name, Object value) throws Exception {
        Field f = LoginUsuarioController.class.getDeclaredField(name);
        f.setAccessible(true);
        f.set(controller, value);
    }

    private void invokeInitialize() {
        try {
            var method = LoginUsuarioController.class.getDeclaredMethod("initialize");
            method.setAccessible(true);
            method.invoke(controller);
        } catch (Exception ignored) {}
    }
}
