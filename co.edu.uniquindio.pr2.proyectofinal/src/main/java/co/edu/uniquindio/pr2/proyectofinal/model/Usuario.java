package co.edu.uniquindio.pr2.proyectofinal.model;

public class Usuario extends Persona {
    private String idUsuario;
    private String cedula;

    public Usuario(String idUsuario, String nombre, String correo, String telefono, String cedula) {
        super(nombre, correo, telefono);
        this.idUsuario = idUsuario;
        this.cedula = cedula;
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

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario='" + idUsuario + '\'' +
                ", cedula='" + cedula + '\'' +
                ", telefono='" + telefono + '\'' +
                ", correo='" + correo + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}