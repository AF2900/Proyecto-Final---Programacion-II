package co.edu.uniquindio.pr2.proyectofinal.model;

public class Repartidor extends Persona{
    private String idRepartidor;
    private DisponibilidadRepartidor disponibilidadRepartidor;

    public Repartidor(String nombre, String correo, String telefono, String idRepartidor, DisponibilidadRepartidor disponibilidadRepartidor) {
        super(nombre, correo, telefono);
        this.idRepartidor = idRepartidor;
        this.disponibilidadRepartidor = disponibilidadRepartidor;
    }

    public DisponibilidadRepartidor getDisponibilidaRepartidor() {return disponibilidadRepartidor;}

    public void setDisponibilidaRepartidor(DisponibilidadRepartidor disponibilidadRepartidor) {this.disponibilidadRepartidor = disponibilidadRepartidor;}

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
                "Estado del repartidor='" + disponibilidadRepartidor + '\'' +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}