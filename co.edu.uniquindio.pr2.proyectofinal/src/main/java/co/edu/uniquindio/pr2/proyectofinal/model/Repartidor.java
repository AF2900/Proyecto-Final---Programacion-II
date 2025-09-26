package co.edu.uniquindio.pr2.proyectofinal.model;

public class Repartidor extends Persona{
    private String idRepartidor;
    private String nombreRepartidor;

    public Repartidor(String nombre, String correo, String telefono, String idRepartidor, String identificacion) {
        super(nombre, correo, telefono);
        this.idRepartidor = idRepartidor;
        this.nombreRepartidor = nombre;
    }
}