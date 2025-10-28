package co.edu.uniquindio.pr2.proyectofinal.controller;

import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import co.edu.uniquindio.pr2.proyectofinal.model.Usuario;
import co.edu.uniquindio.pr2.proyectofinal.builder.UsuarioBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UsuarioController {

    private final ModelFactory modelFactory = ModelFactory.getInstance();
    private ObservableList<Usuario> listaUsuarios;

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
    }

    public void actualizarUsuario(Usuario usuario, String id, String nombre, String correo, String telefono, String password) {
        usuario.setIdUsuario(id);
        usuario.setNombre(nombre);
        usuario.setCorreo(correo);
        usuario.setTelefono(telefono);
        usuario.setPassword(password);
    }

    public void eliminarUsuario(Usuario usuario) {
        getListaUsuarios().remove(usuario);
        modelFactory.getEmpresaLogistica().getUsuarios().remove(usuario);
    }
}