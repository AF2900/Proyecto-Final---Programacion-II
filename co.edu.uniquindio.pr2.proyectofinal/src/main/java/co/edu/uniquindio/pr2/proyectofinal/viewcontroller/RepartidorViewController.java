package co.edu.uniquindio.pr2.proyectofinal.viewcontroller;

import co.edu.uniquindio.pr2.proyectofinal.controller.RepartidorController;
import co.edu.uniquindio.pr2.proyectofinal.model.DisponibilidadRepartidor;
import co.edu.uniquindio.pr2.proyectofinal.model.Repartidor;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RepartidorViewController {

    @FXML private Button btnActualizarRepartidor, btnAgregarRepartidor, btnEliminarRepartidor, btnLimpiarCampos;
    @FXML private ComboBox<DisponibilidadRepartidor> cbDisponibilidad;
    @FXML private TableView<Repartidor> tablaRepartidores;
    @FXML private TableColumn<Repartidor, String> colCedula, colIdRepartidor, colNombre, colTelefono, colPassword;
    @FXML private TableColumn<Repartidor, DisponibilidadRepartidor> colDisponibilidad;
    @FXML private TextField txtCedula, txtIdRepartidor, txtNombre, txtPassword, txtTelefono;

    private final RepartidorController controller = new RepartidorController();

    @FXML
    void initialize() {
        cbDisponibilidad.setItems(FXCollections.observableArrayList(DisponibilidadRepartidor.values()));

        colIdRepartidor.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getIdRepartidor()));
        colNombre.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNombre()));
        colCedula.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCedula()));
        colTelefono.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTelefono()));
        colPassword.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getPassword()));
        colDisponibilidad.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getDisponibilidadRepartidor()));

        tablaRepartidores.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        tablaRepartidores.setItems(controller.getListaRepartidores());

        tablaRepartidores.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) mostrarRepartidorSeleccionado(newSel);
        });
    }

    @FXML
    void OnAgregarRepartidor(ActionEvent event) {
        if (!controller.camposValidos(txtIdRepartidor.getText(), txtNombre.getText(), txtCedula.getText(),
                txtTelefono.getText(), txtPassword.getText(), cbDisponibilidad.getValue())) {
            mostrarAlerta("Error", "Todos los campos son obligatorios.", Alert.AlertType.ERROR);
            return;
        }

        if (controller.buscarRepartidorPorId(txtIdRepartidor.getText()) != null) {
            mostrarAlerta("Error", "Ya existe un repartidor con ese ID.", Alert.AlertType.ERROR);
            return;
        }

        controller.agregarRepartidor(txtIdRepartidor.getText(), txtNombre.getText(), txtCedula.getText(),
                txtTelefono.getText(), txtPassword.getText(), cbDisponibilidad.getValue());
        tablaRepartidores.refresh();
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

        if (!controller.camposValidos(txtIdRepartidor.getText(), txtNombre.getText(), txtCedula.getText(),
                txtTelefono.getText(), txtPassword.getText(), cbDisponibilidad.getValue())) {
            mostrarAlerta("Error", "Todos los campos son obligatorios.", Alert.AlertType.ERROR);
            return;
        }

        controller.actualizarRepartidor(seleccionado, txtNombre.getText(), txtCedula.getText(),
                txtTelefono.getText(), txtPassword.getText(), cbDisponibilidad.getValue());
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

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Desea eliminar al repartidor \"" + seleccionado.getNombre() + "\"?",
                ButtonType.YES, ButtonType.NO);
        confirmacion.showAndWait().ifPresent(res -> {
            if (res == ButtonType.YES) {
                controller.eliminarRepartidor(seleccionado);
                tablaRepartidores.refresh();
                limpiarCampos();
                mostrarAlerta("Éxito", "Repartidor eliminado correctamente.", Alert.AlertType.INFORMATION);
            }
        });
    }

    @FXML
    void OnLimpiarCampos(ActionEvent event) {
        limpiarCampos();
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

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}