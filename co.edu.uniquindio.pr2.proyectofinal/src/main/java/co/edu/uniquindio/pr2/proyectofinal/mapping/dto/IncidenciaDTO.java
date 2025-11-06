package co.edu.uniquindio.pr2.proyectofinal.mapping.dto;

import co.edu.uniquindio.pr2.proyectofinal.model.EstadoIncidencia;
import java.time.LocalDate;

public record IncidenciaDTO(
        String idIncidencia,
        String descripcion,
        LocalDate fecha,
        EstadoIncidencia estadoIncidencia
) {}