package co.edu.uniquindio.pr2.proyectofinal.controller;

import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import co.edu.uniquindio.pr2.proyectofinal.services.ILogisticaMapping;
import co.edu.uniquindio.pr2.proyectofinal.mapping.mappers.LogisticaMappingImpl;
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
    private final ILogisticaMapping mapping = new LogisticaMappingImpl();

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
        var envioMapped = mapping.mapFromEnvio(envio);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        lblEstado.setText("Estado: " + envioMapped.estado());
        aplicarColorEstado(envioMapped.estado());

        double volumen = envioMapped.largo() * envioMapped.ancho() * envioMapped.alto();
        double costoTotal = envioMapped.costo();

        StringBuilder detalles = new StringBuilder();
        detalles.append("Código: ").append(envioMapped.idEnvio()).append("\n")
                .append("Fecha de creación: ").append(envioMapped.fechaCreacion() != null ? envioMapped.fechaCreacion().format(formatter) : "N/A").append("\n")
                .append("Fecha estimada de entrega: ").append(envioMapped.fechaEstimadaEntrega() != null ? envioMapped.fechaEstimadaEntrega().format(formatter) : "N/A").append("\n\n")
                .append(String.format("Peso: %.2f kg\n", envioMapped.peso()))
                .append(String.format("Dimensiones: %.2f x %.2f x %.2f cm\n", envioMapped.largo(), envioMapped.ancho(), envioMapped.alto()))
                .append(String.format("Volumen total: %.2f cm³\n", volumen))
                .append(String.format("Costo total: $%.2f\n\n", costoTotal));

        detalles.append("Incidencias:\n");
        if (envioMapped.listaIncidencias().isEmpty()) {
            detalles.append("   - Ninguna\n\n");
        } else {
            for (var i : envioMapped.listaIncidencias()) {
                detalles.append("   - ").append(i.descripcion()).append(" (").append(i.estadoIncidencia()).append(")\n");
            }
            detalles.append("\n");
        }

        detalles.append("Servicios adicionales:\n");
        if (envioMapped.listaServiciosAdicionales().isEmpty()) {
            detalles.append("   - Ninguno\n\n");
        } else {
            for (var s : envioMapped.listaServiciosAdicionales()) {
                detalles.append("   - ").append(s.tipoServicio()).append(" (+$").append(String.format("%.0f", s.costoServicioAdd())).append(")\n");
            }
            detalles.append("\n");
        }

        detalles.append("Origen: ").append(envioMapped.origen() != null ? envioMapped.origen().calle() : "No registrado").append("\n")
                .append("Destino: ").append(envioMapped.destino() != null ? envioMapped.destino().calle() : "No registrado").append("\n\n")
                .append("Repartidor: ").append(envioMapped.idRepartidor() != null ? envioMapped.idRepartidor() : "No asignado").append("\n");

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
