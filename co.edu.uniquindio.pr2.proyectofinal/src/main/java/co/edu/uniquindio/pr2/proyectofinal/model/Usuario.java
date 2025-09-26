package co.edu.uniquindio.pr2.proyectofinal.model;

public class Usuario extends Persona {
    private String idUsuario;

    public Usuario(String nombre, String correo, String telefono, String idUsuario) {
        super(nombre, correo, telefono);
        this.idUsuario = idUsuario;
    }
}