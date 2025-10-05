package co.edu.uniquindio.pr2.proyectofinal.model;

import java.time.LocalDate;

public class Incidencia {

    private String idIncidencia;
    private String descripcion;
    private LocalDate fecha;
    private String estadoIncidencia;
    private Envio envioAsociado;

    public Incidencia(String idIncidencia, String descripcion, LocalDate fecha, String estadoIncidencia) {
        this.idIncidencia = idIncidencia;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.estadoIncidencia = estadoIncidencia;
    }

    public String getIdIncidencia() {
        return idIncidencia;
    }

    public void setIdIncidencia(String idIncidencia) {
        this.idIncidencia = idIncidencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getEstadoIncidencia() {
        return estadoIncidencia;
    }

    public void setEstadoIncidencia(String estadoIncidencia) {
        this.estadoIncidencia = estadoIncidencia;
    }

    public Envio getEnvioAsociado() {
        return envioAsociado;
    }

    public void setEnvioAsociado(Envio envioAsociado) {
        this.envioAsociado = envioAsociado;
    }

    @Override
    public String toString() {
        return "Incidencia{" +
                "idIncidencia='" + idIncidencia + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fecha=" + fecha +
                ", estadoIncidencia='" + estadoIncidencia + '\'' +
                ", envioAsociado=" + (envioAsociado != null ? envioAsociado.getIdEnvio() : "N/A") +
                '}';
    }
}