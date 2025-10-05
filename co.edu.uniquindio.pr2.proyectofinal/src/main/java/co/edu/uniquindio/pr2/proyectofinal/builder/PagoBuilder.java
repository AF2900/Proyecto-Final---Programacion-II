package co.edu.uniquindio.pr2.proyectofinal.builder;

import co.edu.uniquindio.pr2.proyectofinal.model.MetodoPago;
import co.edu.uniquindio.pr2.proyectofinal.model.Pago;
import java.time.LocalDate;

public class PagoBuilder {

    private String idPago;
    private double monto;
    private LocalDate fecha;
    private MetodoPago metodoPago;
    private String resultado;

    public PagoBuilder idPago(String idPago) {
        this.idPago = idPago;
        return this;
    }

    public PagoBuilder monto(double monto) {
        this.monto = monto;
        return this;
    }

    public PagoBuilder fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public PagoBuilder metodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
        return this;
    }

    public PagoBuilder resultado(String resultado) {
        this.resultado = resultado;
        return this;
    }

    public Pago build() {
        return new Pago(idPago, monto, fecha, metodoPago, resultado);
    }
}