package co.edu.uniquindio.pr2.proyectofinal.builder;

import co.edu.uniquindio.pr2.proyectofinal.model.Envio;
import co.edu.uniquindio.pr2.proyectofinal.model.Incidencia;
import java.time.LocalDate;

public class IncidenciaBuilder {

    private String idIncidencia;
    private String descripcion;
    private LocalDate fecha;
    private String estadoIncidencia;
    private Envio envioAsociado;

    public IncidenciaBuilder idIncidencia(String idIncidencia) {
        this.idIncidencia = idIncidencia;
        return this;
    }

    public IncidenciaBuilder descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public IncidenciaBuilder fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public IncidenciaBuilder estadoIncidencia(String estadoIncidencia) {
        this.estadoIncidencia = estadoIncidencia;
        return this;
    }

    public IncidenciaBuilder envioAsociado(Envio envioAsociado) {
        this.envioAsociado = envioAsociado;
        return this;
    }

    public Incidencia build() {
        Incidencia incidencia = new Incidencia(idIncidencia, descripcion, fecha, estadoIncidencia);
        incidencia.setEnvioAsociado(envioAsociado);
        return incidencia;
    }
}