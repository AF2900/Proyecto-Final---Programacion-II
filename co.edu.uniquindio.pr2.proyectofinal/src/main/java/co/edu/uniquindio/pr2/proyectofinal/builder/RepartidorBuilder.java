package co.edu.uniquindio.pr2.proyectofinal.builder;

import co.edu.uniquindio.pr2.proyectofinal.model.DisponibilidadRepartidor;
import co.edu.uniquindio.pr2.proyectofinal.model.Envio;
import co.edu.uniquindio.pr2.proyectofinal.model.Repartidor;

import java.util.ArrayList;
import java.util.List;

public class RepartidorBuilder {
    private String nombre;
    private String correo;
    private String telefono;
    private String idRepartidor;
    private DisponibilidadRepartidor disponibilidadRepartidor;
    private String zonaCobertura;
    private List<Envio> enviosAsignados = new ArrayList<>();

    public RepartidorBuilder nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public RepartidorBuilder correo(String correo) {
        this.correo = correo;
        return this;
    }

    public RepartidorBuilder telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public RepartidorBuilder idRepartidor(String idRepartidor) {
        this.idRepartidor = idRepartidor;
        return this;
    }

    public RepartidorBuilder disponibilidadRepartidor(DisponibilidadRepartidor disponibilidadRepartidor) {
        this.disponibilidadRepartidor = disponibilidadRepartidor;
        return this;
    }

    public RepartidorBuilder zonaCobertura(String zonaCobertura) {
        this.zonaCobertura = zonaCobertura;
        return this;
    }

    public RepartidorBuilder enviosAsignados(List<Envio> enviosAsignados) {
        this.enviosAsignados = enviosAsignados;
        return this;
    }

    public Repartidor build() {
        Repartidor r = new Repartidor(
                nombre,
                correo,
                telefono,
                idRepartidor,
                disponibilidadRepartidor,
                zonaCobertura
        );
        if (enviosAsignados != null && !enviosAsignados.isEmpty()) {
            r.getEnviosAsignados().addAll(enviosAsignados);
        }
        return r;
    }
}