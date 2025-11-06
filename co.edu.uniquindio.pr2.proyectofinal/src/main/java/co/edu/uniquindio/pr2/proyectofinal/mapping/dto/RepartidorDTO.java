package co.edu.uniquindio.pr2.proyectofinal.mapping.dto;

import co.edu.uniquindio.pr2.proyectofinal.model.DisponibilidadRepartidor;
import java.util.List;

public record RepartidorDTO(
        String cedula,
        String idRepartidor,
        String nombre,
        String correo,
        String telefono,
        DisponibilidadRepartidor disponibilidadRepartidor,
        String zonaCobertura,
        List<String> idsEnviosAsignados
) {}