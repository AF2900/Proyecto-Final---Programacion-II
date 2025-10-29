package co.edu.uniquindio.pr2.proyectofinal.controller;

import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import co.edu.uniquindio.pr2.proyectofinal.model.*;
import co.edu.uniquindio.pr2.proyectofinal.builder.DireccionBuilder;
import co.edu.uniquindio.pr2.proyectofinal.builder.ServicioAdicionalBuilder;
import co.edu.uniquindio.pr2.proyectofinal.builder.PagoBuilder;
import co.edu.uniquindio.pr2.proyectofinal.observer.UsuarioObserver;
import java.time.LocalDate;
import java.util.*;

public class CrearEnvioController {

    private final ModelFactory modelFactory = ModelFactory.getInstance();

    public Optional<String> validarCampos(String origen, String destino, String peso, String volumen, String prioridad, MetodoPago metodoPago) {
        if (origen.isEmpty() || destino.isEmpty() || peso.isEmpty() || volumen.isEmpty() || prioridad == null || metodoPago == null)
            return Optional.of("Por favor llena todos los campos.");
        return Optional.empty();
    }

    public Envio crearEnvio(String origenTexto,
                            String destinoTexto,
                            double peso,
                            double volumen,
                            String prioridad,
                            boolean seguro,
                            boolean fragil,
                            boolean firma,
                            MetodoPago metodoPago) {

        String idEnvio = String.valueOf(new Random().nextInt(90000) + 10000);
        Direccion origen = new DireccionBuilder().idDireccion(UUID.randomUUID().toString()).calle(origenTexto).build();
        Direccion destino = new DireccionBuilder().idDireccion(UUID.randomUUID().toString()).calle(destinoTexto).build();

        LocalDate fechaCreacion = LocalDate.now();
        LocalDate fechaEstimada;
        EstadoEnvio estadoEnvio;

        switch (prioridad) {
            case "Alta" -> { fechaEstimada = fechaCreacion.plusDays(1); estadoEnvio = EstadoEnvio.EN_CAMINO; }
            case "Urgente" -> { fechaEstimada = fechaCreacion; estadoEnvio = EstadoEnvio.ENTREGADO; }
            default -> { fechaEstimada = fechaCreacion.plusDays(2); estadoEnvio = EstadoEnvio.PENDIENTE; }
        }

        double lado = Math.cbrt(volumen);
        double dimension = Math.round(lado * 100.0) / 100.0;

        Envio envio = new Envio(idEnvio, origen, destino, peso, dimension, dimension, dimension, fechaCreacion, fechaEstimada, estadoEnvio);
        double costoBase = (peso * 0.5) + (volumen * 0.02);
        double costo = switch (prioridad) {
            case "Alta" -> costoBase * 1.4;
            case "Urgente" -> costoBase * 1.8;
            default -> costoBase;
        };
        envio.setCosto(costo);

        if (seguro)
            envio.getListaServiciosAdicionales().add(new ServicioAdicionalBuilder().idServicioAdd(UUID.randomUUID().toString()).tipoServicio(TipoServicio.SEGURO).costoServicioAdd(10000).build());
        if (fragil)
            envio.getListaServiciosAdicionales().add(new ServicioAdicionalBuilder().idServicioAdd(UUID.randomUUID().toString()).tipoServicio(TipoServicio.FRAGIL).costoServicioAdd(5000).build());
        if (firma)
            envio.getListaServiciosAdicionales().add(new ServicioAdicionalBuilder().idServicioAdd(UUID.randomUUID().toString()).tipoServicio(TipoServicio.FIRMA_REQUERIDA).costoServicioAdd(3000).build());

        Usuario usuario = modelFactory.getUsuarioActual();
        if (usuario != null) {
            envio.setUsuario(usuario);
            UsuarioObserver observer = new UsuarioObserver(usuario);
            envio.agregarObserver(observer);
            envio.setEstado(envio.getEstado());
        }

        Optional<Repartidor> repartidorDisponible = modelFactory.getEmpresaLogistica().getRepartidores().stream()
                .filter(r -> r.getDisponibilidadRepartidor() == DisponibilidadRepartidor.DISPONIBLE)
                .findFirst();

        repartidorDisponible.ifPresent(r -> {
            envio.setRepartidor(r);
            r.getEnviosAsignados().add(envio);
            r.setDisponibilidadRepartidor(DisponibilidadRepartidor.OCUPADO);
        });

        Pago pago = new PagoBuilder()
                .idPago(UUID.randomUUID().toString())
                .monto(costo)
                .fecha(LocalDate.now())
                .metodoPago(metodoPago)
                .resultado("APROBADO")
                .build();

        modelFactory.getEmpresaLogistica().getPagos().add(pago);
        modelFactory.getEmpresaLogistica().getEnvios().add(envio);

        return envio;
    }

    public double calcularCostoTotal(String prioridad, double peso, double volumen, boolean seguro, boolean fragil, boolean firma) {
        double costoBase = (peso * 0.5) + (volumen * 0.02);
        double costo = switch (prioridad) {
            case "Alta" -> costoBase * 1.4;
            case "Urgente" -> costoBase * 1.8;
            default -> costoBase;
        };
        if (seguro) costo += 10000;
        if (fragil) costo += 5000;
        if (firma) costo += 3000;

        return costo;
    }

    public Usuario getUsuarioActual() {
        return modelFactory.getUsuarioActual();
    }
}