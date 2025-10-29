package co.edu.uniquindio.pr2.proyectofinal.viewcontroller;

import co.edu.uniquindio.pr2.proyectofinal.controller.HistorialEnviosController;
import co.edu.uniquindio.pr2.proyectofinal.model.Envio;
import co.edu.uniquindio.pr2.proyectofinal.model.Incidencia;
import co.edu.uniquindio.pr2.proyectofinal.model.ServicioAdicional;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class HistorialEnviosViewController {

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

    private final HistorialEnviosController controller = new HistorialEnviosController();
    private List<Envio> listaBase;
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    private void initialize() {
        listaBase = controller.obtenerEnviosUsuarioActual();
        colCodigo.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getIdEnvio()));
        colEstado.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getEstado() != null ? c.getValue().getEstado().name() : "N/A"));
        colFechaCreacion.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getFechaCreacion() != null ? c.getValue().getFechaCreacion().format(fmt) : "N/A"));
        colEntrega.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getFechaEstimadaEntrega() != null ? c.getValue().getFechaEstimadaEntrega().format(fmt) : "N/A"));
        colCosto.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty("$" + String.format("%.2f", controller.calcularCostoTotalEnvio(c.getValue()))));
        colIncidencias.setCellValueFactory(c -> {
            List<Incidencia> incidencias = c.getValue().getListaIncidencias();
            String texto = incidencias == null || incidencias.isEmpty()
                    ? "Ninguna"
                    : incidencias.stream().map(i -> i.getDescripcion() + " (" + i.getEstadoIncidencia() + ")").collect(Collectors.joining(", "));
            return new javafx.beans.property.SimpleStringProperty(texto);
        });
        colServicios.setCellValueFactory(c -> {
            List<ServicioAdicional> servicios = c.getValue().getListaServiciosAdicionales();
            String texto = servicios == null || servicios.isEmpty()
                    ? "Ninguno"
                    : servicios.stream().map(s -> s.getTipoServicio() + " ($" + String.format("%.0f", s.getCostoServicioAdd()) + ")").collect(Collectors.joining(", "));
            return new javafx.beans.property.SimpleStringProperty(texto);
        });
        tablaEnvios.setItems(FXCollections.observableArrayList(listaBase));
        estadoCombo.getItems().clear();
        estadoCombo.getItems().add("Todos");
        estadoCombo.getItems().addAll(controller.obtenerNombresEstados());
        estadoCombo.setValue("Todos");
        filtrarBtn.setOnAction(e -> onFiltrar());
        exportarPDFBtn.setOnAction(e -> controller.exportarPDF(tablaEnvios.getItems()));
        exportarCSVBtn.setOnAction(e -> controller.exportarCSV(tablaEnvios.getItems()));
        closeBtn.setOnAction(e -> {
            Stage stage = (Stage) closeBtn.getScene().getWindow();
            stage.close();
        });
    }

    private void onFiltrar() {
        String estadoSeleccionado = estadoCombo.getValue();
        String codigoBusqueda = buscarField.getText();
        List<Envio> filtrados = controller.filtrarEnvios(listaBase, estadoSeleccionado, codigoBusqueda);
        tablaEnvios.setItems(FXCollections.observableArrayList(filtrados));
    }
}