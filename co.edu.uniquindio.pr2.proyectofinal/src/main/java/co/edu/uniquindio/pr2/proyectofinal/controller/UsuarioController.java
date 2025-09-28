package co.edu.uniquindio.pr2.proyectofinal.controller;

import co.edu.uniquindio.pr2.proyectofinal.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.event.ActionEvent;


public class UsuarioController {

    @FXML
    private TextField txtIdUsuario;
    @FXML
    private Button btnActualizarUsuario;

    @FXML
    private Button btnAgregarUsuario;

    @FXML
    private Button btnEliminarUsuario;

    @FXML
    private Button btnLimpiarCampos;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TableView<Usuario> tablaUsuarios;

    @FXML
    private TableColumn<Usuario, String> colIdUsuario;

    @FXML
    private TableColumn<Usuario, String> colNombre;

    @FXML
    private TableColumn<Usuario, String> colCorreo;

    @FXML
    private TableColumn<Usuario, String> colTelefono;

    @FXML
    private Label lblCorreoUsuario;

    @FXML
    private Label lblIdUsuario;

    @FXML
    private Label lblNombreUsuario;

    @FXML
    private Label lblTelefonoUsuario;



    private ObservableList<Usuario> listaUsuarios;

    @FXML
    public void initialize() {
        listaUsuarios = FXCollections.observableArrayList();

        colIdUsuario.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        tablaUsuarios.setItems(listaUsuarios);
    }
    @FXML
    private void OnAgregarUsuario() {
        Usuario u = new Usuario(
                txtIdUsuario.getText(),
                txtNombre.getText(),
                txtCorreo.getText(),
                txtTelefono.getText()
        );
        listaUsuarios.add(u);
        OnLimpiarCampos();
    }

    @FXML
    private void OnActualizarUsuario() {
        Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            seleccionado.setNombre(txtNombre.getText());
            seleccionado.setCorreo(txtCorreo.getText());
            seleccionado.setTelefono(txtTelefono.getText());
            tablaUsuarios.refresh();
        }
    }

    @FXML
    private void OnEliminarUsuario() {
        Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            listaUsuarios.remove(seleccionado);
        }
    }

    @FXML
    private void OnLimpiarCampos() {
        txtIdUsuario.clear();
        txtNombre.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        tablaUsuarios.getSelectionModel().clearSelection();
    }
}
