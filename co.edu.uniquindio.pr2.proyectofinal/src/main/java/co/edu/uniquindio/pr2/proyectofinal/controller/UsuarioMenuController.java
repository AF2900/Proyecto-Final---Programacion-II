package co.edu.uniquindio.pr2.proyectofinal.controller;

import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import co.edu.uniquindio.pr2.proyectofinal.services.ILogisticaMapping;
import co.edu.uniquindio.pr2.proyectofinal.mapping.mappers.LogisticaMappingImpl;
import co.edu.uniquindio.pr2.proyectofinal.model.Envio;
import co.edu.uniquindio.pr2.proyectofinal.model.EstadoEnvio;
import co.edu.uniquindio.pr2.proyectofinal.model.ServicioAdicional;
import co.edu.uniquindio.pr2.proyectofinal.model.Usuario;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioMenuController {

    private final ModelFactory modelFactory = ModelFactory.getInstance();
    private final ILogisticaMapping mapping = new LogisticaMappingImpl();

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

    public double calcularCostoTotalEnvio(Envio envio) {
        if (envio == null) return 0;
        double costoBase = envio.getCosto();
        double servicios = envio.getListaServiciosAdicionales() == null ? 0 :
                envio.getListaServiciosAdicionales().stream()
                        .mapToDouble(ServicioAdicional::getCostoServicioAdd)
                        .sum();
        return costoBase + servicios;
    }

    public Usuario getUsuarioActual() {
        return modelFactory.getUsuarioActual();
    }

    public void mapearUsuarioActual() {
        Usuario usuario = getUsuarioActual();
        if (usuario != null) {
            var mapped = mapping.mapFromUsuario(usuario);
            System.out.println("Usuario mapeado: " + mapped);
        }
    }
}