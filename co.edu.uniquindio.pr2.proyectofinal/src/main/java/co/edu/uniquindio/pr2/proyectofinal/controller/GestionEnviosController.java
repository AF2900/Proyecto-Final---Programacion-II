package co.edu.uniquindio.pr2.proyectofinal.controller;

import co.edu.uniquindio.pr2.proyectofinal.builder.IncidenciaBuilder;
import co.edu.uniquindio.pr2.proyectofinal.builder.DireccionBuilder;
import co.edu.uniquindio.pr2.proyectofinal.builder.ServicioAdicionalBuilder;
import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import co.edu.uniquindio.pr2.proyectofinal.model.*;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
import java.util.UUID;
import java.util.stream.Collectors;

public class GestionEnviosController {

    @FXML private TableView<Envio> tablaEnvios;
    @FXML private TableColumn<Envio, String> colCodigo;
    @FXML private TableColumn<Envio, String> colUsuario;
    @FXML private TableColumn<Envio, String> colOrigen;
    @FXML private TableColumn<Envio, String> colDestino;
    @FXML private TableColumn<Envio, Double> colPeso;
    @FXML private TableColumn<Envio, Double> colVolumen;
    @FXML private TableColumn<Envio, Repartidor> colRepartidor;
    @FXML private TableColumn<Envio, Boolean> colSeguro;
    @FXML private TableColumn<Envio, Boolean> colFragil;
    @FXML private TableColumn<Envio, Boolean> colFirma;
    @FXML private TableColumn<Envio, EstadoEnvio> colEstado;
    @FXML private TableColumn<Envio, EstadoIncidencia> colIncidencias;
    @FXML private TableColumn<Envio, LocalDate> colEntrega;
    @FXML private TableColumn<Envio, Double> colCosto;
    @FXML private TableColumn<Envio, String> colFecha;
    @FXML private TextField txtBuscar;

    private final ModelFactory modelFactory = ModelFactory.getInstance();
    private ObservableList<Envio> listaEnvios;

    @FXML
    private void initialize() {
        listaEnvios = FXCollections.observableArrayList(modelFactory.getEmpresaLogistica().getEnvios());
        tablaEnvios.setItems(listaEnvios);
        tablaEnvios.setEditable(true);

        colCodigo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIdEnvio()));
        colUsuario.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUsuario().getNombre()));
        colFecha.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFechaCreacion().toString()));

        colOrigen.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getOrigen().getCalle()));
        colOrigen.setCellFactory(TextFieldTableCell.forTableColumn());
        colOrigen.setOnEditCommit(event -> event.getRowValue().getOrigen().setCalle(event.getNewValue()));

        colDestino.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDestino().getCalle()));
        colDestino.setCellFactory(TextFieldTableCell.forTableColumn());
        colDestino.setOnEditCommit(event -> event.getRowValue().getDestino().setCalle(event.getNewValue()));

        colPeso.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPeso()).asObject());
        colPeso.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colPeso.setOnEditCommit(event -> event.getRowValue().setPeso(event.getNewValue()));

        colVolumen.setCellValueFactory(data -> new SimpleDoubleProperty(Math.pow(data.getValue().getAlto(),3)).asObject());
        colVolumen.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colVolumen.setOnEditCommit(event -> {
            double cubica = Math.cbrt(event.getNewValue());
            event.getRowValue().setAlto(cubica);
            event.getRowValue().setAncho(cubica);
            event.getRowValue().setLargo(cubica);
        });

        colRepartidor.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getRepartidor()));
        colRepartidor.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(modelFactory.getEmpresaLogistica().getRepartidores())));
        colRepartidor.setOnEditCommit(event -> event.getRowValue().setRepartidor(event.getNewValue()));

        colSeguro.setCellValueFactory(data -> new SimpleBooleanProperty(data.getValue().getListaServiciosAdicionales().stream().anyMatch(s -> s.getTipoServicio() == TipoServicio.SEGURO)));
        colSeguro.setCellFactory(tc -> new CheckBoxTableCell<>());

        colFragil.setCellValueFactory(data -> new SimpleBooleanProperty(data.getValue().getListaServiciosAdicionales().stream().anyMatch(s -> s.getTipoServicio() == TipoServicio.FRAGIL)));
        colFragil.setCellFactory(tc -> new CheckBoxTableCell<>());

        colFirma.setCellValueFactory(data -> new SimpleBooleanProperty(data.getValue().getListaServiciosAdicionales().stream().anyMatch(s -> s.getTipoServicio() == TipoServicio.FIRMA_REQUERIDA)));
        colFirma.setCellFactory(tc -> new CheckBoxTableCell<>());

        colEstado.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getEstado()));
        colEstado.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(EstadoEnvio.values())));
        colEstado.setOnEditCommit(event -> event.getRowValue().setEstado(event.getNewValue()));

        colIncidencias.setCellValueFactory(data -> {
            if (data.getValue().getListaIncidencias().isEmpty()) return new SimpleObjectProperty<>(null);
            return new SimpleObjectProperty<>(data.getValue().getListaIncidencias().get(0).getEstadoIncidencia());
        });
        colIncidencias.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(EstadoIncidencia.values())));
        colIncidencias.setOnEditCommit(event -> {
            event.getRowValue().getListaIncidencias().clear();
            Incidencia incidencia = new IncidenciaBuilder()
                    .descripcion("Incidencia seleccionada: " + event.getNewValue())
                    .estadoIncidencia(event.getNewValue())
                    .fecha(LocalDate.now())
                    .envioAsociado(event.getRowValue())
                    .build();
            event.getRowValue().getListaIncidencias().add(incidencia);
            modelFactory.getEmpresaLogistica().getIncidencias().add(incidencia);
            tablaEnvios.refresh();
        });

        colEntrega.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getFechaEstimadaEntrega()));
        colEntrega.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
        colEntrega.setOnEditCommit(event -> event.getRowValue().setFechaEstimadaEntrega(event.getNewValue()));

        colCosto.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getCosto()).asObject());
        colCosto.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colCosto.setOnEditCommit(event -> event.getRowValue().setCosto(event.getNewValue()));
    }

    @FXML
    private void buscarEnvio() {
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

    @FXML
    private void crearEnvio() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/pr2/proyectofinal/crearEnvioAdmin.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load(), 700, 500));
            stage.setTitle("Crear Envío (Administrador)");
            stage.centerOnScreen();
            stage.showAndWait();
            listaEnvios.setAll(modelFactory.getEmpresaLogistica().getEnvios());
            for (Envio e : listaEnvios) {
                if (new Random().nextDouble() < 0.2) {
                    EstadoIncidencia estadoRandom = EstadoIncidencia.values()[new Random().nextInt(EstadoIncidencia.values().length)];
                    Incidencia incidencia = new IncidenciaBuilder()
                            .descripcion("Incidencia automática: " + estadoRandom)
                            .estadoIncidencia(estadoRandom)
                            .fecha(LocalDate.now())
                            .envioAsociado(e)
                            .build();
                    e.getListaIncidencias().add(incidencia);
                    modelFactory.getEmpresaLogistica().getIncidencias().add(incidencia);
                }
            }
            tablaEnvios.refresh();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void eliminarEnvio() {
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