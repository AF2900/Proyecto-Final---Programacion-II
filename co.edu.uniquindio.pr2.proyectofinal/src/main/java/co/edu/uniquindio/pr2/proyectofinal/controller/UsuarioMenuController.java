package co.edu.uniquindio.pr2.proyectofinal.controller;

import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import co.edu.uniquindio.pr2.proyectofinal.model.Envio;
import co.edu.uniquindio.pr2.proyectofinal.model.EstadoEnvio;
import co.edu.uniquindio.pr2.proyectofinal.model.Usuario;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioMenuController {

    private final ModelFactory modelFactory = ModelFactory.getInstance();

    public List<Envio> obtenerEnviosUsuario(Usuario usuario) {
        return modelFactory.getEmpresaLogistica().getEnvios().stream()
                .filter(e -> e.getUsuario() != null && e.getUsuario().equals(usuario))
                .collect(Collectors.toList());
    }

    public long contarTotalEnvios(Usuario usuario) {
        return obtenerEnviosUsuario(usuario).size();
    }

    public long contarEnviosActivos(Usuario usuario) {
        return obtenerEnviosUsuario(usuario).stream()
                .filter(e -> e.getEstado() == EstadoEnvio.PENDIENTE || e.getEstado() == EstadoEnvio.EN_CAMINO)
                .count();
    }

    public long contarEnviosEntregados(Usuario usuario) {
        return obtenerEnviosUsuario(usuario).stream()
                .filter(e -> e.getEstado() == EstadoEnvio.ENTREGADO)
                .count();
    }

    public Envio buscarEnvioPorCodigo(String codigo) {
        return modelFactory.getEmpresaLogistica().getEnvios().stream()
                .filter(e -> e.getIdEnvio().equalsIgnoreCase(codigo))
                .findFirst()
                .orElse(null);
    }

    public Usuario getUsuarioActual() {
        return modelFactory.getUsuarioActual();
    }
}