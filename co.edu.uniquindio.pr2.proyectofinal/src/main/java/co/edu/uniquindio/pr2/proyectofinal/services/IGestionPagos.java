package co.edu.uniquindio.pr2.proyectofinal.services;

import co.edu.uniquindio.pr2.proyectofinal.model.Pago;

public interface IGestionPagos {
    boolean esPagoAprobado(Pago pago);
    void validarPago(Pago pago);
}