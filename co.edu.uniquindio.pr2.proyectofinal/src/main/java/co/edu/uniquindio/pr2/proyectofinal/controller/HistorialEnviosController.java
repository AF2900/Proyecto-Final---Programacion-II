package co.edu.uniquindio.pr2.proyectofinal.controller;

import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import co.edu.uniquindio.pr2.proyectofinal.mapping.dto.EnvioDTO;
import co.edu.uniquindio.pr2.proyectofinal.services.ILogisticaMapping;
import co.edu.uniquindio.pr2.proyectofinal.mapping.mappers.LogisticaMappingImpl;
import co.edu.uniquindio.pr2.proyectofinal.model.*;
import co.edu.uniquindio.pr2.proyectofinal.strategy.ExportarCSV;
import co.edu.uniquindio.pr2.proyectofinal.strategy.ExportarPDF;
import co.edu.uniquindio.pr2.proyectofinal.strategy.Exportador;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class HistorialEnviosController {

    private final ModelFactory modelFactory = ModelFactory.getInstance();
    private final Exportador exportador = new Exportador();
    private final ILogisticaMapping mapper = new LogisticaMappingImpl();

    public List<Envio> obtenerEnviosUsuarioActual() {
        var usuario = modelFactory.getUsuarioActual();
        if (usuario == null) return List.of();
        return modelFactory.getEmpresaLogistica().getEnvios().stream()
                .filter(e -> e.getUsuario() != null && e.getUsuario().equals(usuario))
                .collect(Collectors.toList());
    }

    public List<EnvioDTO> obtenerEnviosUsuarioActualDTO() {
        return obtenerEnviosUsuarioActual().stream()
                .map(mapper::mapFromEnvio)
                .collect(Collectors.toList());
    }

    public List<String> obtenerNombresEstados() {
        var lista = EstadoEnvio.values();
        return java.util.stream.IntStream.range(0, lista.length)
                .mapToObj(i -> lista[i].name())
                .collect(Collectors.toList());
    }

    public List<Envio> filtrarEnvios(List<Envio> base, String estadoSeleccionado, String codigoBusqueda) {
        String codigo = codigoBusqueda == null ? "" : codigoBusqueda.trim().toLowerCase(Locale.ROOT);
        return base.stream()
                .filter(e -> estadoSeleccionado == null || estadoSeleccionado.equals("Todos")
                        || e.getEstado() != null && e.getEstado().name().equalsIgnoreCase(estadoSeleccionado))
                .filter(e -> e.getIdEnvio() != null && e.getIdEnvio().toLowerCase(Locale.ROOT).contains(codigo))
                .collect(Collectors.toList());
    }

    public List<EnvioDTO> filtrarEnviosDTO(List<EnvioDTO> base, String estadoSeleccionado, String codigoBusqueda) {
        String codigo = codigoBusqueda == null ? "" : codigoBusqueda.trim().toLowerCase(Locale.ROOT);
        return base.stream()
                .filter(e -> estadoSeleccionado == null || estadoSeleccionado.equals("Todos")
                        || e.estado() != null && e.estado().name().equalsIgnoreCase(estadoSeleccionado))
                .filter(e -> e.idEnvio() != null && e.idEnvio().toLowerCase(Locale.ROOT).contains(codigo))
                .collect(Collectors.toList());
    }

    public void exportarPDF(List<Envio> envios) {
        exportador.setEstrategia(new ExportarPDF());
        exportador.ejecutarExportacion(envios);
    }

    public void exportarCSV(List<Envio> envios) {
        exportador.setEstrategia(new ExportarCSV());
        exportador.ejecutarExportacion(envios);
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
}