package co.edu.uniquindio.pr2.proyectofinal.model;

public class Persona {
    private String nombre;
    private String correo;
    private String telefono;

    public Persona(String nombre) {
        this.nombre = nombre;
    }
    public Persona(String correo)  {
        this.correo = correo;
    }
    public Persona(String telefono)  {
        this.telefono = telefono;

    }


    public Persona(String nombre, String correo, String telefono) {
    }
}
