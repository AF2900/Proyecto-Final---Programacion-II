package co.edu.uniquindio.pr2.proyectofinal.viewcontroller;

import co.edu.uniquindio.pr2.proyectofinal.LogisticaApplication;
import co.edu.uniquindio.pr2.proyectofinal.controller.UsuarioMenuController;
import co.edu.uniquindio.pr2.proyectofinal.model.Envio;
import co.edu.uniquindio.pr2.proyectofinal.model.Incidencia;
import co.edu.uniquindio.pr2.proyectofinal.model.ServicioAdicional;
import co.edu.uniquindio.pr2.proyectofinal.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class UsuarioMenuViewController {

    @FXML private Label welcomeLabel;
    @FXML private Label totalShipmentsLabel;
    @FXML private Label activeShipmentsLabel;
    @FXML private Label deliveredShipmentsLabel;
    @FXML private VBox recentShipmentsContainer;
    @FXML private Button createBtn, historyBtn, logoutBtn, profileBtn, quickTrackBtn, quoteBtn, trackBtn, viewAllBtn;
    @FXML private TextField trackingField;

    private final UsuarioMenuController controller = new UsuarioMenuController();

    @FXML
    private void initialize() {
        Usuario usuario = controller.getUsuarioActual();
        if (usuario != null) {
            welcomeLabel.setText("Bienvenid@ " + usuario.getNombre());
            actualizarResumenEnvios(usuario);
            cargarEnviosRecientes(usuario);
        } else {
            welcomeLabel.setText("No hay sesión activa.");
        }
    }

    public void actualizarResumenEnvios(Usuario usuario) {
        totalShipmentsLabel.setText(String.valueOf(controller.contarTotalEnvios(usuario)));
        activeShipmentsLabel.setText(String.valueOf(controller.contarEnviosActivos(usuario)));
        deliveredShipmentsLabel.setText(String.valueOf(controller.contarEnviosEntregados(usuario)));
    }

    public void cargarEnviosRecientes(Usuario usuario) {
        recentShipmentsContainer.getChildren().clear();
        List<Envio> enviosUsuario = controller.obtenerEnviosUsuario(usuario);
        if (enviosUsuario.isEmpty()) {
            Label noneLabel = new Label("No hay envíos registrados.");
            noneLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");
            recentShipmentsContainer.getChildren().add(noneLabel);
        } else {
            enviosUsuario.stream().limit(5).forEach(envio -> {
                HBox item = new HBox();
                item.setSpacing(10);
                Label codigo = new Label("Código: " + envio.getIdEnvio() + " | Costo Total: $" + String.format("%.2f", controller.calcularCostoTotalEnvio(envio)));
                codigo.setStyle("-fx-font-size: 14px; -fx-text-fill: #2c3e50;");
                item.getChildren().add(codigo);
                recentShipmentsContainer.getChildren().add(item);
            });
        }
    }

    @FXML
    void handleLogout(ActionEvent event) {
        try {
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
    void handleQuickTrack(ActionEvent event) {
        String codigo = trackingField.getText().trim();
        if (codigo.isEmpty()) {
            mostrarAlerta("Seguimiento rápido", "Por favor, ingresa un código de envío.");
            return;
        }
        Envio envio = controller.buscarEnvioPorCodigo(codigo);
        if (envio == null) {
            mostrarAlerta("Seguimiento rápido", "No se encontró ningún envío con el código ingresado.");
            return;
        }
        mostrarResumenEnvio(envio);
    }

    @FXML
    void handleQuote(ActionEvent event) {
        abrirVentana("cotizarEnvio.fxml", "Cotizar Envío");
    }

    @FXML
    void handleCreateShipment(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(LogisticaApplication.class.getResource("crearEnvio.fxml"));
            Scene scene = new Scene(loader.load(), 600, 500);
            Stage stage = new Stage();
            stage.setTitle("Crear Envío");
            stage.setScene(scene);
            CrearEnvioViewController crearController = loader.getController();
            crearController.setUsuarioMenuViewController(this);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleTrack(ActionEvent event) {
        abrirVentana("rastrearEnvio.fxml", "Rastrear Envío");
    }

    @FXML
    void handleHistory(ActionEvent event) {
        abrirVentana("historialEnvios.fxml", "Historial de Envíos");
    }

    @FXML
    void handleProfile(ActionEvent event) {
        Usuario usuario = controller.getUsuarioActual();
        if (usuario != null) {
            String info = "ID Usuario: " + usuario.getIdUsuario() + "\n" +
                    "Nombre: " + usuario.getNombre() + "\n" +
                    "Correo: " + usuario.getCorreo() + "\n" +
                    "Teléfono: " + usuario.getTelefono();
            mostrarAlerta("Perfil de Usuario", info);
        } else {
            mostrarAlerta("Perfil", "No hay sesión activa.");
        }
    }

    private void abrirVentana(String fxml, String titulo) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LogisticaApplication.class.getResource(fxml));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarResumenEnvio(Envio envio) {
        double costoTotal = controller.calcularCostoTotalEnvio(envio);
        StringBuilder resumen = new StringBuilder();
        resumen.append("Código de Envío: ").append(envio.getIdEnvio()).append("\n")
                .append("Estado: ").append(envio.getEstado()).append("\n")
                .append(String.format("Costo Total: $%.2f\n", costoTotal))
                .append(String.format("Peso: %.2f kg\n", envio.getPeso()))
                .append("Origen: ").append(envio.getOrigen() != null ? envio.getOrigen().getCalle() : "No registrado").append("\n")
                .append("Destino: ").append(envio.getDestino() != null ? envio.getDestino().getCalle() : "No registrado").append("\n")
                .append("Fecha de Creación: ").append(envio.getFechaCreacion() != null ? envio.getFechaCreacion() : "N/A").append("\n")
                .append("Fecha Estimada de Entrega: ").append(envio.getFechaEstimadaEntrega() != null ? envio.getFechaEstimadaEntrega() : "N/A").append("\n")
                .append("Repartidor: ").append(envio.getRepartidor() != null ? envio.getRepartidor().getNombre() : "No asignado").append("\n\n")
                .append("Incidencias:\n");
        if (envio.getListaIncidencias().isEmpty()) {
            resumen.append("   - Ninguna\n\n");
        } else {
            for (Incidencia i : envio.getListaIncidencias()) {
                resumen.append("   - ").append(i.getDescripcion())
                        .append(" (").append(i.getEstadoIncidencia()).append(")\n");
            }
            resumen.append("\n");
        }
        resumen.append("Servicios Adicionales:\n");
        if (envio.getListaServiciosAdicionales() == null || envio.getListaServiciosAdicionales().isEmpty()) {
            resumen.append("   - Ninguno\n");
        } else {
            for (ServicioAdicional s : envio.getListaServiciosAdicionales()) {
                resumen.append("   - ").append(s.getTipoServicio())
                        .append(" (+$").append(String.format("%.0f", s.getCostoServicioAdd())).append(")\n");
            }
        }
        mostrarAlerta("Resumen del envío", resumen.toString());
    }

    @FXML
    void handleViewAll(ActionEvent event) {
        Usuario usuario = controller.getUsuarioActual();
        if (usuario == null) {
            mostrarAlerta("Ver todos envíos", "No hay sesión activa.");
            return;
        }
        List<Envio> enviosUsuario = controller.obtenerEnviosUsuario(usuario);
        VBox container = new VBox(10);
        container.setStyle("-fx-padding: 20; -fx-background-color: white;");
        if (enviosUsuario.isEmpty()) {
            Label noneLabel = new Label("No hay envíos registrados.");
            noneLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");
            container.getChildren().add(noneLabel);
        } else {
            for (Envio envio : enviosUsuario) {
                double costoTotal = controller.calcularCostoTotalEnvio(envio);
                HBox item = new HBox(10);
                Label codigo = new Label("Código: " + envio.getIdEnvio() + " | Estado: " + envio.getEstado() + " | Costo Total: $" + String.format("%.2f", costoTotal));
                codigo.setStyle("-fx-font-size: 14px; -fx-text-fill: #2c3e50;");
                item.setOnMouseClicked(e -> mostrarResumenEnvio(envio));
                item.getChildren().add(codigo);
                container.getChildren().add(item);
            }
        }
        ScrollPane scrollPane = new ScrollPane(container);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #ecf0f1;");
        Stage stage = new Stage();
        stage.setTitle("Todos los Envíos");
        stage.setScene(new Scene(scrollPane, 600, 400));
        stage.centerOnScreen();
        stage.show();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}