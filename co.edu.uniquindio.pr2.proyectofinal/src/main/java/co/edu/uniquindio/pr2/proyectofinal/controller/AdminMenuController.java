package co.edu.uniquindio.pr2.proyectofinal.controller;

import co.edu.uniquindio.pr2.proyectofinal.LogisticaApplication;
import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import co.edu.uniquindio.pr2.proyectofinal.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
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

public class AdminMenuController {

    @FXML
    private Label activeDeliveryLabel;

    @FXML
    private Label adminNameLabel;

    @FXML
    private StackPane contentArea;

    @FXML
    private Button deliveryBtn;

    @FXML
    private VBox deliveryContent;

    @FXML
    private LineChart<String, Number> deliveryTimesChart;

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
    private BarChart<String, Number> revenueChart;

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

    @FXML
    private ScrollPane dashboardContent;

    private final ModelFactory modelFactory = ModelFactory.getInstance();
    private Usuario usuarioActual;
    private Parent usuarioView;
    private Parent repartidorView;
    private Parent gestionEnviosView;

    @FXML
    public void initialize() {
        usuarioActual = modelFactory.getUsuarioActual();
        if (usuarioActual != null) {
            adminNameLabel.setText("Bienvenid@ " + usuarioActual.getNombre());
        } else {
            adminNameLabel.setText("");
        }
        actualizarReportes();
        contentArea.getChildren().setAll(dashboardContent);
        highlightButton(reportsBtn);
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
    void showReports(ActionEvent event) {
        contentArea.getChildren().setAll(dashboardContent);
        highlightButton(reportsBtn);
        actualizarReportes();
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

    private void actualizarReportes() {
        totalUsersLabel.setText(String.valueOf(modelFactory.getEmpresaLogistica().getUsuarios().size()));
        List<Envio> envios = modelFactory.getEmpresaLogistica().getEnvios();
        long pendientes = envios.stream().filter(e -> e.getEstado() == EstadoEnvio.PENDIENTE).count();
        long enRuta = envios.stream().filter(e -> e.getEstado() == EstadoEnvio.EN_CAMINO).count();
        long entregados = envios.stream().filter(e -> e.getEstado() == EstadoEnvio.ENTREGADO).count();
        long incidencias = envios.stream()
                .filter(e -> e.getListaIncidencias().stream()
                        .anyMatch(i -> i.getEstadoIncidencia() != null))
                .count();
        long repartidoresActivos = modelFactory.getEmpresaLogistica().getRepartidores().stream()
                .filter(r -> r.getDisponibilidadRepartidor() == DisponibilidadRepartidor.DISPONIBLE)
                .count();
        pendingShipmentsLabel.setText(String.valueOf(pendientes));
        activeDeliveryLabel.setText(String.valueOf(repartidoresActivos));
        incidentsLabel.setText(String.valueOf(incidencias));
        revenueChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Ingresos por Servicio Adicional");
        double seguroTotal = envios.stream()
                .flatMap(e -> e.getListaServiciosAdicionales().stream())
                .filter(s -> s.getTipoServicio() == TipoServicio.SEGURO)
                .mapToDouble(ServicioAdicional::getCostoServicioAdd).sum();
        double fragilTotal = envios.stream()
                .flatMap(e -> e.getListaServiciosAdicionales().stream())
                .filter(s -> s.getTipoServicio() == TipoServicio.FRAGIL)
                .mapToDouble(ServicioAdicional::getCostoServicioAdd).sum();
        double firmaTotal = envios.stream()
                .flatMap(e -> e.getListaServiciosAdicionales().stream())
                .filter(s -> s.getTipoServicio() == TipoServicio.FIRMA_REQUERIDA)
                .mapToDouble(ServicioAdicional::getCostoServicioAdd).sum();
        series.getData().add(new XYChart.Data<>("Seguro", seguroTotal));
        series.getData().add(new XYChart.Data<>("Frágil", fragilTotal));
        series.getData().add(new XYChart.Data<>("Firma", firmaTotal));
        revenueChart.getData().add(series);
        servicesChart.getData().clear();
        int totalServicios = (int) envios.stream().flatMap(e -> e.getListaServiciosAdicionales().stream()).count();
        if (totalServicios > 0) {
            long seguroCount = envios.stream().flatMap(e -> e.getListaServiciosAdicionales().stream())
                    .filter(s -> s.getTipoServicio() == TipoServicio.SEGURO).count();
            long fragilCount = envios.stream().flatMap(e -> e.getListaServiciosAdicionales().stream())
                    .filter(s -> s.getTipoServicio() == TipoServicio.FRAGIL).count();
            long firmaCount = envios.stream().flatMap(e -> e.getListaServiciosAdicionales().stream())
                    .filter(s -> s.getTipoServicio() == TipoServicio.FIRMA_REQUERIDA).count();
            servicesChart.getData().add(new PieChart.Data("Seguro", seguroCount));
            servicesChart.getData().add(new PieChart.Data("Frágil", fragilCount));
            servicesChart.getData().add(new PieChart.Data("Firma", firmaCount));
        }
        deliveryTimesChart.getData().clear();
        XYChart.Series<String, Number> lineSeries = new XYChart.Series<>();
        lineSeries.setName("Envíos por día");
        envios.stream()
                .map(Envio::getFechaCreacion)
                .distinct()
                .sorted()
                .forEach(fecha -> {
                    long count = envios.stream().filter(e -> e.getFechaCreacion().equals(fecha)).count();
                    lineSeries.getData().add(new XYChart.Data<>(fecha.toString(), count));
                });
        deliveryTimesChart.getData().add(lineSeries);
    }
}