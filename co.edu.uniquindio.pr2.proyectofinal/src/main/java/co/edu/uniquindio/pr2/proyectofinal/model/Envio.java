package co.edu.uniquindio.pr2.proyectofinal.model;

import co.edu.uniquindio.pr2.proyectofinal.observer.ObserverEnvio;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Envio {
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
    private List<ServicioAdicional> listaServiciosAdicionales;
    private List<Incidencia> listaIncidencias;

    private final List<ObserverEnvio> observers = new ArrayList<>();

    public Envio(String idEnvio,
                 Direccion origen,
                 Direccion destino,
                 double peso,
                 double largo,
                 double ancho,
                 double alto,
                 LocalDate fechaCreacion,
                 LocalDate fechaEstimadaEntrega,
                 EstadoEnvio estado) {

        this.idEnvio = idEnvio;
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
        this.largo = largo;
        this.ancho = ancho;
        this.alto = alto;
        this.costo = 0;
        this.fechaCreacion = fechaCreacion;
        this.fechaEstimadaEntrega = fechaEstimadaEntrega;
        this.estado = estado;
        this.listaServiciosAdicionales = new ArrayList<>();
        this.listaIncidencias = new ArrayList<>();
    }

    public EstadoEnvio getEstado() {
        return estado;
    }

    public void setEstado(EstadoEnvio estado) {
        this.estado = estado;
        notificarObservers();
    }

    public void agregarObserver(ObserverEnvio observer) {
        observers.add(observer);
    }

    public void eliminarObserver(ObserverEnvio observer) {
        observers.remove(observer);
    }

    private void notificarObservers() {
        for (ObserverEnvio observer : observers) {
            observer.actualizar(this, this.estado);
        }
    }

    public Repartidor getRepartidor() {
        return repartidor;
    }

    public void setRepartidor(Repartidor repartidor) {
        this.repartidor = repartidor;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ServicioAdicional> getListaServiciosAdicionales() {
        return listaServiciosAdicionales;
    }

    public void setListaServiciosAdicionales(List<ServicioAdicional> listaServiciosAdicionales) {
        this.listaServiciosAdicionales = listaServiciosAdicionales;
    }

    public List<Incidencia> getListaIncidencias() {
        return listaIncidencias;
    }

    public void setListaIncidencias(List<Incidencia> listaIncidencias) {
        this.listaIncidencias = listaIncidencias;
    }

    public String getIdEnvio() {
        return idEnvio;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public Direccion getOrigen() {
        return origen;
    }

    public Direccion getDestino() {
        return destino;
    }

    public double getPeso() {
        return peso;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDate getFechaEstimadaEntrega() {
        return fechaEstimadaEntrega;
    }

    public double getLargo() {
        return largo;
    }

    public double getAncho() {
        return ancho;
    }

    public double getCosto() {
        return costo;
    }

    public double getAlto() {
        return alto;
    }

    @Override
    public String toString() {
        return "Envio{" +
                "idEnvio='" + idEnvio + '\'' +
                ", origen=" + origen +
                ", destino=" + destino +
                ", peso=" + peso +
                ", largo=" + largo +
                ", ancho=" + ancho +
                ", alto=" + alto +
                ", costo=" + costo +
                ", estado=" + estado +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaEstimadaEntrega=" + fechaEstimadaEntrega +
                ", repartidor=" + (repartidor != null ? repartidor.getNombre() : "N/A") +
                ", usuario=" + (usuario != null ? usuario.getNombre() : "N/A") +
                '}';
    }
}