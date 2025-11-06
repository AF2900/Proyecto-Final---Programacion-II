package co.edu.uniquindio.pr2.proyectofinal.mapping.dto;

import co.edu.uniquindio.pr2.proyectofinal.model.MetodoPago;
import java.time.LocalDate;

public record PagoDTO(
        String idPago,
        double monto,
        LocalDate fecha,
        MetodoPago metodoPago,
        String resultado
) {}