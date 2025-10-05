package co.edu.uniquindio.pr2.proyectofinal.builder;

import co.edu.uniquindio.pr2.proyectofinal.model.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EnvioBuilder {
    private String idEnvio;
    private Direccion origen;
    private Direccion destino;
    private double peso;
    private double largo;
    private double ancho;
    private double alto;
    private double costo;
    private LocalDate fechaCreacion;
    private LocalDate fechaEstimadaEntrega;
    private EstadoEnvio estado;
    private Repartidor repartidor;
    private Usuario usuario;
    private List<ServicioAdicional> listaServiciosAdicionales = new ArrayList<>();
    private List<Incidencia> listaIncidencias = new ArrayList<>();

    public EnvioBuilder idEnvio(String idEnvio) {
        this.idEnvio = idEnvio;
        return this;
    }

    public EnvioBuilder origen(Direccion origen) {
        this.origen = origen;
        return this;
    }

    public EnvioBuilder destino(Direccion destino) {
        this.destino = destino;
        return this;
    }

    public EnvioBuilder peso(double peso) {
        this.peso = peso;
        return this;
    }

    public EnvioBuilder largo(double largo) {
        this.largo = largo;
        return this;
    }

    public EnvioBuilder ancho(double ancho) {
        this.ancho = ancho;
        return this;
    }

    public EnvioBuilder alto(double alto) {
        this.alto = alto;
        return this;
    }

    public EnvioBuilder costo(double costo) {
        this.costo = costo;
        return this;
    }

    public EnvioBuilder fechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public EnvioBuilder fechaEstimadaEntrega(LocalDate fechaEstimadaEntrega) {
        this.fechaEstimadaEntrega = fechaEstimadaEntrega;
        return this;
    }

    public EnvioBuilder estado(EstadoEnvio estado) {
        this.estado = estado;
        return this;
    }

    public EnvioBuilder repartidor(Repartidor repartidor) {
        this.repartidor = repartidor;
        return this;
    }

    public EnvioBuilder usuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public EnvioBuilder listaServiciosAdicionales(List<ServicioAdicional> listaServiciosAdicionales) {
        this.listaServiciosAdicionales = listaServiciosAdicionales;
        return this;
    }

    public EnvioBuilder listaIncidencias(List<Incidencia> listaIncidencias) {
        this.listaIncidencias = listaIncidencias;
        return this;
    }

    public Envio build() {
        Envio envio = new Envio(
                idEnvio,
                origen,
                destino,
                peso,
                largo,
                ancho,
                alto,
                fechaCreacion,
                fechaEstimadaEntrega,
                estado
        );
        envio.setCosto(costo);
        envio.setRepartidor(repartidor);
        envio.setUsuario(usuario);
        envio.setListaServiciosAdicionales(listaServiciosAdicionales);
        envio.setListaIncidencias(listaIncidencias);
        return envio;
    }
}