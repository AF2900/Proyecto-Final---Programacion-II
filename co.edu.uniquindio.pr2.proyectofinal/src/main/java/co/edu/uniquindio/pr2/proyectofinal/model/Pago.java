package co.edu.uniquindio.pr2.proyectofinal.model;

import java.time.LocalDate;

public class Pago{
    private String idPago;
    private double monto;
    private LocalDate fecha;
    private MetodoPago metodoPago;
    private String resultado;

    public Pago(String idPago, double monto, LocalDate fecha, MetodoPago metodoPago, String resultado) {
        this.idPago = idPago;
        this.monto = monto;
        this.fecha = fecha;
        this.metodoPago = metodoPago;
        this.resultado = resultado;

    }

    public double getMonto() {return monto;}
    public void setMonto(double monto) {this.monto = monto;}

    public String getResultado() {return resultado;}
    public void setResultado(String resultado) {this.resultado = resultado;}

    public String getIdPago() {return idPago;}
    public void setIdPago(String idPago) {this.idPago = idPago;}


    public LocalDate getFecha() {return fecha;}
    public void setFecha(LocalDate fecha) {this.fecha = fecha;}

    public MetodoPago getMetodoPago() {return metodoPago;}
    public void setMetodoPago(MetodoPago metodoPago) {this.metodoPago = metodoPago;}

    public boolean esPagoAprobado(){
        return "APROBADO".equalsIgnoreCase(resultado);
    }

    public void validarPago(){
        if(monto>0){
            this.resultado = "APROBADO";
        }else {
            this.resultado = "RECHAZADO";
        }
    }

    @Override
    public String toString() {
        return "Pago{" +
                "idPago='" + idPago + '\'' +
                ", monto=" + monto +
                ", fecha=" + fecha +
                ", metodoPago=" + metodoPago +
                ", resultado='" + resultado + '\'' +
                '}';
    }
}
