package co.edu.uniquindio.pr2.proyectofinal.builder;

import co.edu.uniquindio.pr2.proyectofinal.model.Envio;
import co.edu.uniquindio.pr2.proyectofinal.model.ServicioAdicional;

public class ServicioAdicionalBuilder {

    private String idServicioAdd;
    private String tipoServicio;
    private double costoServicioAdd;
    private Envio envioAsociado;

    public ServicioAdicionalBuilder idServicioAdd(String idServicioAdd) {
        this.idServicioAdd = idServicioAdd;
        return this;
    }

    public ServicioAdicionalBuilder tipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
        return this;
    }

    public ServicioAdicionalBuilder costoServicioAdd(double costoServicioAdd) {
        this.costoServicioAdd = costoServicioAdd;
        return this;
    }

    public ServicioAdicionalBuilder envioAsociado(Envio envioAsociado) {
        this.envioAsociado = envioAsociado;
        return this;
    }

    public ServicioAdicional build() {
        ServicioAdicional servicio = new ServicioAdicional(idServicioAdd, tipoServicio, costoServicioAdd);
        servicio.setEnvioAsociado(envioAsociado);
        return servicio;
    }
}