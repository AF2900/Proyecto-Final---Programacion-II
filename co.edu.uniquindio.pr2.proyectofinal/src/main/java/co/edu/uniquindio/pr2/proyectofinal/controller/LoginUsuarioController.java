package co.edu.uniquindio.pr2.proyectofinal.controller;

import co.edu.uniquindio.pr2.proyectofinal.LogisticaApplication;
import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import co.edu.uniquindio.pr2.proyectofinal.facade.FacadeSeguridadAdmin;
import co.edu.uniquindio.pr2.proyectofinal.model.Usuario;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.stream.Collectors;

public class LoginUsuarioController {

    public Button btnIniciarSesion;
    public Label lblMensaje;
    public Hyperlink linkOlvidePassword;
    public Hyperlink linkRegistrarse;
    public Hyperlink linkAdmin;
    public TextField txtEmail;
    public PasswordField txtPassword;

    private ModelFactory modelFactory;
    private final FacadeSeguridadAdmin fachadaSeguridad = new FacadeSeguridadAdmin();

    public void initialize() {
        modelFactory = ModelFactory.getInstance();
        modelFactory.inicializarDatos();
    }

    public void handleLogin() {
        String correo = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();

        if (correo.isEmpty() || password.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Por favor ingrese su correo y contraseña.");
            return;
        }

        Usuario usuario = modelFactory.getEmpresaLogistica().buscarUsuarioPorCorreo(correo);
        if (usuario == null || !usuario.getPassword().equals(password)) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de inicio de sesión", "El correo o la contraseña son incorrectos.");
            return;
        }

        modelFactory.setUsuarioActual(usuario);
        mostrarAlerta(Alert.AlertType.INFORMATION, "Bienvenido", "Bienvenid@ " + usuario.getNombre() + "!");
        lblMensaje.setText("Bienvenido " + usuario.getNombre());
        abrirVentana("usuarioMenu.fxml", "Panel Usuario");
    }

    public void handleRegister() {
        abrirVentana("registroUsuario.fxml", "Registro Usuario");
    }

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

    public void handleGoToAdminLogin() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Acceso de administrador");
        dialog.setHeaderText("Protección de seguridad");
        dialog.setContentText("Ingrese la clave maestra para acceder:");

        dialog.showAndWait().ifPresent(claveIngresada -> {
            boolean accesoPermitido = fachadaSeguridad.intentarAcceso(claveIngresada);
            if (accesoPermitido) {
                abrirVentana("loginAdmin.fxml", "Login Administrador");
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Acceso denegado", "Clave incorrecta. Inténtalo nuevamente.");
            }
        });
    }

    private void abrirVentana(String fxml, String titulo) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LogisticaApplication.class.getResource(fxml));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.setFullScreen(false);
            stage.centerOnScreen();
            stage.show();
            Stage loginStage = (Stage) btnIniciarSesion.getScene().getWindow();
            loginStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}