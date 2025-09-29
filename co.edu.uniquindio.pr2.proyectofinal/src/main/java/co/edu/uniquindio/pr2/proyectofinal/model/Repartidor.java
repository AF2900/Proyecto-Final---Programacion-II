package co.edu.uniquindio.pr2.proyectofinal.model;

import java.util.ArrayList;
import java.util.List;

public class Repartidor extends Persona{
    private String idRepartidor;
    private DisponibilidadRepartidor disponibilidadRepartidor;
    private String zonaCobertura;
    private List<Envio> enviosAsignados;

    public Repartidor(String nombre, String correo, String telefono, String idRepartidor, DisponibilidadRepartidor disponibilidadRepartidor
    , String zonaCobertura) {
        super(nombre, correo, telefono);
        this.idRepartidor = idRepartidor;
        this.disponibilidadRepartidor = disponibilidadRepartidor;
        this.zonaCobertura = zonaCobertura;
        this.enviosAsignados = new ArrayList<>();
    }

    public DisponibilidadRepartidor getDisponibilidadRepartidor() {return disponibilidadRepartidor;}
    public void setDisponibilidadRepartidor(DisponibilidadRepartidor disponibilidadRepartidor) {this.disponibilidadRepartidor = disponibilidadRepartidor;}

    public List<Envio> getEnviosAsignados() {return enviosAsignados;}

    public String getZonaCobertura() {return zonaCobertura;}
    public void setZonaCobertura(String zonaCobertura) {this.zonaCobertura = zonaCobertura;}

    public DisponibilidadRepartidor getDisponibilidaRepartidor() {return disponibilidadRepartidor;}
    public void setDisponibilidaRepartidor(DisponibilidadRepartidor disponibilidadRepartidor) {this.disponibilidadRepartidor = disponibilidadRepartidor;}

    public String getIdRepartidor() {
        return idRepartidor;
    }
    public void setIdRepartidor(String idRepartidor) {
        this.idRepartidor = idRepartidor;
    }

    public void asignarEnvio(Envio envio){
        if (envio != null) {
            enviosAsignados.add(envio);
            envio.setRepartidor(this);
        }
    }

    public void eliminarEnvio(Envio envio){
        enviosAsignados.remove(envio);
    }

    public int getNumeroEnviosAsignados(){
        return enviosAsignados.size();
    }

    @Override
    public String toString() {
        return "Repartidor{" +
                "idRepartidor='" + idRepartidor + '\'' +
                ", nombre='" + nombre + '\'' +
                ", disponibilidad=" + disponibilidadRepartidor +
                ", zonaCobertura='" + zonaCobertura + '\'' +
                ", enviosAsignados=" + enviosAsignados.size() +
                '}';
    }
}