package co.edu.uniquindio.pr2.proyectofinal.model;

import java.time.LocalDate;

public class Pago{
    private String idPago;
    private double deposito;
    private LocalDate fecha;
    private MetodoPago metodoPago;
    private String resultadoPago;

    public Pago(String idPago, double deposito, LocalDate fecha, MetodoPago metodoPago, String resultadoPago) {
        this.idPago = idPago;
        this.deposito = deposito;
        this.fecha = fecha;
        this.metodoPago = metodoPago;
        this.resultadoPago = resultadoPago;

    }

    public String getIdPago() {return idPago;}

    public void setIdPago(String idPago) {this.idPago = idPago;}

    public double getDeposito() {return deposito;}

    public void setDeposito(double deposito) {this.deposito = deposito;}

    public LocalDate getFecha() {return fecha;}

    public void setFecha(LocalDate fecha) {this.fecha = fecha;}

    public MetodoPago getMetodoPago() {return metodoPago;}

    public void setMetodoPago(MetodoPago metodoPago) {this.metodoPago = metodoPago;}

    public String getResultadoPago() {return resultadoPago;}

    public void setResultadoPago(String resultadoPago) {this.resultadoPago = resultadoPago;}

    @Override
    public String toString() {
        return "id del pago=" + idPago + '\''+
                "Deposito =" + deposito + '\''+
                "Fecha del envio="+ fecha +'\''+
                "Seleccione metodo de pago"+ metodoPago + '\''+
                "Resultado"+ resultadoPago;
    }
}
