package co.edu.uniquindio.pr2.proyectofinal.builder;

import co.edu.uniquindio.pr2.proyectofinal.model.Usuario;

public class UsuarioBuilder {
    private String idUsuario;
    private String nombre;
    private String correo;
    private String telefono;
    private String password;

    public UsuarioBuilder idUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
        return this;
    }

    public UsuarioBuilder nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public UsuarioBuilder correo(String correo) {
        this.correo = correo;
        return this;
    }

    public UsuarioBuilder telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public UsuarioBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UsuarioBuilder from(Usuario u) {
        this.idUsuario = u.getIdUsuario();
        this.nombre = u.getNombre();
        this.correo = u.getCorreo();
        this.telefono = u.getTelefono();
        this.password = u.getPassword();
        return this;
    }

    public Usuario build() {
        return new Usuario(idUsuario, password, nombre, correo, telefono);
    }
}