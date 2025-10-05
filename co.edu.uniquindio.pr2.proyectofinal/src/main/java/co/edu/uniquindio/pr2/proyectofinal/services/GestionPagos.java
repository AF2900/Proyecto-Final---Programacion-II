package co.edu.uniquindio.pr2.proyectofinal.services;

import co.edu.uniquindio.pr2.proyectofinal.model.Pago;

public interface GestionPagos {
    boolean esPagoAprobado(Pago pago);
    void validarPago(Pago pago);
}