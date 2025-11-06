package co.edu.uniquindio.pr2.proyectofinal.mapping.dto;

import co.edu.uniquindio.pr2.proyectofinal.model.TipoServicio;

public record ServicioAdicionalDTO(
        String idServicioAdd,
        TipoServicio tipoServicio,
        double costoServicioAdd
) {}