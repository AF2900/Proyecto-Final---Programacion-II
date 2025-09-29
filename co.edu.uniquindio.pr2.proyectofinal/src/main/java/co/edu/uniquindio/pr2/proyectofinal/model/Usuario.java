package co.edu.uniquindio.pr2.proyectofinal.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario extends Persona {
    private String idUsuario;
    private String cedula;
    private List<Direccion> direcciones;


    public Usuario(String idUsuario, String nombre, String correo, String telefono) {
        super(nombre, correo, telefono);
        this.idUsuario = idUsuario;
        this.cedula = "";
        this.direcciones = new ArrayList<>();
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public List<Direccion> getDirecciones() {return direcciones;}

    public void addDireccion(Direccion d) {if (d != null) direcciones.add(d);}
    public void removeDireccion(Direccion d) {direcciones.remove(d);}

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario='" + idUsuario + '\'' +
                ", cedula='" + cedula + '\'' +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}