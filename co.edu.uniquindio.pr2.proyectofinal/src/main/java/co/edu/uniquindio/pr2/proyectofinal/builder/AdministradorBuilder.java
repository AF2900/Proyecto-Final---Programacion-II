package co.edu.uniquindio.pr2.proyectofinal.builder;

import co.edu.uniquindio.pr2.proyectofinal.model.Administrador;

public class AdministradorBuilder {
    private String nombre;
    private String correo;
    private String idAdministrador;
    private String telefono;

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

    public AdministradorBuilder idAdministrador(String idAdministrador) {
        this.idAdministrador = idAdministrador;
        return this;
    }

    public Administrador build() {
        return new Administrador(nombre, correo, telefono, idAdministrador);
    }
}