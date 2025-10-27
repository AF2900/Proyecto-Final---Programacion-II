package co.edu.uniquindio.pr2.proyectofinal.controller;

import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import co.edu.uniquindio.pr2.proyectofinal.model.Envio;
import co.edu.uniquindio.pr2.proyectofinal.model.EstadoEnvio;
import co.edu.uniquindio.pr2.proyectofinal.model.Incidencia;
import co.edu.uniquindio.pr2.proyectofinal.model.ServicioAdicional;
import co.edu.uniquindio.pr2.proyectofinal.model.Usuario;
import co.edu.uniquindio.pr2.proyectofinal.strategy.ExportarCSV;
import co.edu.uniquindio.pr2.proyectofinal.strategy.ExportarPDF;
import co.edu.uniquindio.pr2.proyectofinal.strategy.Exportador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class HistorialEnviosController {

    @FXML private TableView<Envio> tablaEnvios;
    @FXML private TableColumn<Envio, String> colCodigo;
    @FXML private TableColumn<Envio, String> colEstado;
    @FXML private TableColumn<Envio, String> colFechaCreacion;
    @FXML private TableColumn<Envio, String> colEntrega;
    @FXML private TableColumn<Envio, String> colCosto;
    @FXML private TableColumn<Envio, String> colIncidencias;
    @FXML private TableColumn<Envio, String> colServicios;
    @FXML private ComboBox<String> estadoCombo;
    @FXML private TextField buscarField;
    @FXML private Button filtrarBtn;
    @FXML private Button closeBtn;
    @FXML private Button exportarPDFBtn;
    @FXML private Button exportarCSVBtn;

    private final ModelFactory modelFactory = ModelFactory.getInstance();
    private ObservableList<Envio> listaEnvios;
    private final Exportador exportador = new Exportador();

    @FXML
    private void initialize() {
        Usuario usuario = modelFactory.getUsuarioActual();
        if (usuario == null) return;

        List<Envio> enviosUsuario = modelFactory.getEmpresaLogistica().getEnvios().stream()
                .filter(e -> e.getUsuario() != null && e.getUsuario().equals(usuario))
                .collect(Collectors.toList());

        listaEnvios = FXCollections.observableArrayList(enviosUsuario);

        colCodigo.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getIdEnvio()));
        colEstado.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getEstado().name()));
        colFechaCreacion.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getFechaCreacion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        colEntrega.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getFechaEstimadaEntrega().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        colCosto.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty("$" + String.format("%.2f", c.getValue().getCosto())));

        colIncidencias.setCellValueFactory(c -> {
            List<Incidencia> incidencias = c.getValue().getListaIncidencias();
            String texto = incidencias.isEmpty()
                    ? "Ninguna"
                    : incidencias.stream()
                    .map(i -> i.getDescripcion() + " (" + i.getEstadoIncidencia() + ")")
                    .collect(Collectors.joining(", "));
            return new javafx.beans.property.SimpleStringProperty(texto);
        });

        colServicios.setCellValueFactory(c -> {
            List<ServicioAdicional> servicios = c.getValue().getListaServiciosAdicionales();
            String texto = servicios.isEmpty()
                    ? "Ninguno"
                    : servicios.stream()
                    .map(s -> s.getTipoServicio() + " ($" + String.format("%.0f", s.getCostoServicioAdd()) + ")")
                    .collect(Collectors.joining(", "));
            return new javafx.beans.property.SimpleStringProperty(texto);
        });

        tablaEnvios.setItems(listaEnvios);
        estadoCombo.getItems().add("Todos");
        for (EstadoEnvio e : EstadoEnvio.values()) {
            estadoCombo.getItems().add(e.name());
        }
        estadoCombo.setValue("Todos");
    }

    @FXML
    private void filtrarEnvios() {
        String estadoSeleccionado = estadoCombo.getValue();
        String codigoBusqueda = buscarField.getText().trim().toLowerCase();

        List<Envio> filtrados = listaEnvios.stream()
                .filter(e -> (estadoSeleccionado.equals("Todos") || e.getEstado().name().equalsIgnoreCase(estadoSeleccionado)))
                .filter(e -> e.getIdEnvio().toLowerCase().contains(codigoBusqueda))
                .collect(Collectors.toList());

        tablaEnvios.setItems(FXCollections.observableArrayList(filtrados));
    }

    @FXML
    private void exportarPDF() {
        exportador.setEstrategia(new ExportarPDF());
        exportador.ejecutarExportacion(tablaEnvios.getItems());
    }

    @FXML
    private void exportarCSV() {
        exportador.setEstrategia(new ExportarCSV());
        exportador.ejecutarExportacion(tablaEnvios.getItems());
    }

    @FXML
    private void cerrarVentana() {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }
}