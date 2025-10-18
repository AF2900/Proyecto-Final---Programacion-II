package co.edu.uniquindio.pr2.proyectofinal.controller;

import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import co.edu.uniquindio.pr2.proyectofinal.model.Usuario;
import co.edu.uniquindio.pr2.proyectofinal.builder.UsuarioBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class UsuarioController {

    @FXML private TextField txtIdUsuario;
    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtTelefono;
    @FXML private PasswordField txtPassword;
    @FXML private Button btnAgregarUsuario;
    @FXML private Button btnActualizarUsuario;
    @FXML private Button btnEliminarUsuario;
    @FXML private Button btnLimpiarCampos;
    @FXML private TableView<Usuario> tablaUsuarios;
    @FXML private TableColumn<Usuario, String> colIdUsuario;
    @FXML private TableColumn<Usuario, String> colNombre;
    @FXML private TableColumn<Usuario, String> colCorreo;
    @FXML private TableColumn<Usuario, String> colTelefono;
    @FXML private TableColumn<Usuario, String> colPassword;

    private ObservableList<Usuario> listaUsuarios;
    private final ModelFactory modelFactory = ModelFactory.getInstance();

    @FXML
    public void initialize() {
        listaUsuarios = FXCollections.observableArrayList(modelFactory.getEmpresaLogistica().getUsuarios());
        colIdUsuario.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        tablaUsuarios.setItems(listaUsuarios);
        tablaUsuarios.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                txtIdUsuario.setText(newSel.getIdUsuario());
                txtNombre.setText(newSel.getNombre());
                txtCorreo.setText(newSel.getCorreo());
                txtTelefono.setText(newSel.getTelefono());
                txtPassword.setText(newSel.getPassword());
            }
        });
    }

    @FXML
    private void OnAgregarUsuario() {
        if (camposVacios()) {
            mostrarAlerta("Error", "Debe llenar todos los campos");
            return;
        }
        if (!correoValido(txtCorreo.getText())) {
            mostrarAlerta("Error", "Ingrese un correo válido.");
            return;
        }
        String id = txtIdUsuario.getText();
        boolean existe = listaUsuarios.stream().anyMatch(u -> u.getIdUsuario().equals(id));
        if (existe) {
            mostrarAlerta("Error", "Ya existe un usuario con el ID: " + id);
            return;
        }
        Usuario u = new UsuarioBuilder()
                .idUsuario(txtIdUsuario.getText())
                .nombre(txtNombre.getText())
                .correo(txtCorreo.getText())
                .telefono(txtTelefono.getText())
                .password(txtPassword.getText())
                .build();
        listaUsuarios.add(u);
        modelFactory.getEmpresaLogistica().getUsuarios().add(u);
        tablaUsuarios.refresh();
        OnLimpiarCampos();
        mostrarInfo("Usuario agregado correctamente.");
    }

    @FXML
    private void OnActualizarUsuario() {
        Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Error", "Debe seleccionar un usuario para actualizar");
            return;
        }
        if (camposVacios()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios");
            return;
        }
        if (!correoValido(txtCorreo.getText())) {
            mostrarAlerta("Error", "Ingrese un correo válido.");
            return;
        }
        String nuevoId = txtIdUsuario.getText();
        if (!nuevoId.equals(seleccionado.getIdUsuario())) {
            boolean idExiste = listaUsuarios.stream()
                    .anyMatch(u -> u.getIdUsuario().equals(nuevoId));
            if (idExiste) {
                mostrarAlerta("Error", "Ya existe otro usuario con el ID: " + nuevoId);
                return;
            }
        }
        seleccionado.setIdUsuario(nuevoId);
        seleccionado.setNombre(txtNombre.getText());
        seleccionado.setCorreo(txtCorreo.getText());
        seleccionado.setTelefono(txtTelefono.getText());
        seleccionado.setPassword(txtPassword.getText());
        tablaUsuarios.refresh();
        OnLimpiarCampos();
        mostrarInfo("Usuario actualizado correctamente.");
    }

    @FXML
    private void OnEliminarUsuario() {
        Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Error", "Debe seleccionar un usuario para eliminar");
            return;
        }
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Está seguro de eliminar al usuario seleccionado?");
        confirmacion.showAndWait().ifPresent(respuesta -> {
            if (respuesta == ButtonType.OK) {
                listaUsuarios.remove(seleccionado);
                modelFactory.getEmpresaLogistica().getUsuarios().remove(seleccionado);
                OnLimpiarCampos();
                mostrarInfo("Usuario eliminado correctamente.");
            }
        });
    }

    @FXML
    private void OnLimpiarCampos() {
        txtIdUsuario.clear();
        txtNombre.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        txtPassword.clear();
        tablaUsuarios.getSelectionModel().clearSelection();
    }

    private boolean camposVacios() {
        return txtIdUsuario.getText().isEmpty() ||
                txtNombre.getText().isEmpty() ||
                txtCorreo.getText().isEmpty() ||
                txtTelefono.getText().isEmpty() ||
                txtPassword.getText().isEmpty();
    }

    private boolean correoValido(String correo) {
        return correo.contains("@");
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarInfo(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}