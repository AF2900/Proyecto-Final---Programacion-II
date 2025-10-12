package co.edu.uniquindio.pr2.proyectofinal.controller;

import co.edu.uniquindio.pr2.proyectofinal.LogisticaApplication;
import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import co.edu.uniquindio.pr2.proyectofinal.model.Administrador;
import co.edu.uniquindio.pr2.proyectofinal.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.stream.Collectors;

public class LoginController {

    @FXML private Button btnIniciarSesion;
    @FXML private Label lblMensaje;
    @FXML private Hyperlink linkOlvidePassword;
    @FXML private Hyperlink linkRegistrarse;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;

    private ModelFactory modelFactory;

    public static boolean registroExitoso = false;

    @FXML
    private void initialize() {
        modelFactory = ModelFactory.getInstance();
        modelFactory.inicializarDatos();

        if (registroExitoso) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registro exitoso");
            alert.setHeaderText(null);
            alert.setContentText("Usuario registrado correctamente. Ahora puedes iniciar sesión.");
            alert.showAndWait();
            registroExitoso = false;
        }
    }

    @FXML
    private void handleLogin() {
        String correo = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();

        if (correo.isEmpty() || password.isEmpty()) {
            lblMensaje.setText("Por favor ingrese su correo y contraseña.");
            return;
        }

        Usuario usuario = modelFactory.getEmpresaLogistica().buscarUsuarioPorCorreo(correo);
        if (usuario != null && usuario.getPassword().equals(password)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Bienvenido");
            alert.setHeaderText(null);
            alert.setContentText("Bienvenid@ " + usuario.getNombre() + "!");
            alert.showAndWait();

            lblMensaje.setText("Bienvenido " + usuario.getNombre());
            abrirVentanas("usuarioMenu.fxml", "Panel Usuario");
            return;
        }

        Administrador admin = modelFactory.getEmpresaLogistica().buscarAdministradorPorCorreo(correo);
        if (admin != null && admin.getPassword().equals(password)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Bienvenido Administrador");
            alert.setHeaderText(null);
            alert.setContentText("Bienvenid@ " + admin.getNombre() + "!");
            alert.showAndWait();

            lblMensaje.setText("Bienvenid@ Administrador " + admin.getNombre());
            abrirVentanas("adminMenu.fxml", "Panel Administrador");
            return;
        }

        lblMensaje.setText("Credenciales inválidas.");
    }

    private void abrirVentanas(String fxml, String titulo) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LogisticaApplication.class.getResource(fxml));
            Scene scene = new Scene(fxmlLoader.load(), 1100, 700);

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(scene);

            stage.setResizable(false);
            stage.centerOnScreen();

            stage.show();

            Stage loginStage = (Stage) btnIniciarSesion.getScene().getWindow();
            loginStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleRegister() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LogisticaApplication.class.getResource("registro.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 900, 800);
            Stage stage = new Stage();
            stage.setTitle("Registro");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();

            Stage loginStage = (Stage) linkRegistrarse.getScene().getWindow();
            loginStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleForgotPassword() {
        var empresa = modelFactory.getEmpresaLogistica();

        String usuarios = empresa.getUsuarios().stream()
                .map(u -> u.getNombre() + " — " + u.getCorreo() + " — Contraseña: " + u.getPassword())
                .collect(Collectors.joining("\n"));

        String administradores = empresa.getAdministradores().stream()
                .map(a -> a.getNombre() + " — " + a.getCorreo() + " — Contraseña: " + a.getPassword())
                .collect(Collectors.joining("\n"));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Usuarios registrados");
        alert.setHeaderText("Usuarios y administradores existentes");
        alert.setContentText(
                (usuarios.isEmpty() ? "No hay usuarios registrados." : "Usuarios:\n" + usuarios) + "\n\n" +
                        (administradores.isEmpty() ? "No hay administradores registrados." : "Administradores:\n" + administradores)
        );

        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }
}