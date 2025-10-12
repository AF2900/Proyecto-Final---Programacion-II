package co.edu.uniquindio.pr2.proyectofinal.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario extends Persona {

    private String idUsuario;
    private List<Direccion> direcciones;

    public Usuario(String idUsuario, String password, String nombre, String correo, String telefono) {
        super(nombre, correo, telefono, password);
        this.idUsuario = idUsuario;
        this.direcciones = new ArrayList<>();
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public List<Direccion> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(List<Direccion> direcciones) {
        this.direcciones = direcciones;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario='" + idUsuario + '\'' +
                ", direcciones=" + direcciones +
                ", nombre='" + nombre + '\'' +
                ", password='" + password + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}