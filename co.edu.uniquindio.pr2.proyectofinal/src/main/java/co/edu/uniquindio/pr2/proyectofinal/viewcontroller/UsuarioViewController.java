package co.edu.uniquindio.pr2.proyectofinal.viewcontroller;

import co.edu.uniquindio.pr2.proyectofinal.controller.UsuarioController;
import co.edu.uniquindio.pr2.proyectofinal.model.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class UsuarioViewController {

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

    private final UsuarioController controller = new UsuarioController();

    @FXML
    public void initialize() {
        colIdUsuario.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getIdUsuario()));
        colNombre.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNombre()));
        colCorreo.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCorreo()));
        colTelefono.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTelefono()));
        colPassword.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getPassword()));
        tablaUsuarios.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        tablaUsuarios.setItems(controller.getListaUsuarios());
        tablaUsuarios.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) mostrarUsuarioSeleccionado(newSel);
        });
    }

    @FXML
    private void OnAgregarUsuario() {
        if (!controller.camposValidos(txtIdUsuario.getText(), txtNombre.getText(), txtCorreo.getText(),
                txtTelefono.getText(), txtPassword.getText())) {
            mostrarAlerta("Error", "Debe llenar todos los campos");
            return;
        }
        if (!controller.correoValido(txtCorreo.getText())) {
            mostrarAlerta("Error", "Ingrese un correo válido.");
            return;
        }
        if (controller.existeUsuario(txtIdUsuario.getText())) {
            mostrarAlerta("Error", "Ya existe un usuario con el ID: " + txtIdUsuario.getText());
            return;
        }
        controller.agregarUsuario(txtIdUsuario.getText(), txtNombre.getText(), txtCorreo.getText(),
                txtTelefono.getText(), txtPassword.getText());
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
        if (!controller.camposValidos(txtIdUsuario.getText(), txtNombre.getText(), txtCorreo.getText(),
                txtTelefono.getText(), txtPassword.getText())) {
            mostrarAlerta("Error", "Todos los campos son obligatorios");
            return;
        }
        if (!controller.correoValido(txtCorreo.getText())) {
            mostrarAlerta("Error", "Ingrese un correo válido.");
            return;
        }
        String nuevoId = txtIdUsuario.getText();
        if (!nuevoId.equals(seleccionado.getIdUsuario()) && controller.existeUsuario(nuevoId)) {
            mostrarAlerta("Error", "Ya existe otro usuario con el ID: " + nuevoId);
            return;
        }
        controller.actualizarUsuario(seleccionado, nuevoId, txtNombre.getText(), txtCorreo.getText(),
                txtTelefono.getText(), txtPassword.getText());
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
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Está seguro de eliminar al usuario seleccionado?",
                ButtonType.OK, ButtonType.CANCEL);
        confirmacion.showAndWait().ifPresent(res -> {
            if (res == ButtonType.OK) {
                controller.eliminarUsuario(seleccionado);
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

    private void mostrarUsuarioSeleccionado(Usuario u) {
        txtIdUsuario.setText(u.getIdUsuario());
        txtNombre.setText(u.getNombre());
        txtCorreo.setText(u.getCorreo());
        txtTelefono.setText(u.getTelefono());
        txtPassword.setText(u.getPassword());
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
