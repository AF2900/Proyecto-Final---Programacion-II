package co.edu.uniquindio.pr2.proyectofinal.factory;

import co.edu.uniquindio.pr2.proyectofinal.model.*;
import co.edu.uniquindio.pr2.proyectofinal.builder.*;
import java.time.LocalDate;

public class ModelFactory {
    private final PlataformaLogistica plataforma;

    private ModelFactory() {
        this.plataforma = new PlataformaLogistica();
    }

    private static class Holder {
        private static final ModelFactory INSTANCE = new ModelFactory();
    }

    public static ModelFactory getInstance() {
        return Holder.INSTANCE;
    }

    public PlataformaLogistica getPlataforma() {
        return plataforma;
    }

    public void inicializarDatos() {
        if (!plataforma.getUsuarios().isEmpty()) return;

        Direccion dirCasa = new DireccionBuilder()
                .idDireccion("D001")
                .alias("Casa")
                .calle("Calle 10 #5-23")
                .ciudad("Armenia")
                .latitud(4.537)
                .longitud(-75.670)
                .build();

        Direccion dirOficina = new DireccionBuilder()
                .idDireccion("D002")
                .alias("Oficina")
                .calle("Carrera 8 #10-20")
                .ciudad("Pereira")
                .latitud(4.810)
                .longitud(-75.690)
                .build();

        Usuario usuario1 = new UsuarioBuilder()
                .idUsuario("U001")
                .nombre("Juan Pérez")
                .correo("juan@correo.com")
                .telefono("3101112233")
                .build();

        Usuario usuario2 = new UsuarioBuilder()
                .idUsuario("U002")
                .nombre("Ana Gómez")
                .correo("ana@correo.com")
                .telefono("3124455667")
                .build();

        usuario1.getDirecciones().add(dirCasa);
        usuario2.getDirecciones().add(dirOficina);

        plataforma.getUsuarios().add(usuario1);
        plataforma.getUsuarios().add(usuario2);

        Repartidor repartidor1 = new RepartidorBuilder()
                .nombre("Carlos Ruiz")
                .correo("carlos@correo.com")
                .telefono("3001234567")
                .idRepartidor("R001")
                .disponibilidadRepartidor(DisponibilidadRepartidor.DISPONIBLE)
                .zonaCobertura("Eje Cafetero")
                .build();

        plataforma.getRepartidores().add(repartidor1);

        Tarifa tarifa1 = new TarifaBuilder()
                .idTarifa("T001")
                .precio(15000)
                .build();

        plataforma.getTarifas().add(tarifa1);

        Envio envio1 = new EnvioBuilder()
                .idEnvio("E001")
                .origen(dirCasa)
                .destino(dirOficina)
                .peso(3.5)
                .largo(40)
                .ancho(30)
                .alto(20)
                .costo(25000)
                .fechaCreacion(LocalDate.now())
                .fechaEstimadaEntrega(LocalDate.now().plusDays(2))
                .estado(EstadoEnvio.EN_CAMINO)
                .repartidor(repartidor1)
                .usuario(usuario1)
                .build();

        plataforma.getEnvios().add(envio1);

        Pago pago1 = new PagoBuilder()
                .idPago("P001")
                .monto(25000)
                .fecha(LocalDate.now())
                .metodoPago(MetodoPago.TARJETA_CREDITO)
                .resultado("APROBADO")
                .build();

        plataforma.getPagos().add(pago1);

        ServicioAdicional servicio1 = new ServicioAdicionalBuilder()
                .idServicioAdd("S001")
                .tipoServicio("Empaque Premium")
                .costoServicioAdd(5000)
                .envioAsociado(envio1)
                .build();

        plataforma.getServiciosAdicionales().add(servicio1);

        Incidencia incidencia1 = new IncidenciaBuilder()
                .idIncidencia("I001")
                .descripcion("Demora por condiciones climáticas")
                .fecha(LocalDate.now())
                .estadoIncidencia("Media")
                .envioAsociado(envio1)
                .build();

        plataforma.getIncidencias().add(incidencia1);
        repartidor1.getEnviosAsignados().add(envio1);
    }

    public void resetAll() {
        plataforma.getUsuarios().clear();
        plataforma.getRepartidores().clear();
        plataforma.getEnvios().clear();
        plataforma.getPagos().clear();
        plataforma.getTarifas().clear();
        plataforma.getIncidencias().clear();
        plataforma.getServiciosAdicionales().clear();
    }
}