package co.edu.uniquindio.pr2.proyectofinal.model;

public class Administrador extends Persona {
    private String idAdministrador;

    public Administrador(String nombre, String correo, String telefono, String idAdministrador) {
        super(nombre, correo, telefono);
        this.idAdministrador = idAdministrador;
    }

    public String getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(String idAdministrador) {
        this.idAdministrador = idAdministrador;
    }
}