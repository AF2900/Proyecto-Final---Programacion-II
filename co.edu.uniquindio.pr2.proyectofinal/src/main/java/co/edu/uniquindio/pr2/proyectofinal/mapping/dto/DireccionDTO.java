package co.edu.uniquindio.pr2.proyectofinal.mapping.dto;

public record DireccionDTO(
        String idDireccion,
        String alias,
        String calle,
        String ciudad,
        double latitud,
        double longitud
) {}