package co.edu.uniquindio.pr2.proyectofinal.controller;

import co.edu.uniquindio.pr2.proyectofinal.builder.IncidenciaBuilder;
import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import co.edu.uniquindio.pr2.proyectofinal.mapping.dto.EnvioDTO;
import co.edu.uniquindio.pr2.proyectofinal.services.ILogisticaMapping;
import co.edu.uniquindio.pr2.proyectofinal.mapping.mappers.LogisticaMappingImpl;
import co.edu.uniquindio.pr2.proyectofinal.model.*;
import co.edu.uniquindio.pr2.proyectofinal.viewcontroller.GestionEnviosViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GestionEnviosController {

    private final ModelFactory modelFactory = ModelFactory.getInstance();
    private final ILogisticaMapping mapping = new LogisticaMappingImpl();

    private GestionEnviosViewController viewController;
    public void setViewController(GestionEnviosViewController viewController) {
        this.viewController = viewController;
    }

    private TableView<Envio> tablaEnvios;
    private TableColumn<Envio, String> colCodigo;
    private TableColumn<Envio, String> colUsuario;
    private TableColumn<Envio, String> colOrigen;
    private TableColumn<Envio, String> colDestino;
    private TableColumn<Envio, Double> colPeso;
    private TableColumn<Envio, Double> colVolumen;
    private TableColumn<Envio, Repartidor> colRepartidor;
    private TableColumn<Envio, Boolean> colSeguro;
    private TableColumn<Envio, Boolean> colFragil;
    private TableColumn<Envio, Boolean> colFirma;
    private TableColumn<Envio, EstadoEnvio> colEstado;
    private TableColumn<Envio, EstadoIncidencia> colIncidencias;
    private TableColumn<Envio, LocalDate> colEntrega;
    private TableColumn<Envio, Double> colCosto;
    private TableColumn<Envio, String> colFecha;
    private TextField txtBuscar;
    private ObservableList<Envio> listaEnvios;

    public GestionEnviosController(TableView<Envio> tablaEnvios,
                                   TableColumn<Envio, String> colCodigo,
                                   TableColumn<Envio, String> colUsuario,
                                   TableColumn<Envio, String> colOrigen,
                                   TableColumn<Envio, String> colDestino,
                                   TableColumn<Envio, Double> colPeso,
                                   TableColumn<Envio, Double> colVolumen,
                                   TableColumn<Envio, Repartidor> colRepartidor,
                                   TableColumn<Envio, Boolean> colSeguro,
                                   TableColumn<Envio, Boolean> colFragil,
                                   TableColumn<Envio, Boolean> colFirma,
                                   TableColumn<Envio, EstadoEnvio> colEstado,
                                   TableColumn<Envio, EstadoIncidencia> colIncidencias,
                                   TableColumn<Envio, LocalDate> colEntrega,
                                   TableColumn<Envio, Double> colCosto,
                                   TableColumn<Envio, String> colFecha,
                                   TextField txtBuscar) {
        this.tablaEnvios = tablaEnvios;
        this.colCodigo = colCodigo;
        this.colUsuario = colUsuario;
        this.colOrigen = colOrigen;
        this.colDestino = colDestino;
        this.colPeso = colPeso;
        this.colVolumen = colVolumen;
        this.colRepartidor = colRepartidor;
        this.colSeguro = colSeguro;
        this.colFragil = colFragil;
        this.colFirma = colFirma;
        this.colEstado = colEstado;
        this.colIncidencias = colIncidencias;
        this.colEntrega = colEntrega;
        this.colCosto = colCosto;
        this.colFecha = colFecha;
        this.txtBuscar = txtBuscar;
    }

    public List<EnvioDTO> obtenerEnviosDTO() {
        return listaEnvios.stream()
                .map(mapping::mapFromEnvio)
                .collect(Collectors.toList());
    }

    public void inicializarTabla() {
        listaEnvios = FXCollections.observableArrayList(modelFactory.getEmpresaLogistica().getEnvios());
        tablaEnvios.setItems(listaEnvios);
        tablaEnvios.setEditable(true);

        colCodigo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getIdEnvio()));
        colUsuario.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getUsuario().getNombre()));
        colFecha.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getFechaCreacion().toString()));
        colOrigen.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getOrigen().getCalle()));
        colOrigen.setCellFactory(TextFieldTableCell.forTableColumn());
        colOrigen.setOnEditCommit(event -> event.getRowValue().getOrigen().setCalle(event.getNewValue()));
        colDestino.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDestino().getCalle()));
        colDestino.setCellFactory(TextFieldTableCell.forTableColumn());
        colDestino.setOnEditCommit(event -> event.getRowValue().getDestino().setCalle(event.getNewValue()));
        colPeso.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getPeso()).asObject());
        colPeso.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colPeso.setOnEditCommit(event -> {
            event.getRowValue().setPeso(event.getNewValue());
            tablaEnvios.refresh();
        });
        colVolumen.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(
                data.getValue().getLargo() * data.getValue().getAncho() * data.getValue().getAlto()).asObject());
        colVolumen.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colVolumen.setOnEditCommit(event -> {
            double cubica = Math.cbrt(event.getNewValue());
            event.getRowValue().setLargo(cubica);
            event.getRowValue().setAncho(cubica);
            event.getRowValue().setAlto(cubica);
            tablaEnvios.refresh();
        });
        colRepartidor.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getRepartidor()));
        colRepartidor.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(modelFactory.getEmpresaLogistica().getRepartidores())));
        colRepartidor.setOnEditCommit(event -> event.getRowValue().setRepartidor(event.getNewValue()));
        colSeguro.setCellValueFactory(data -> new javafx.beans.property.SimpleBooleanProperty(
                data.getValue().getListaServiciosAdicionales().stream().anyMatch(s -> s.getTipoServicio() == TipoServicio.SEGURO)));
        colSeguro.setCellFactory(tc -> new CheckBoxTableCell<>());
        colFragil.setCellValueFactory(data -> new javafx.beans.property.SimpleBooleanProperty(
                data.getValue().getListaServiciosAdicionales().stream().anyMatch(s -> s.getTipoServicio() == TipoServicio.FRAGIL)));
        colFragil.setCellFactory(tc -> new CheckBoxTableCell<>());
        colFirma.setCellValueFactory(data -> new javafx.beans.property.SimpleBooleanProperty(
                data.getValue().getListaServiciosAdicionales().stream().anyMatch(s -> s.getTipoServicio() == TipoServicio.FIRMA_REQUERIDA)));
        colFirma.setCellFactory(tc -> new CheckBoxTableCell<>());
        colEstado.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getEstado()));
        colEstado.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(EstadoEnvio.values())));
        colEstado.setOnEditCommit(event -> event.getRowValue().setEstado(event.getNewValue()));

        ObservableList<EstadoIncidencia> opcionesIncidencias = FXCollections.observableArrayList(EstadoIncidencia.values());
        opcionesIncidencias.add(0, null);
        colIncidencias.setCellValueFactory(data -> {
            if (data.getValue().getListaIncidencias().isEmpty()) return new javafx.beans.property.SimpleObjectProperty<>(null);
            return new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getListaIncidencias().get(0).getEstadoIncidencia());
        });
        colIncidencias.setCellFactory(ComboBoxTableCell.forTableColumn(opcionesIncidencias));
        colIncidencias.setOnEditCommit(event -> {
            event.getRowValue().getListaIncidencias().clear();
            if (event.getNewValue() != null) {
                Incidencia incidencia = new IncidenciaBuilder()
                        .descripcion("Incidencia seleccionada: " + event.getNewValue())
                        .estadoIncidencia(event.getNewValue())
                        .fecha(LocalDate.now())
                        .envioAsociado(event.getRowValue())
                        .build();
                event.getRowValue().getListaIncidencias().add(incidencia);
                modelFactory.getEmpresaLogistica().getIncidencias().add(incidencia);
            }
            tablaEnvios.refresh();
        });

        colEntrega.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getFechaEstimadaEntrega()));
        colEntrega.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
        colEntrega.setOnEditCommit(event -> {
            event.getRowValue().setFechaEstimadaEntrega(event.getNewValue());
            tablaEnvios.refresh();
        });

        colCosto.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getCosto()).asObject());
        colCosto.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        tablaEnvios.refresh();
    }

    public void crearEnvio() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/pr2/proyectofinal/crearEnvioAdmin.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load(), 700, 500));
            stage.setTitle("Crear Envío (Administrador)");
            stage.centerOnScreen();
            stage.showAndWait();
            listaEnvios.setAll(modelFactory.getEmpresaLogistica().getEnvios());
            tablaEnvios.refresh();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void buscarEnvio() {
        String codigo = txtBuscar.getText().trim().toLowerCase();
        if (codigo.isEmpty()) {
            tablaEnvios.setItems(listaEnvios);
            return;
        }
        List<Envio> filtrados = listaEnvios.stream()
                .filter(e -> e.getIdEnvio().toLowerCase().contains(codigo))
                .collect(Collectors.toList());
        tablaEnvios.setItems(FXCollections.observableArrayList(filtrados));
    }

    public void eliminarEnvio() {
        Envio envio = tablaEnvios.getSelectionModel().getSelectedItem();
        if (envio == null) {
            mostrarAlerta("Error", "Seleccione un envío para eliminar.");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar eliminación");
        confirm.setHeaderText(null);
        confirm.setContentText("¿Desea eliminar el envío seleccionado?");
        confirm.showAndWait().ifPresent(r -> {
            if (r == ButtonType.OK) {
                modelFactory.getEmpresaLogistica().getEnvios().remove(envio);
                listaEnvios.remove(envio);
                tablaEnvios.refresh();
                mostrarAlerta("Éxito", "Envío eliminado correctamente.");
            }
        });
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}