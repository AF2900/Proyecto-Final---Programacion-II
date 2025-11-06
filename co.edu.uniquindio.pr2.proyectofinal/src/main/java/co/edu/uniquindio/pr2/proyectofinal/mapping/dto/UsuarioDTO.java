package co.edu.uniquindio.pr2.proyectofinal.mapping.dto;

import java.util.List;

public record UsuarioDTO(
        String idUsuario,
        String nombre,
        String correo,
        String telefono,
        List<DireccionDTO> direcciones
) {}