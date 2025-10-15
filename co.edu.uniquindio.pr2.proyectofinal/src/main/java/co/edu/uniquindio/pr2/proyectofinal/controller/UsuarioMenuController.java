package co.edu.uniquindio.pr2.proyectofinal.controller;

import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import co.edu.uniquindio.pr2.proyectofinal.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import co.edu.uniquindio.pr2.proyectofinal.LogisticaApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
            stage.show();

            Stage currentStage = (Stage) logoutBtn.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleCreateShipment(ActionEvent event) {
        mostrarAlerta("Crear envío", "Funcionalidad próximamente disponible.");
    }

    @FXML
    void handleHistory(ActionEvent event) {
        mostrarAlerta("Historial", "Mostrando historial de envíos.");
    }

    @FXML
    void handleProfile(ActionEvent event) {
        mostrarAlerta("Perfil", "Mostrando perfil del usuario.");
    }

    @FXML
    void handleQuickTrack(ActionEvent event) {
        mostrarAlerta("Seguimiento rápido", "Funcionalidad de seguimiento no disponible aún.");
    }

    @FXML
    void handleQuote(ActionEvent event) {
        mostrarAlerta("Cotización", "Funcionalidad de cotización no disponible aún.");
    }

    @FXML
    void handleTrack(ActionEvent event) {
        mostrarAlerta("Seguimiento", "Buscando envío...");
    }

    @FXML
    void handleViewAll(ActionEvent event) {
        mostrarAlerta("Ver todo", "Mostrando todos los envíos.");
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}