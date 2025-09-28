package co.edu.uniquindio.pr2.proyectofinal.model;

import java.time.LocalDate;

public class Envio {
    private String idEnvio;
    private String origen;
    private String destino;
    private double pesoEnvio;
    private double costo;
    private LocalDate empaquetado;
    private LocalDate fechaEnvio;
    private EstadoEnvio estado;

    public Envio(String idEnvio,String origen,String destino,double pesoEnvio, double costo,LocalDate empaquetado,LocalDate fechaEnvio,EstadoEnvio estado) {
        this.idEnvio = idEnvio;
        this.origen = origen;
        this.destino = destino;
        this.pesoEnvio = pesoEnvio;
        this.costo = costo;
        this.empaquetado = empaquetado;
        this.fechaEnvio = fechaEnvio;
        this.estado = estado;
    }

    public String getIdEnvio() {return idEnvio;}

    public void setIdEnvio(String idEnvio) {this.idEnvio = idEnvio;}

    public String getOrigen() {return origen;}

    public void setOrigen(String origen) {this.origen = origen;}

    public String getDestino() {return destino;}

    public void setDestino(String destino) {this.destino = destino;}

    public double getPesoEnvio() {return pesoEnvio;}

    public void setPesoEnvio(double pesoEnvio) {this.pesoEnvio = pesoEnvio;}

    public double getCosto() {return costo;}

    public void setCosto(double costo) {this.costo = costo;}

    public LocalDate getEmpaquetado() {return empaquetado;}

    public void setEmpaquetado(LocalDate empaquetado) {this.empaquetado = empaquetado;}

    public LocalDate getFechaEnvio() {return fechaEnvio;}

    public void setFechaEnvio(LocalDate fechaEnvio) {this.fechaEnvio = fechaEnvio;}

    public EstadoEnvio getEstado() {return estado;}

    public void setEstado(EstadoEnvio estado) {this.estado = estado;}

    @Override
    public String toString() {
        return "Envio{" +
                "id del envio='" + idEnvio + '\'' +
                ", origen='" + origen + '\'' +
                ", destino='" + destino + '\'' +
                ", peso del envio=" + pesoEnvio +
                ", costo=" + costo +
                ", empaquetado=" + empaquetado +
                ", fechaEnvio=" + fechaEnvio +
                ", estado=" + estado +
                '}';
    }
}
