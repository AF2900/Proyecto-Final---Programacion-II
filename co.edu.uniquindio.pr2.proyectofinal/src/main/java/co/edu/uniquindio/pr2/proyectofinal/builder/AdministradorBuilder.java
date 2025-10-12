package co.edu.uniquindio.pr2.proyectofinal.builder;

import co.edu.uniquindio.pr2.proyectofinal.model.Administrador;

public class AdministradorBuilder {
    private String nombre;
    private String correo;
    private String telefono;
    private String password;
    private String idAdministrador;

    public AdministradorBuilder nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public AdministradorBuilder correo(String correo) {
        this.correo = correo;
        return this;
    }

    public AdministradorBuilder telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public AdministradorBuilder password(String password) {
        this.password = password;
        return this;
    }

    public AdministradorBuilder idAdministrador(String idAdministrador) {
        this.idAdministrador = idAdministrador;
        return this;
    }

    public AdministradorBuilder from(Administrador a) {
        this.idAdministrador = a.getIdAdministrador();
        this.nombre = a.getNombre();
        this.correo = a.getCorreo();
        this.telefono = a.getTelefono();
        this.password = a.getPassword();
        return this;
    }

    public Administrador build() {
        return new Administrador(nombre, correo, telefono, password, idAdministrador);
    }
}