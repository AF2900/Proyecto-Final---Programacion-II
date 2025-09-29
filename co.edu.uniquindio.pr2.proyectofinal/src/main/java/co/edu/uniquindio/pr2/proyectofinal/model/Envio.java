package co.edu.uniquindio.pr2.proyectofinal.model;

import java.time.LocalDate;

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

    public Envio(String idEnvio, Direccion origen, Direccion destino, double peso, double largo, double ancho, double alto, LocalDate fechaCreacion, LocalDate fechaEstimadaEntrega,
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
        this.costo = 0.0;
    }

    public Direccion getOrigen() {return origen;}

    public void setOrigen(Direccion origen) {this.origen = origen;}

    public Direccion getDestino() {return destino;}

    public void setDestino(Direccion destino) {this.destino = destino;}

    public double getLargo() {return largo;}

    public void setLargo(double largo) {this.largo = largo;}

    public double getPeso() {return peso;}

    public void setPeso(double peso) {this.peso = peso;}

    public double getAlto() {return alto;}

    public void setAlto(double alto) {this.alto = alto;}

    public double getAncho() {return ancho;}

    public void setAncho(double ancho) {this.ancho = ancho;}

    public LocalDate getFechaCreacion() {return fechaCreacion;}

    public void setFechaCreacion(LocalDate fechaCreacion) {this.fechaCreacion = fechaCreacion;}

    public LocalDate getFechaEstimadaEntrega() {return fechaEstimadaEntrega;}

    public void setFechaEstimadaEntrega(LocalDate fechaEstimadaEntrega) {this.fechaEstimadaEntrega = fechaEstimadaEntrega;}

    public Repartidor getRepartidor() {return repartidor;}

    public void setRepartidor(Repartidor repartidor) {this.repartidor = repartidor;}

    public Usuario getUsuario() {return usuario;}

    public void setUsuario(Usuario usuario) {this.usuario = usuario;}

    public String getIdEnvio() {return idEnvio;}

    public void setIdEnvio(String idEnvio) {this.idEnvio = idEnvio;}

    public double getPesoEnvio() {return peso;}

    public void setPesoEnvio(double pesoEnvio) {this.peso = peso;}

    public double getCosto() {return costo;}

    public void setCosto(double costo) {this.costo = costo;}

    public EstadoEnvio getEstado() {return estado;}

    public void setEstado(EstadoEnvio estado) {this.estado = estado;}

    public double calcularVolumen(){
        return largo*ancho*alto;
    }

    public double calcularCosto(){
        double base = 500;
        double costoPeso = peso * 1500;
        double costoVolumen = calcularVolumen() * 3000;
        double total =  base + costoPeso + costoVolumen;
        this.costo = Math.round(total * 100) / 100.0;
        return this.costo;
    }

    @Override
    public String toString() {
        return "Envio{" +
                "idEnvio='" + idEnvio + '\'' +
                ", origen=" + origen +
                ", destino=" + destino +
                ", peso=" + peso +
                ", volumen=" + calcularVolumen() +
                ", costo=" + costo +
                ", estado=" + estado +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaEstimadaEntrega=" + fechaEstimadaEntrega +
                '}';
    }

}
