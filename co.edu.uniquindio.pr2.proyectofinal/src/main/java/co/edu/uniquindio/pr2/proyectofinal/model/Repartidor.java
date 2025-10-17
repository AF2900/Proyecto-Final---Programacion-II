package co.edu.uniquindio.pr2.proyectofinal.model;

import java.util.ArrayList;
import java.util.List;

public class Repartidor extends Persona {
    private String idRepartidor;
    private DisponibilidadRepartidor disponibilidadRepartidor;
    private String zonaCobertura;
    private List<Envio> enviosAsignados;

    public Repartidor(String nombre,
                      String cedula,
                      String telefono,
                      String idRepartidor,
                      String password,
                      DisponibilidadRepartidor disponibilidadRepartidor,
                      String zonaCobertura) {
        super(nombre, cedula, telefono, password);
        this.idRepartidor = idRepartidor;
        this.disponibilidadRepartidor = disponibilidadRepartidor;
        this.zonaCobertura = zonaCobertura;
        this.enviosAsignados = new ArrayList<>();
    }

    public DisponibilidadRepartidor getDisponibilidadRepartidor() {
        return disponibilidadRepartidor;
    }

    public void setDisponibilidadRepartidor(DisponibilidadRepartidor disponibilidadRepartidor) {
        this.disponibilidadRepartidor = disponibilidadRepartidor;
    }

    public List<Envio> getEnviosAsignados() {
        return enviosAsignados;
    }

    public String getZonaCobertura() {
        return zonaCobertura;
    }

    public void setZonaCobertura(String zonaCobertura) {
        this.zonaCobertura = zonaCobertura;
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
                ", disponibilidadRepartidor=" + disponibilidadRepartidor +
                ", zonaCobertura='" + zonaCobertura + '\'' +
                ", enviosAsignados=" + enviosAsignados +
                '}';
    }
}
