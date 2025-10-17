package co.edu.uniquindio.pr2.proyectofinal.controller;

import co.edu.uniquindio.pr2.proyectofinal.LogisticaApplication;
import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import co.edu.uniquindio.pr2.proyectofinal.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

public class UsuarioMenuController {

    @FXML private Label welcomeLabel;
    @FXML private Label totalShipmentsLabel;
    @FXML private Label activeShipmentsLabel;
    @FXML private Label deliveredShipmentsLabel;
    @FXML private Label noShipmentsLabel;
    @FXML private VBox recentShipmentsContainer;
    @FXML private Button createBtn;
    @FXML private Button historyBtn;
    @FXML private Button logoutBtn;
    @FXML private Button profileBtn;
    @FXML private Button quickTrackBtn;
    @FXML private Button quoteBtn;
    @FXML private Button trackBtn;
    @FXML private Button viewAllBtn;
    @FXML private TextField trackingField;

    private final ModelFactory modelFactory = ModelFactory.getInstance();

    @FXML
    private void initialize() {
        Usuario usuario = modelFactory.getUsuarioActual();
        if (usuario != null) {
            welcomeLabel.setText("Bienvenido " + usuario.getNombre());
        } else {
            welcomeLabel.setText("No hay sesión activa.");
        }
    }

    @FXML
    void handleLogout(ActionEvent event) {
        try {
            modelFactory.setUsuarioActual(null);
            FXMLLoader loader = new FXMLLoader(LogisticaApplication.class.getResource("loginUsuario.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Inicio de Sesión");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

            Stage currentStage = (Stage) logoutBtn.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleCreateShipment(ActionEvent event) {
        abrirVentana("crearEnvio.fxml", "Crear Envío");
    }

    @FXML
    void handleQuote(ActionEvent event) {
        abrirVentana("cotizarEnvio.fxml", "Cotizar Envío");
    }

    @FXML
    void handleTrack(ActionEvent event) {
        abrirVentana("rastrearEnvio.fxml", "Rastrear Envío");
    }

    @FXML
    void handleHistory(ActionEvent event) {
        mostrarAlerta("Historial", "Mostrando historial de envíos (en desarrollo).");
    }

    @FXML
    void handleProfile(ActionEvent event) {
        mostrarAlerta("Perfil", "Mostrando perfil del usuario (en desarrollo).");
    }

    @FXML
    void handleQuickTrack(ActionEvent event) {
        mostrarAlerta("Seguimiento rápido", "Funcionalidad de seguimiento rápido en desarrollo.");
    }

    @FXML
    void handleViewAll(ActionEvent event) {
        mostrarAlerta("Ver todo", "Mostrando todos los envíos (en desarrollo).");
    }

    private void abrirVentana(String fxml, String titulo) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LogisticaApplication.class.getResource(fxml));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}