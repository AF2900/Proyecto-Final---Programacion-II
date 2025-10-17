package co.edu.uniquindio.pr2.proyectofinal.model;

public class Persona {
    protected String nombre;
    protected String correo;
    protected String telefono;
    protected String password;

    public Persona(String nombre, String correo, String telefono, String password) {
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.password = password;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}