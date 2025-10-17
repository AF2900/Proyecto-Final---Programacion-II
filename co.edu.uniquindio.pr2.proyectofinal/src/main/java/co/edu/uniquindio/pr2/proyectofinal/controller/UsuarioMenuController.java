package co.edu.uniquindio.pr2.proyectofinal.controller;

import co.edu.uniquindio.pr2.proyectofinal.LogisticaApplication;
import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import co.edu.uniquindio.pr2.proyectofinal.model.Envio;
import co.edu.uniquindio.pr2.proyectofinal.model.EstadoEnvio;
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
import java.util.stream.Collectors;

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
            cargarEnviosRecientes(usuario);
            actualizarResumenEnvios(usuario);
        } else {
            welcomeLabel.setText("No hay sesión activa.");
        }
    }

    private void actualizarResumenEnvios(Usuario usuario) {
        List<Envio> enviosUsuario = modelFactory.getEmpresaLogistica().getEnvios().stream()
                .filter(e -> e.getUsuario() != null && e.getUsuario().equals(usuario))
                .collect(Collectors.toList());

        long total = enviosUsuario.size();
        long activos = enviosUsuario.stream()
                .filter(e -> e.getEstado() == EstadoEnvio.PENDIENTE || e.getEstado() == EstadoEnvio.EN_CAMINO)
                .count();
        long entregados = enviosUsuario.stream()
                .filter(e -> e.getEstado() == EstadoEnvio.ENTREGADO)
                .count();

        totalShipmentsLabel.setText(String.valueOf(total));
        activeShipmentsLabel.setText(String.valueOf(activos));
        deliveredShipmentsLabel.setText(String.valueOf(entregados));
    }

    private void cargarEnviosRecientes(Usuario usuario) {
        recentShipmentsContainer.getChildren().clear();
        List<Envio> enviosUsuario = modelFactory.getEmpresaLogistica().getEnvios().stream()
                .filter(e -> e.getUsuario() != null && e.getUsuario().equals(usuario))
                .collect(Collectors.toList());

        if (enviosUsuario.isEmpty()) {
            recentShipmentsContainer.getChildren().add(noShipmentsLabel);
            noShipmentsLabel.setVisible(true);
        } else {
            noShipmentsLabel.setVisible(false);
            enviosUsuario.stream().limit(5).forEach(envio -> {
                HBox item = new HBox();
                item.setSpacing(10);
                Label codigo = new Label("Código de envío: " + envio.getIdEnvio());
                codigo.setStyle("-fx-font-size: 14px; -fx-text-fill: #2c3e50;");
                item.getChildren().add(codigo);
                recentShipmentsContainer.getChildren().add(item);
            });
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
        Stage stage = abrirVentanaModal("crearEnvio.fxml", "Crear Envío");
        if (stage != null) {
            stage.setOnHidden(e -> {
                Usuario usuario = modelFactory.getUsuarioActual();
                if (usuario != null) {
                    actualizarResumenEnvios(usuario);
                    cargarEnviosRecientes(usuario);
                }
            });
        }
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
        try {
            FXMLLoader loader = new FXMLLoader(LogisticaApplication.class.getResource("historialEnvios.fxml"));
            Scene scene = new Scene(loader.load(), 700, 500);
            Stage stage = new Stage();
            stage.setTitle("Historial de Envíos");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleProfile(ActionEvent event) {
        Usuario usuario = modelFactory.getUsuarioActual();
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

    @FXML
    void handleQuickTrack(ActionEvent event) {
        String codigo = trackingField.getText().trim();
        if (codigo.isEmpty()) {
            mostrarAlerta("Seguimiento rápido", "Por favor, ingresa un código de envío.");
            return;
        }
        Envio envio = modelFactory.getEmpresaLogistica().getEnvios().stream()
                .filter(e -> e.getIdEnvio().equalsIgnoreCase(codigo))
                .findFirst()
                .orElse(null);

        if (envio == null) {
            mostrarAlerta("Seguimiento rápido", "No se encontró ningún envío con el código ingresado.");
            return;
        }

        String resumen = String.format(
                "Código de Envío: %s\n" +
                        "Estado: %s\n" +
                        "Costo: $%.2f\n" +
                        "Peso: %.2f kg\n" +
                        "Origen: %s\n" +
                        "Destino: %s\n" +
                        "Fecha de Creación: %s\n" +
                        "Fecha Estimada de Entrega: %s\n" +
                        "Repartidor: %s\n",
                envio.getIdEnvio(),
                envio.getEstado(),
                envio.getCosto(),
                envio.getPeso(),
                envio.getOrigen() != null ? envio.getOrigen().getCalle() : "No registrado",
                envio.getDestino() != null ? envio.getDestino().getCalle() : "No registrado",
                envio.getFechaCreacion() != null ? envio.getFechaCreacion().toString() : "N/A",
                envio.getFechaEstimadaEntrega() != null ? envio.getFechaEstimadaEntrega().toString() : "N/A",
                envio.getRepartidor() != null ? envio.getRepartidor().getNombre() : "No asignado"
        );

        mostrarAlerta("Resumen del envío", resumen);
    }

    @FXML
    void handleViewAll(ActionEvent event) {
        Usuario usuario = modelFactory.getUsuarioActual();
        if (usuario == null) {
            mostrarAlerta("Ver envíos", "No hay sesión activa.");
            return;
        }

        List<Envio> enviosUsuario = modelFactory.getEmpresaLogistica().getEnvios().stream()
                .filter(e -> e.getUsuario() != null && e.getUsuario().equals(usuario))
                .collect(Collectors.toList());

        if (enviosUsuario.isEmpty()) {
            mostrarAlerta("Ver envíos", "No tienes envíos registrados.");
            return;
        }

        VBox lista = new VBox();
        lista.setSpacing(8);
        for (Envio envio : enviosUsuario) {
            Label lbl = new Label("Código de envío: " + envio.getIdEnvio());
            lbl.setStyle("-fx-font-size: 14px; -fx-text-fill: #2c3e50;");
            lista.getChildren().add(lbl);
        }

        ScrollPane scroll = new ScrollPane(lista);
        scroll.setFitToWidth(true);
        scroll.setPrefHeight(300);

        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Todos los Envíos");
        dialog.getDialogPane().setContent(scroll);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.showAndWait();
    }

    private Stage abrirVentanaModal(String fxml, String titulo) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LogisticaApplication.class.getResource(fxml));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.show();
            return stage;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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