package co.edu.uniquindio.pr2.proyectofinal.viewcontroller;

import co.edu.uniquindio.pr2.proyectofinal.controller.LoginAdminController;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginAdminViewController {

    @FXML private Button btnIniciarSesion;
    @FXML private Label lblMensaje;
    @FXML private Hyperlink linkOlvidePassword;
    @FXML private Hyperlink linkRegistrarse;
    @FXML private Hyperlink linkUsuario;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;

    private LoginAdminController controller;

    @FXML
    private void initialize() {
        controller = new LoginAdminController();
        controllerInitialize();
        btnIniciarSesion.setOnAction(e -> controller.handleLogin());
        linkOlvidePassword.setOnAction(e -> controller.handleForgotPassword());
        linkRegistrarse.setOnAction(e -> controller.handleRegister());
        linkUsuario.setOnAction(e -> controller.handleGoToUsuarioLogin());
    }

    private void controllerInitialize() {
        try {
            var fieldFactory = LoginAdminController.class.getDeclaredField("modelFactory");
            fieldFactory.setAccessible(true);
            fieldFactory.set(controller, null);

            var fieldBtn = LoginAdminController.class.getDeclaredField("btnIniciarSesion");
            fieldBtn.setAccessible(true);
            fieldBtn.set(controller, btnIniciarSesion);

            var fieldLbl = LoginAdminController.class.getDeclaredField("lblMensaje");
            fieldLbl.setAccessible(true);
            fieldLbl.set(controller, lblMensaje);

            var fieldOlv = LoginAdminController.class.getDeclaredField("linkOlvidePassword");
            fieldOlv.setAccessible(true);
            fieldOlv.set(controller, linkOlvidePassword);

            var fieldReg = LoginAdminController.class.getDeclaredField("linkRegistrarse");
            fieldReg.setAccessible(true);
            fieldReg.set(controller, linkRegistrarse);

            var fieldUsu = LoginAdminController.class.getDeclaredField("linkUsuario");
            fieldUsu.setAccessible(true);
            fieldUsu.set(controller, linkUsuario);

            var fieldTxtE = LoginAdminController.class.getDeclaredField("txtEmail");
            fieldTxtE.setAccessible(true);
            fieldTxtE.set(controller, txtEmail);

            var fieldTxtP = LoginAdminController.class.getDeclaredField("txtPassword");
            fieldTxtP.setAccessible(true);
            fieldTxtP.set(controller, txtPassword);

            var init = LoginAdminController.class.getDeclaredMethod("initialize");
            init.setAccessible(true);
            init.invoke(controller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
