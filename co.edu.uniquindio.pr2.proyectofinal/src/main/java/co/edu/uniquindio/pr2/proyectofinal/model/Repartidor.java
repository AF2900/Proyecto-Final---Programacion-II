package co.edu.uniquindio.pr2.proyectofinal.model;

public class Repartidor extends Persona{
    private String idRepartidor;

    public Repartidor(String nombre, String correo, String telefono, String idRepartidor) {
        super(nombre, correo, telefono);
        this.idRepartidor = idRepartidor;
    }

    public String getIdRepartidor() {
        return idRepartidor;
    }

    public void setIdRepartidor(String idRepartidor) {
        this.idRepartidor = idRepartidor;
    }

    @Override
    public String toString() {
        return "Repartidor{" +
                "idRepartidor='" + idRepartidor + '\'' +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}