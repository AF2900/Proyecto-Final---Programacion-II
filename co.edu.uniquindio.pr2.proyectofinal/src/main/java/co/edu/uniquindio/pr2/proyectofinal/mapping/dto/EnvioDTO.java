package co.edu.uniquindio.pr2.proyectofinal.mapping.dto;

import co.edu.uniquindio.pr2.proyectofinal.model.EstadoEnvio;
import java.time.LocalDate;
import java.util.List;

public record EnvioDTO(
        String idEnvio,
        DireccionDTO origen,
        DireccionDTO destino,
        double peso,
        double largo,
        double ancho,
        double alto,
        double costo,
        LocalDate fechaCreacion,
        LocalDate fechaEstimadaEntrega,
        EstadoEnvio estado,
        String descripcionRepartidor,
        String idRepartidor,
        String idUsuario,
        List<ServicioAdicionalDTO> listaServiciosAdicionales,
        List<IncidenciaDTO> listaIncidencias
) {}