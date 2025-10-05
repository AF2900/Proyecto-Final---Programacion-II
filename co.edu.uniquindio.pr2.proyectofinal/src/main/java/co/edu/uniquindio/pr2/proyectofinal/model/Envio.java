package co.edu.uniquindio.pr2.proyectofinal.model;

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
        this.fechaCreacion = fechaCreacion;
        this.fechaEstimadaEntrega = fechaEstimadaEntrega;
        this.estado = estado;
        this.listaServiciosAdicionales = new ArrayList<>();
        this.listaIncidencias = new ArrayList<>();
    }

    public String getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(String idEnvio) {
        this.idEnvio = idEnvio;
    }

    public Direccion getOrigen() {
        return origen;
    }

    public void setOrigen(Direccion origen) {
        this.origen = origen;
    }

    public Direccion getDestino() {
        return destino;
    }

    public void setDestino(Direccion destino) {
        this.destino = destino;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getLargo() {
        return largo;
    }

    public void setLargo(double largo) {
        this.largo = largo;
    }

    public double getAncho() {
        return ancho;
    }

    public void setAncho(double ancho) {
        this.ancho = ancho;
    }

    public double getAlto() {
        return alto;
    }

    public void setAlto(double alto) {
        this.alto = alto;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaEstimadaEntrega() {
        return fechaEstimadaEntrega;
    }

    public void setFechaEstimadaEntrega(LocalDate fechaEstimadaEntrega) {
        this.fechaEstimadaEntrega = fechaEstimadaEntrega;
    }

    public EstadoEnvio getEstado() {
        return estado;
    }

    public void setEstado(EstadoEnvio estado) {
        this.estado = estado;
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