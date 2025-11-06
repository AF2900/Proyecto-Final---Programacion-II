package co.edu.uniquindio.pr2.proyectofinal.controller;

import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import co.edu.uniquindio.pr2.proyectofinal.model.Usuario;
import co.edu.uniquindio.pr2.proyectofinal.builder.UsuarioBuilder;
import co.edu.uniquindio.pr2.proyectofinal.services.ILogisticaMapping;
import co.edu.uniquindio.pr2.proyectofinal.mapping.mappers.LogisticaMappingImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

public class UsuarioController {

    private final ModelFactory modelFactory = ModelFactory.getInstance();
    private ObservableList<Usuario> listaUsuarios;
    private final ILogisticaMapping mapping = new LogisticaMappingImpl();

    public ObservableList<Usuario> getListaUsuarios() {
        if (listaUsuarios == null) {
            listaUsuarios = FXCollections.observableArrayList(modelFactory.getEmpresaLogistica().getUsuarios());
        }
        return listaUsuarios;
    }

    public boolean camposValidos(String id, String nombre, String correo, String telefono, String password) {
        return !(id.isEmpty() || nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || password.isEmpty());
    }

    public boolean correoValido(String correo) {
        return correo.contains("@");
    }

    public boolean existeUsuario(String id) {
        return getListaUsuarios().stream().anyMatch(u -> u.getIdUsuario().equals(id));
    }

    public void agregarUsuario(String id, String nombre, String correo, String telefono, String password) {
        Usuario u = new UsuarioBuilder()
                .idUsuario(id)
                .nombre(nombre)
                .correo(correo)
                .telefono(telefono)
                .password(password)
                .build();

        getListaUsuarios().add(u);
        modelFactory.getEmpresaLogistica().getUsuarios().add(u);

        var usuarioMapped = mapping.mapFromUsuario(u);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Usuario agregado");
        alert.setHeaderText(null);
        alert.setContentText("Usuario agregado correctamente.");
        alert.showAndWait();
    }

    public void actualizarUsuario(Usuario usuario, String id, String nombre, String correo, String telefono, String password) {
        usuario.setIdUsuario(id);
        usuario.setNombre(nombre);
        usuario.setCorreo(correo);
        usuario.setTelefono(telefono);
        usuario.setPassword(password);

        var usuarioMapped = mapping.mapFromUsuario(usuario);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Usuario actualizado");
        alert.setHeaderText(null);
        alert.setContentText("Usuario actualizado correctamente.");
        alert.showAndWait();
    }

    public void eliminarUsuario(Usuario usuario) {
        getListaUsuarios().remove(usuario);
        modelFactory.getEmpresaLogistica().getUsuarios().remove(usuario);
    }
}