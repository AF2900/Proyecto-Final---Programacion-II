package co.edu.uniquindio.pr2.proyectofinal.builder;

import co.edu.uniquindio.pr2.proyectofinal.model.Direccion;

public class DireccionBuilder {

    private String idDireccion;
    private String alias;
    private String calle;
    private String ciudad;
    private double latitud;
    private double longitud;

    public DireccionBuilder idDireccion(String idDireccion) {
        this.idDireccion = idDireccion;
        return this;
    }

    public DireccionBuilder alias(String alias) {
        this.alias = alias;
        return this;
    }

    public DireccionBuilder calle(String calle) {
        this.calle = calle;
        return this;
    }

    public DireccionBuilder ciudad(String ciudad) {
        this.ciudad = ciudad;
        return this;
    }

    public DireccionBuilder latitud(double latitud) {
        this.latitud = latitud;
        return this;
    }

    public DireccionBuilder longitud(double longitud) {
        this.longitud = longitud;
        return this;
    }

    public Direccion build() {
        return new Direccion(idDireccion, alias, calle, ciudad, latitud, longitud);
    }
}