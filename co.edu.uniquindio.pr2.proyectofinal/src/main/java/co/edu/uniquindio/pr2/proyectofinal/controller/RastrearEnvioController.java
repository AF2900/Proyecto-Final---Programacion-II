package co.edu.uniquindio.pr2.proyectofinal.controller;

import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import co.edu.uniquindio.pr2.proyectofinal.model.Envio;
import co.edu.uniquindio.pr2.proyectofinal.model.EstadoEnvio;
import co.edu.uniquindio.pr2.proyectofinal.model.Incidencia;
import co.edu.uniquindio.pr2.proyectofinal.model.ServicioAdicional;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RastrearEnvioController {

    public ListView<Envio> listaEnvios;
    public Label lblEstado;
    public TextArea txtDetalles;
    private final ModelFactory modelFactory = ModelFactory.getInstance();

    public void initialize() {
        List<Envio> envios = modelFactory.getEmpresaLogistica().getEnvios();
        if (envios == null || envios.isEmpty()) {
            listaEnvios.setItems(FXCollections.observableArrayList());
            txtDetalles.setText("No hay envíos registrados aún.");
            return;
        }

        listaEnvios.setItems(FXCollections.observableArrayList(envios));
        listaEnvios.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Envio envio, boolean empty) {
                super.updateItem(envio, empty);
                if (empty || envio == null) setText(null);
                else setText("Envío #" + envio.getIdEnvio() + " — Estado: " + envio.getEstado());
            }
        });

        listaEnvios.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, nuevoEnvio) -> {
            if (nuevoEnvio != null) mostrarDetallesEnvio(nuevoEnvio);
        });
    }

    private void mostrarDetallesEnvio(Envio envio) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        lblEstado.setText("Estado: " + envio.getEstado());
        aplicarColorEstado(envio.getEstado());
        double volumen = envio.getLargo() * envio.getAncho() * envio.getAlto();

        StringBuilder detalles = new StringBuilder();
        detalles.append("Código: ").append(envio.getIdEnvio()).append("\n")
                .append("Fecha de creación: ").append(envio.getFechaCreacion() != null ? envio.getFechaCreacion().format(formatter) : "N/A").append("\n")
                .append("Fecha estimada de entrega: ").append(envio.getFechaEstimadaEntrega() != null ? envio.getFechaEstimadaEntrega().format(formatter) : "N/A").append("\n\n")
                .append(String.format("Peso: %.2f kg\n", envio.getPeso()))
                .append(String.format("Dimensiones: %.2f x %.2f x %.2f cm\n", envio.getLargo(), envio.getAncho(), envio.getAlto()))
                .append(String.format("Volumen total: %.2f cm³\n", volumen))
                .append(String.format("Costo: $%.2f\n\n", envio.getCosto()));

        detalles.append("Incidencias:\n");
        if (envio.getListaIncidencias().isEmpty()) {
            detalles.append("   - Ninguna\n\n");
        } else {
            for (Incidencia i : envio.getListaIncidencias()) {
                detalles.append("   - ").append(i.getDescripcion()).append(" (").append(i.getEstadoIncidencia()).append(")\n");
            }
            detalles.append("\n");
        }

        detalles.append("Servicios adicionales:\n");
        if (envio.getListaServiciosAdicionales().isEmpty()) {
            detalles.append("   - Ninguno\n\n");
        } else {
            for (ServicioAdicional s : envio.getListaServiciosAdicionales()) {
                detalles.append("   - ").append(s.getTipoServicio()).append(" (+$").append(String.format("%.0f", s.getCostoServicioAdd())).append(")\n");
            }
            detalles.append("\n");
        }

        detalles.append("Origen: ").append(envio.getOrigen() != null ? envio.getOrigen().getCalle() : "No registrado").append("\n")
                .append("Destino: ").append(envio.getDestino() != null ? envio.getDestino().getCalle() : "No registrado").append("\n\n")
                .append("Repartidor: ").append(envio.getRepartidor() != null ? envio.getRepartidor().getNombre() : "No asignado").append("\n");

        txtDetalles.setText(detalles.toString());
    }

    private void aplicarColorEstado(EstadoEnvio estado) {
        if (estado == null) {
            lblEstado.setTextFill(Color.BLACK);
            return;
        }
        switch (estado) {
            case PENDIENTE -> lblEstado.setTextFill(Color.GRAY);
            case EN_CAMINO -> lblEstado.setTextFill(Color.ORANGE);
            case ENTREGADO -> lblEstado.setTextFill(Color.GREEN);
            case CANCELADO -> lblEstado.setTextFill(Color.RED);
            default -> lblEstado.setTextFill(Color.BLACK);
        }
    }

    public void volverMenuUsuario() {
        Stage stage = (Stage) listaEnvios.getScene().getWindow();
        stage.close();
    }
}