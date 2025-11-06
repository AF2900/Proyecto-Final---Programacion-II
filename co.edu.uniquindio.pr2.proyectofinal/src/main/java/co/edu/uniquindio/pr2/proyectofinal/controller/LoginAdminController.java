package co.edu.uniquindio.pr2.proyectofinal.controller;

import co.edu.uniquindio.pr2.proyectofinal.LogisticaApplication;
import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import co.edu.uniquindio.pr2.proyectofinal.services.ILogisticaMapping;
import co.edu.uniquindio.pr2.proyectofinal.mapping.mappers.LogisticaMappingImpl;
import co.edu.uniquindio.pr2.proyectofinal.model.Administrador;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.stream.Collectors;

public class LoginAdminController {

    @FXML private Button btnIniciarSesion;
    @FXML private Label lblMensaje;
    @FXML private Hyperlink linkOlvidePassword;
    @FXML private Hyperlink linkRegistrarse;
    @FXML private Hyperlink linkUsuario;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;

    private ModelFactory modelFactory;
    private final ILogisticaMapping mapping = new LogisticaMappingImpl();

    @FXML
    private void initialize() {
        modelFactory = ModelFactory.getInstance();
        modelFactory.inicializarDatos();
    }

    @FXML
    public void handleLogin() {
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

        Administrador admin = modelFactory.getEmpresaLogistica().buscarAdministradorPorCorreo(correo);

        if (admin == null || !admin.getPassword().equals(password)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de inicio de sesión");
            alert.setHeaderText(null);
            alert.setContentText("El correo o la contraseña son incorrectos.");
            alert.showAndWait();
            return;
        }

        var adminMapped = mapping.mapFromAdministrador(admin);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bienvenido Administrador");
        alert.setHeaderText(null);
        alert.setContentText("Bienvenid@ " + admin.getNombre() + "!");
        alert.showAndWait();

        lblMensaje.setText("Bienvenid@ Administrador " + admin.getNombre());
        abrirVentana("adminMenu.fxml", "Panel Administrador");
    }

    private void abrirVentana(String fxml, String titulo) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LogisticaApplication.class.getResource(fxml));
            Parent root = fxmlLoader.load();
            StackPane stackRoot = new StackPane(root);
            Scene scene = new Scene(stackRoot);
            stackRoot.prefWidthProperty().bind(scene.widthProperty());
            stackRoot.prefHeightProperty().bind(scene.heightProperty());
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

    @FXML
    public void handleRegister() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LogisticaApplication.class.getResource("registroAdmin.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Registro Administrador");
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

        String administradores = empresa.getAdministradores().stream()
                .map(a -> "Nombre: " + a.getNombre() + " — Correo: " + a.getCorreo() + " — Contraseña: " + a.getPassword())
                .collect(Collectors.joining("\n"));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Administradores registrados");
        alert.setHeaderText("Administradores existentes");
        alert.setContentText(administradores.isEmpty() ? "No hay administradores registrados." : administradores);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    @FXML
    public void handleGoToUsuarioLogin() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LogisticaApplication.class.getResource("LoginUsuario.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
            Stage stage = new Stage();
            stage.setTitle("Login Usuario");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
            Stage loginStage = (Stage) linkUsuario.getScene().getWindow();
            loginStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}