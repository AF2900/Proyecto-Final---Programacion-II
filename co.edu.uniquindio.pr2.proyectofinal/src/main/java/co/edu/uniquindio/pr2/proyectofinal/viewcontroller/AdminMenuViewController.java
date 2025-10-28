package co.edu.uniquindio.pr2.proyectofinal.viewcontroller;

import co.edu.uniquindio.pr2.proyectofinal.LogisticaApplication;
import co.edu.uniquindio.pr2.proyectofinal.controller.AdminMenuController;
import co.edu.uniquindio.pr2.proyectofinal.model.Administrador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class AdminMenuViewController {

    @FXML private Label activeDeliveryLabel;
    @FXML private Label adminNameLabel;
    @FXML private StackPane contentArea;
    @FXML private Button deliveryBtn;
    @FXML private VBox deliveryContent;
    @FXML private LineChart<String, Number> deliveryTimesChart;
    @FXML private Label incidentsLabel;
    @FXML private Button logoutButton;
    @FXML private Label pendingShipmentsLabel;
    @FXML private ListView<String> recentActivitiesListView;
    @FXML private Button reportsBtn;
    @FXML private VBox reportsContent;
    @FXML private BarChart<String, Number> revenueChart;
    @FXML private PieChart servicesChart;
    @FXML private Button shipmentsBtn;
    @FXML private VBox shipmentsContent;
    @FXML private Label totalUsersLabel;
    @FXML private Button usersBtn;
    @FXML private VBox usersContent;
    @FXML private ScrollPane dashboardContent;

    private final AdminMenuController adminController = new AdminMenuController();
    private Administrador adminActual;
    private Parent usuarioView;
    private Parent repartidorView;
    private Parent gestionEnviosView;

    @FXML
    public void initialize() {
        adminActual = adminController.obtenerAdminActual();
        if (adminActual != null) {
            adminNameLabel.setText("Bienvenid@ " + adminActual.getNombre());
        } else {
            adminNameLabel.setText("");
        }
        recentActivitiesListView.getItems().clear();
        recentActivitiesListView.getItems().add("La puedes ver en las gráficas");
        adminController.actualizarReportes(totalUsersLabel, pendingShipmentsLabel, activeDeliveryLabel,
                incidentsLabel, revenueChart, servicesChart, deliveryTimesChart);
        contentArea.getChildren().setAll(dashboardContent);
        highlightButton(reportsBtn);
    }

    @FXML
    void handleLogout(ActionEvent event) {
        adminController.cerrarSesion();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LogisticaApplication.class.getResource("loginUsuario.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
            Stage currentStage = (Stage) logoutButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showReports(ActionEvent event) {
        contentArea.getChildren().setAll(dashboardContent);
        highlightButton(reportsBtn);
        adminController.actualizarReportes(totalUsersLabel, pendingShipmentsLabel, activeDeliveryLabel,
                incidentsLabel, revenueChart, servicesChart, deliveryTimesChart);
    }

    @FXML
    void showShipments(ActionEvent event) {
        try {
            if (gestionEnviosView == null) {
                FXMLLoader loader = new FXMLLoader(LogisticaApplication.class.getResource("gestionEnvios.fxml"));
                gestionEnviosView = loader.load();
            }
            setAnchors(gestionEnviosView);
            contentArea.getChildren().setAll(gestionEnviosView);
            highlightButton(shipmentsBtn);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showUsers(ActionEvent event) {
        try {
            if (usuarioView == null) {
                FXMLLoader loader = new FXMLLoader(LogisticaApplication.class.getResource("usuario.fxml"));
                usuarioView = loader.load();
            }
            setAnchors(usuarioView);
            contentArea.getChildren().setAll(usuarioView);
            highlightButton(usersBtn);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showDelivery(ActionEvent event) {
        try {
            if (repartidorView == null) {
                FXMLLoader loader = new FXMLLoader(LogisticaApplication.class.getResource("repartidor.fxml"));
                repartidorView = loader.load();
            }
            setAnchors(repartidorView);
            contentArea.getChildren().setAll(repartidorView);
            highlightButton(deliveryBtn);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setAnchors(Parent view) {
        if (view instanceof AnchorPane anchorPane) {
            AnchorPane.setTopAnchor(anchorPane, 0.0);
            AnchorPane.setBottomAnchor(anchorPane, 0.0);
            AnchorPane.setLeftAnchor(anchorPane, 0.0);
            AnchorPane.setRightAnchor(anchorPane, 0.0);
        }
    }

    private void highlightButton(Button activeButton) {
        List<Button> botones = List.of(reportsBtn, usersBtn, deliveryBtn, shipmentsBtn);
        for (Button btn : botones) {
            if (btn == activeButton) {
                btn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 14;");
            } else {
                btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #ecf0f1; -fx-background-radius: 5; -fx-padding: 14;");
            }
        }
    }
}