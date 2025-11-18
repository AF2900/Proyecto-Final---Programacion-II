package co.edu.uniquindio.pr2.proyectofinal.controller;

import co.edu.uniquindio.pr2.proyectofinal.adapter.INotificador;
import co.edu.uniquindio.pr2.proyectofinal.adapter.NotificadorAdapter;
import co.edu.uniquindio.pr2.proyectofinal.adapter.NotificadorExterno;
import co.edu.uniquindio.pr2.proyectofinal.builder.DireccionBuilder;
import co.edu.uniquindio.pr2.proyectofinal.builder.ServicioAdicionalBuilder;
import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import co.edu.uniquindio.pr2.proyectofinal.factoryMethod.factory.BicicletaRepartidorFactory;
import co.edu.uniquindio.pr2.proyectofinal.factoryMethod.factory.CarroRepartidorFactory;
import co.edu.uniquindio.pr2.proyectofinal.factoryMethod.factory.MotoRepartidorFactory;
import co.edu.uniquindio.pr2.proyectofinal.factoryMethod.model.IRepartidorBase;
import co.edu.uniquindio.pr2.proyectofinal.mapping.dto.EnvioDTO;
import co.edu.uniquindio.pr2.proyectofinal.services.ILogisticaMapping;
import co.edu.uniquindio.pr2.proyectofinal.model.*;
import co.edu.uniquindio.pr2.proyectofinal.command.EnvioReceiver;
import co.edu.uniquindio.pr2.proyectofinal.command.EnvioInvoker;
import co.edu.uniquindio.pr2.proyectofinal.command.CrearEnvioCommand;
import java.time.LocalDate;
import java.util.*;

public class CrearEnvioAdminController {

    private final ModelFactory modelFactory = ModelFactory.getInstance();
    private final ILogisticaMapping mapper = modelFactory.getLogisticaMapping();

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

        Direccion origen = new DireccionBuilder()
                .idDireccion(UUID.randomUUID().toString())
                .calle(origenTexto)
                .build();

        Direccion destino = new DireccionBuilder()
                .idDireccion(UUID.randomUUID().toString())
                .calle(destinoTexto)
                .build();

        LocalDate fechaCreacion = LocalDate.now();
        LocalDate fechaEstimada = prioridad.equals("Urgente") ? fechaCreacion
                : prioridad.equals("Alta") ? fechaCreacion.plusDays(1)
                : fechaCreacion.plusDays(2);

        Envio envio = new Envio(
                idEnvio,
                origen,
                destino,
                peso,
                Math.cbrt(volumen),
                Math.cbrt(volumen),
                Math.cbrt(volumen),
                fechaCreacion,
                fechaEstimada,
                EstadoEnvio.PENDIENTE,
                ""
        );
        envio.setUsuario(usuario);
        envio.setRepartidor(repartidor);

        double costoBase = (peso * 0.5) + (volumen * 0.02);
        double costoFinal = calcularCostoConPrioridadYServicios(costoBase, prioridad, seguro, fragil, firma);
        envio.setCosto(Math.round(costoFinal * 100.0) / 100.0);

        if (seguro)
            envio.getListaServiciosAdicionales().add(new ServicioAdicionalBuilder()
                    .idServicioAdd(UUID.randomUUID().toString())
                    .tipoServicio(TipoServicio.SEGURO)
                    .costoServicioAdd(10000)
                    .build());
        if (fragil)
            envio.getListaServiciosAdicionales().add(new ServicioAdicionalBuilder()
                    .idServicioAdd(UUID.randomUUID().toString())
                    .tipoServicio(TipoServicio.FRAGIL)
                    .costoServicioAdd(5000)
                    .build());
        if (firma)
            envio.getListaServiciosAdicionales().add(new ServicioAdicionalBuilder()
                    .idServicioAdd(UUID.randomUUID().toString())
                    .tipoServicio(TipoServicio.FIRMA_REQUERIDA)
                    .costoServicioAdd(3000)
                    .build());

        var factoria = switch (new Random().nextInt(3)) {
            case 0 -> new MotoRepartidorFactory();
            case 1 -> new CarroRepartidorFactory();
            default -> new BicicletaRepartidorFactory();
        };

        IRepartidorBase rep = factoria.crearRepartidor();
        envio.setDescripcionRepartidor(rep.getTipo());

        EnvioReceiver receiver = new EnvioReceiver();
        EnvioInvoker invoker = new EnvioInvoker();
        CrearEnvioCommand crearEnvioCommand = new CrearEnvioCommand(receiver, envio);
        invoker.recibirOperacion(crearEnvioCommand);
        invoker.ejecutarOperaciones();

        EnvioDTO envioDTO = mapper.mapFromEnvio(envio);

        INotificador notificador = new NotificadorAdapter(new NotificadorExterno());
        StringBuilder servicios = new StringBuilder();
        if (seguro) servicios.append("Seguro (+$10000)\n");
        if (fragil) servicios.append("Frágil (+$5000)\n");
        if (firma) servicios.append("Firma requerida (+$3000)\n");
        if (servicios.isEmpty()) servicios.append("Ninguno");
        String mensaje = "Nuevo envío creado:\n\nCódigo: " + idEnvio +
                "\nUsuario: " + usuario.getNombre() +
                "\nRepartidor: " + repartidor.getIdRepartidor() +
                "\nPrioridad: " + prioridad +
                "\nServicios adicionales:\n" + servicios +
                "\nCosto total: $" + envio.getCosto();
        notificador.notificar(mensaje);

        return envio;
    }

    private double calcularCostoConPrioridadYServicios(double costoBase, String prioridad,
                                                       boolean seguro, boolean fragil, boolean firma) {
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

    public String obtenerDescripcionRepartidor(Envio envio) {
        if (envio == null) return "";
        String desc = envio.getDescripcionRepartidor();
        if (desc != null && !desc.isBlank()) return desc;
        int random = new Random().nextInt(3);
        IRepartidorBase repartidorBase = switch (random) {
            case 0 -> new MotoRepartidorFactory().crearRepartidor();
            case 1 -> new CarroRepartidorFactory().crearRepartidor();
            default -> new BicicletaRepartidorFactory().crearRepartidor();
        };
        String tipo = repartidorBase.getTipo();
        envio.setDescripcionRepartidor(tipo);
        return tipo;
    }
}