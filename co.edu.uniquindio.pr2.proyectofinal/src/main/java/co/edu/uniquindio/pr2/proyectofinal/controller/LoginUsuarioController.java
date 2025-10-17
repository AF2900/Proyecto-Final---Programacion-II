package co.edu.uniquindio.pr2.proyectofinal.controller;

import co.edu.uniquindio.pr2.proyectofinal.LogisticaApplication;
import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import co.edu.uniquindio.pr2.proyectofinal.model.Usuario;
import co.edu.uniquindio.pr2.proyectofinal.facade.FacadeSeguridadAdmin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.stream.Collectors;

public class LoginUsuarioController {

    @FXML private Button btnIniciarSesion;
    @FXML private Label lblMensaje;
    @FXML private Hyperlink linkOlvidePassword;
    @FXML private Hyperlink linkRegistrarse;
    @FXML private Hyperlink linkAdmin;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;

    private ModelFactory modelFactory;
    private final FacadeSeguridadAdmin fachadaSeguridad = new FacadeSeguridadAdmin();

    @FXML
    private void initialize() {
        modelFactory = ModelFactory.getInstance();
        modelFactory.inicializarDatos();
    }

    @FXML
    private void handleLogin() {
        String correo = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();

        if (correo.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos vacíos");
            alert.setHeaderText(null);
            alert.setContentText("Por favor ingrese su correo y contraseña.");
            alert.showAndWait();
            return;
        }

        Usuario usuario = modelFactory.getEmpresaLogistica().buscarUsuarioPorCorreo(correo);

        if (usuario == null || !usuario.getPassword().equals(password)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de inicio de sesión");
            alert.setHeaderText(null);
            alert.setContentText("El correo o la contraseña son incorrectos.");
            alert.showAndWait();
            return;
        }

        modelFactory.setUsuarioActual(usuario);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bienvenido");
        alert.setHeaderText(null);
        alert.setContentText("Bienvenid@ " + usuario.getNombre() + "!");
        alert.showAndWait();

        lblMensaje.setText("Bienvenido " + usuario.getNombre());
        abrirVentana("usuarioMenu.fxml", "Panel Usuario");
    }

    private void abrirVentana(String fxml, String titulo) {
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
            FXMLLoader fxmlLoader = new FXMLLoader(LogisticaApplication.class.getResource("registroUsuario.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Registro Usuario");
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
                .map(u -> "Nombre: " + u.getNombre() + " — Correo: " + u.getCorreo() + " — Contraseña: " + u.getPassword())
                .collect(Collectors.joining("\n"));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Usuarios registrados");
        alert.setHeaderText("Usuarios existentes");
        alert.setContentText(usuarios.isEmpty() ? "No hay usuarios registrados." : usuarios);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    @FXML
    public void handleGoToAdminLogin() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Acceso de administrador");
        dialog.setHeaderText("Protección de seguridad");
        dialog.setContentText("Ingrese la clave maestra para acceder:");

        dialog.showAndWait().ifPresent(claveIngresada -> {
            boolean accesoPermitido = fachadaSeguridad.intentarAcceso(claveIngresada);

            if (accesoPermitido) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(LogisticaApplication.class.getResource("loginAdmin.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
                    Stage stage = new Stage();
                    stage.setTitle("Login Administrador");
                    stage.setScene(scene);
                    stage.centerOnScreen();
                    stage.show();
                    Stage loginStage = (Stage) linkAdmin.getScene().getWindow();
                    loginStage.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Acceso denegado");
                alert.setHeaderText(null);
                alert.setContentText("Clave incorrecta. Inténtalo nuevamente.");
                alert.showAndWait();
            }
        });
    }
}