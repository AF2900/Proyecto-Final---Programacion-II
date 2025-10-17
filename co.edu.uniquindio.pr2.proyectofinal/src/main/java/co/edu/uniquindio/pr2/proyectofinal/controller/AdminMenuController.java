package co.edu.uniquindio.pr2.proyectofinal.controller;

import co.edu.uniquindio.pr2.proyectofinal.LogisticaApplication;
import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import co.edu.uniquindio.pr2.proyectofinal.model.Usuario;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.io.IOException;

public class AdminMenuController {

    @FXML
    private Label activeDeliveryLabel;

    @FXML
    private Label adminNameLabel;

    @FXML
    private StackPane contentArea;

    @FXML
    private Button dashboardBtn;

    @FXML
    private ScrollPane dashboardContent;

    @FXML
    private Button deliveryBtn;

    @FXML
    private VBox deliveryContent;

    @FXML
    private LineChart<?, ?> deliveryTimesChart;

    @FXML
    private Label incidentsLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private Label pendingShipmentsLabel;

    @FXML
    private ListView<?> recentActivitiesListView;

    @FXML
    private Button reportsBtn;

    @FXML
    private VBox reportsContent;

    @FXML
    private BarChart<?, ?> revenueChart;

    @FXML
    private PieChart servicesChart;

    @FXML
    private Button shipmentsBtn;

    @FXML
    private VBox shipmentsContent;

    @FXML
    private Label totalUsersLabel;

    @FXML
    private Button usersBtn;

    @FXML
    private VBox usersContent;

    private final ModelFactory modelFactory = ModelFactory.getInstance();
    private Usuario usuarioActual;

    @FXML
    public void initialize() {
        usuarioActual = modelFactory.getUsuarioActual();
        if (usuarioActual != null) {
            adminNameLabel.setText("Bienvenido " + usuarioActual.getNombre());
        } else {
            adminNameLabel.setText("Sin sesión");
        }
    }

    @FXML
    void handleLogout(ActionEvent event) {
        modelFactory.setUsuarioActual(null);
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
    void showDashboard(ActionEvent event) {
        contentArea.getChildren().setAll(dashboardContent);
    }

    @FXML
    void showReports(ActionEvent event) {
        contentArea.getChildren().setAll(reportsContent);
    }

    @FXML
    void showShipments(ActionEvent event) {
        contentArea.getChildren().setAll(shipmentsContent);
    }

    private Parent usuarioView;
    private Parent repartidorView;

    @FXML
    void showUsers(ActionEvent event) {
        try {
            if (usuarioView == null) {
                FXMLLoader loader = new FXMLLoader(LogisticaApplication.class.getResource("usuario.fxml"));
                usuarioView = loader.load();
            }
            contentArea.getChildren().setAll(usuarioView);
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
            contentArea.getChildren().setAll(repartidorView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}