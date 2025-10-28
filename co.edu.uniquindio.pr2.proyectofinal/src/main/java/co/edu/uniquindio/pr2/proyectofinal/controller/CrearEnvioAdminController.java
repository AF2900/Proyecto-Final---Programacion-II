package co.edu.uniquindio.pr2.proyectofinal.controller;

import co.edu.uniquindio.pr2.proyectofinal.builder.DireccionBuilder;
import co.edu.uniquindio.pr2.proyectofinal.builder.ServicioAdicionalBuilder;
import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import co.edu.uniquindio.pr2.proyectofinal.model.*;
import java.time.LocalDate;
import java.util.*;

public class CrearEnvioAdminController {

    private final ModelFactory modelFactory = ModelFactory.getInstance();

    public List<Usuario> obtenerUsuarios() {
        return modelFactory.getEmpresaLogistica().getUsuarios();
    }

    public List<Repartidor> obtenerRepartidores() {
        return modelFactory.getEmpresaLogistica().getRepartidores();
    }

    public Envio crearEnvio(Usuario usuario, Repartidor repartidor, String origenTexto, String destinoTexto,
                            double peso, double volumen, String prioridad,
                            boolean seguro, boolean fragil, boolean firma) {

        String idEnvio = String.valueOf(new Random().nextInt(90000) + 10000);
        Direccion origen = new DireccionBuilder().idDireccion(UUID.randomUUID().toString()).calle(origenTexto).build();
        Direccion destino = new DireccionBuilder().idDireccion(UUID.randomUUID().toString()).calle(destinoTexto).build();

        LocalDate fechaCreacion = LocalDate.now();
        LocalDate fechaEstimada = prioridad.equals("Urgente") ? fechaCreacion
                : prioridad.equals("Alta") ? fechaCreacion.plusDays(1) : fechaCreacion.plusDays(2);

        Envio envio = new Envio(idEnvio, origen, destino, peso, Math.cbrt(volumen), Math.cbrt(volumen),
                Math.cbrt(volumen), fechaCreacion, fechaEstimada, EstadoEnvio.PENDIENTE);
        envio.setUsuario(usuario);
        envio.setRepartidor(repartidor);

        double costo = (peso * 0.5) + (volumen * 0.02);
        envio.setCosto(costo);

        if (seguro)
            envio.getListaServiciosAdicionales().add(new ServicioAdicionalBuilder()
                    .idServicioAdd(UUID.randomUUID().toString())
                    .tipoServicio(TipoServicio.SEGURO)
                    .costoServicioAdd(10000).build());
        if (fragil)
            envio.getListaServiciosAdicionales().add(new ServicioAdicionalBuilder()
                    .idServicioAdd(UUID.randomUUID().toString())
                    .tipoServicio(TipoServicio.FRAGIL)
                    .costoServicioAdd(5000).build());
        if (firma)
            envio.getListaServiciosAdicionales().add(new ServicioAdicionalBuilder()
                    .idServicioAdd(UUID.randomUUID().toString())
                    .tipoServicio(TipoServicio.FIRMA_REQUERIDA)
                    .costoServicioAdd(3000).build());

        modelFactory.getEmpresaLogistica().getEnvios().add(envio);
        return envio;
    }

    public Optional<Envio> obtenerEnvioPorUsuario(Usuario usuario) {
        return modelFactory.getEmpresaLogistica().getEnvios()
                .stream()
                .filter(e -> e.getUsuario().equals(usuario))
                .findFirst();
    }

    public String calcularPrioridad(LocalDate fechaCreacion, LocalDate fechaEstimada) {
        long dias = fechaEstimada.toEpochDay() - fechaCreacion.toEpochDay();
        if (dias == 0) return "Urgente";
        else if (dias == 1) return "Alta";
        else return "Baja";
    }
}