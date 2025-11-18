package co.edu.uniquindio.pr2.proyectofinal.model;

import java.util.List;

public class Repartidor extends Persona {
    private String cedula;
    private String idRepartidor;
    private DisponibilidadRepartidor disponibilidadRepartidor;
    private String zonaCobertura;
    private List<Envio> enviosAsignados;

    public Repartidor(String nombre,
                      String correo,
                      String telefono,
                      String password,
                      String cedula,
                      String idRepartidor,
                      String zonaCobertura,
                      DisponibilidadRepartidor disponibilidadRepartidor,
                      List<Envio> enviosAsignados) {

        super(nombre, correo, telefono, password);
        this.cedula = cedula;
        this.idRepartidor = idRepartidor;
        this.zonaCobertura = zonaCobertura;
        this.disponibilidadRepartidor = disponibilidadRepartidor;
        this.enviosAsignados = enviosAsignados;

    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
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