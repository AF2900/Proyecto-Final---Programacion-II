package co.edu.uniquindio.pr2.proyectofinal.model;

public class ServicioAdicional {

    private String idServicioAdd;
    private TipoServicio tipoServicio;
    private double costoServicioAdd;
    private Envio envioAsociado;

    public ServicioAdicional(String idServicioAdd, TipoServicio tipoServicio, double costoServicioAdd) {
        this.idServicioAdd = idServicioAdd;
        this.tipoServicio = tipoServicio;
        this.costoServicioAdd = costoServicioAdd;
    }

    public String getIdServicioAdd() {
        return idServicioAdd;
    }

    public void setIdServicioAdd(String idServicioAdd) {
        this.idServicioAdd = idServicioAdd;
    }

    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(TipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public double getCostoServicioAdd() {
        return costoServicioAdd;
    }

    public void setCostoServicioAdd(double costoServicioAdd) {
        this.costoServicioAdd = costoServicioAdd;
    }

    public Envio getEnvioAsociado() {
        return envioAsociado;
    }

    public void setEnvioAsociado(Envio envioAsociado) {
        this.envioAsociado = envioAsociado;
    }

    @Override
    public String toString() {
        return "ServicioAdicional{" +
                "idServicioAdd='" + idServicioAdd + '\'' +
                ", tipoServicio=" + tipoServicio +
                ", costoServicioAdd=" + costoServicioAdd +
                ", envioAsociado=" + (envioAsociado != null ? envioAsociado.getIdEnvio() : "N/A") +
                '}';
    }
}
