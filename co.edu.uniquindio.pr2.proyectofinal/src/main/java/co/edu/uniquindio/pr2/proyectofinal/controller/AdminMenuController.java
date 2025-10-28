package co.edu.uniquindio.pr2.proyectofinal.controller;

import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import co.edu.uniquindio.pr2.proyectofinal.model.*;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import java.util.List;

public class AdminMenuController {

    private final ModelFactory modelFactory = ModelFactory.getInstance();

    public void actualizarReportes(Label totalUsersLabel, Label pendingShipmentsLabel, Label activeDeliveryLabel,
                                   Label incidentsLabel, BarChart<String, Number> revenueChart,
                                   PieChart servicesChart, LineChart<String, Number> deliveryTimesChart) {

        totalUsersLabel.setText(String.valueOf(modelFactory.getEmpresaLogistica().getUsuarios().size()));
        List<Envio> envios = modelFactory.getEmpresaLogistica().getEnvios();

        long pendientes = envios.stream().filter(e -> e.getEstado() == EstadoEnvio.PENDIENTE).count();
        long incidencias = envios.stream().filter(e -> !e.getListaIncidencias().isEmpty()
                && e.getListaIncidencias().get(0).getEstadoIncidencia() != null).count();
        long repartidoresActivos = modelFactory.getEmpresaLogistica().getRepartidores().stream()
                .filter(r -> r.getDisponibilidadRepartidor() == DisponibilidadRepartidor.DISPONIBLE).count();

        pendingShipmentsLabel.setText(String.valueOf(pendientes));
        activeDeliveryLabel.setText(String.valueOf(repartidoresActivos));
        incidentsLabel.setText(String.valueOf(incidencias));

        revenueChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Ingresos por Servicio Adicional");

        double seguroTotal = envios.stream().flatMap(e -> e.getListaServiciosAdicionales().stream())
                .filter(s -> s.getTipoServicio() == TipoServicio.SEGURO)
                .mapToDouble(ServicioAdicional::getCostoServicioAdd).sum();
        double fragilTotal = envios.stream().flatMap(e -> e.getListaServiciosAdicionales().stream())
                .filter(s -> s.getTipoServicio() == TipoServicio.FRAGIL)
                .mapToDouble(ServicioAdicional::getCostoServicioAdd).sum();
        double firmaTotal = envios.stream().flatMap(e -> e.getListaServiciosAdicionales().stream())
                .filter(s -> s.getTipoServicio() == TipoServicio.FIRMA_REQUERIDA)
                .mapToDouble(ServicioAdicional::getCostoServicioAdd).sum();

        series.getData().add(new XYChart.Data<>("Seguro", seguroTotal));
        series.getData().add(new XYChart.Data<>("Frágil", fragilTotal));
        series.getData().add(new XYChart.Data<>("Firma", firmaTotal));
        revenueChart.getData().add(series);

        servicesChart.getData().clear();
        int totalServicios = (int) envios.stream().flatMap(e -> e.getListaServiciosAdicionales().stream()).count();

        if (totalServicios > 0) {
            long seguroCount = envios.stream().flatMap(e -> e.getListaServiciosAdicionales().stream())
                    .filter(s -> s.getTipoServicio() == TipoServicio.SEGURO).count();
            long fragilCount = envios.stream().flatMap(e -> e.getListaServiciosAdicionales().stream())
                    .filter(s -> s.getTipoServicio() == TipoServicio.FRAGIL).count();
            long firmaCount = envios.stream().flatMap(e -> e.getListaServiciosAdicionales().stream())
                    .filter(s -> s.getTipoServicio() == TipoServicio.FIRMA_REQUERIDA).count();

            servicesChart.getData().add(new PieChart.Data("Seguro", seguroCount));
            servicesChart.getData().add(new PieChart.Data("Frágil", fragilCount));
            servicesChart.getData().add(new PieChart.Data("Firma", firmaCount));
        }

        deliveryTimesChart.getData().clear();
        XYChart.Series<String, Number> lineSeries = new XYChart.Series<>();
        lineSeries.setName("Envíos por día");

        envios.stream().map(Envio::getFechaCreacion).distinct().sorted().forEach(fecha -> {
            long count = envios.stream().filter(e -> e.getFechaCreacion().equals(fecha)).count();
            lineSeries.getData().add(new XYChart.Data<>(fecha.toString(), count));
        });

        deliveryTimesChart.getData().add(lineSeries);
    }

    public Administrador obtenerAdminActual() {
        return modelFactory.getAdministradorActual();
    }

    public void cerrarSesion() {
        modelFactory.setUsuarioActual(null);
    }
}