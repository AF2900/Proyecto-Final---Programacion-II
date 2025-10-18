package co.edu.uniquindio.pr2.proyectofinal.controller;

import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import co.edu.uniquindio.pr2.proyectofinal.model.*;
import co.edu.uniquindio.pr2.proyectofinal.builder.RepartidorBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RepartidorController {

    @FXML
    private Button btnActualizarRepartidor, btnAgregarRepartidor, btnEliminarRepartidor, btnLimpiarCampos;

    @FXML
    private ComboBox<DisponibilidadRepartidor> cbDisponibilidad;

    @FXML
    private TableView<Repartidor> tablaRepartidores;

    @FXML
    private TableColumn<Repartidor, String> colCedula, colIdRepartidor, colNombre, colTelefono, colPassword;

    @FXML
    private TableColumn<Repartidor, DisponibilidadRepartidor> colDisponibilidad;

    @FXML
    private TextField txtCedula, txtIdRepartidor, txtNombre, txtPassword, txtTelefono;

    private final ModelFactory modelFactory = ModelFactory.getInstance();
    private ObservableList<Repartidor> listaRepartidores;

    @FXML
    void initialize() {
        inicializarTabla();
        inicializarComboBox();
        cargarRepartidores();
        configurarSeleccionTabla();
    }

    @FXML
    void OnAgregarRepartidor(ActionEvent event) {
        if (!camposValidos()) return;

        String id = txtIdRepartidor.getText();
        if (buscarRepartidorPorId(id) != null) {
            mostrarAlerta("Error", "Ya existe un repartidor con ese ID.", Alert.AlertType.ERROR);
            return;
        }

        Repartidor nuevo = new RepartidorBuilder()
                .idRepartidor(id)
                .nombre(txtNombre.getText())
                .telefono(txtTelefono.getText())
                .cedula(txtCedula.getText())
                .password(txtPassword.getText())
                .disponibilidadRepartidor(cbDisponibilidad.getValue())
                .zonaCobertura("No definida")
                .build();

        modelFactory.getEmpresaLogistica().getRepartidores().add(nuevo);
        listaRepartidores.add(nuevo);
        limpiarCampos();

        mostrarAlerta("Éxito", "Repartidor agregado correctamente.", Alert.AlertType.INFORMATION);
    }

    @FXML
    void OnActualizarRepartidor(ActionEvent event) {
        Repartidor seleccionado = tablaRepartidores.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Advertencia", "Debe seleccionar un repartidor para actualizar.", Alert.AlertType.WARNING);
            return;
        }

        if (!camposValidos()) return;

        seleccionado.setNombre(txtNombre.getText());
        seleccionado.setCedula(txtCedula.getText());
        seleccionado.setTelefono(txtTelefono.getText());
        seleccionado.setPassword(txtPassword.getText());
        seleccionado.setDisponibilidadRepartidor(cbDisponibilidad.getValue());

        tablaRepartidores.refresh();
        mostrarAlerta("Éxito", "Repartidor actualizado correctamente.", Alert.AlertType.INFORMATION);
    }

    @FXML
    void OnEliminarRepartidor(ActionEvent event) {
        Repartidor seleccionado = tablaRepartidores.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Error", "Debe seleccionar un repartidor para eliminar", Alert.AlertType.ERROR);
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmación de eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Estás seguro de que deseas eliminar al repartidor \"" + seleccionado.getNombre() + "\"?");

        ButtonType btnSi = new ButtonType("Sí", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnNo = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmacion.getButtonTypes().setAll(btnSi, btnNo);

        confirmacion.showAndWait().ifPresent(respuesta -> {
            if (respuesta == btnSi) {
                modelFactory.getEmpresaLogistica().getRepartidores().remove(seleccionado);
                listaRepartidores.remove(seleccionado);
                limpiarCampos();
                mostrarAlerta("Éxito", "Repartidor eliminado correctamente.", Alert.AlertType.INFORMATION);
            }
        });
    }

    @FXML
    void OnLimpiarCampos(ActionEvent event) {
        limpiarCampos();
    }

    private void inicializarComboBox() {
        cbDisponibilidad.setItems(FXCollections.observableArrayList(DisponibilidadRepartidor.values()));
    }

    private void inicializarTabla() {
        colIdRepartidor.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getIdRepartidor()));
        colNombre.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombre()));
        colCedula.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCedula()));
        colTelefono.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTelefono()));
        colPassword.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPassword()));
        colDisponibilidad.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getDisponibilidadRepartidor()));
    }

    private void cargarRepartidores() {
        listaRepartidores = FXCollections.observableArrayList(modelFactory.getEmpresaLogistica().getRepartidores());
        tablaRepartidores.setItems(listaRepartidores);
    }

    private void configurarSeleccionTabla() {
        tablaRepartidores.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                mostrarRepartidorSeleccionado(newSel);
            }
        });
    }

    private void mostrarRepartidorSeleccionado(Repartidor r) {
        txtIdRepartidor.setText(r.getIdRepartidor());
        txtNombre.setText(r.getNombre());
        txtCedula.setText(r.getCedula());
        txtTelefono.setText(r.getTelefono());
        txtPassword.setText(r.getPassword());
        cbDisponibilidad.setValue(r.getDisponibilidadRepartidor());
    }

    private void limpiarCampos() {
        txtIdRepartidor.clear();
        txtNombre.clear();
        txtCedula.clear();
        txtTelefono.clear();
        txtPassword.clear();
        cbDisponibilidad.setValue(null);
        tablaRepartidores.getSelectionModel().clearSelection();
    }

    private Repartidor buscarRepartidorPorId(String id) {
        return modelFactory.getEmpresaLogistica().getRepartidores().stream()
                .filter(r -> r.getIdRepartidor().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    private boolean camposValidos() {
        if (txtIdRepartidor.getText().isEmpty() || txtNombre.getText().isEmpty()
                || txtCedula.getText().isEmpty() || txtTelefono.getText().isEmpty()
                || txtPassword.getText().isEmpty() || cbDisponibilidad.getValue() == null) {
            mostrarAlerta("Error", "Todos los campos son obligatorios.", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}