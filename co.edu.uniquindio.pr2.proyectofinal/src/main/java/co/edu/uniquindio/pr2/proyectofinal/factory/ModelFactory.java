package co.edu.uniquindio.pr2.proyectofinal.factory;

import co.edu.uniquindio.pr2.proyectofinal.model.*;
import co.edu.uniquindio.pr2.proyectofinal.builder.*;
import java.time.LocalDate;

public class ModelFactory {
    private EmpresaLogistica empresaLogistica;

    private ModelFactory() {
        this.empresaLogistica = new EmpresaLogistica();
    }

    private static class Holder {
        private static final ModelFactory INSTANCE = new ModelFactory();
    }

    public static ModelFactory getInstance() {
        return Holder.INSTANCE;
    }

    public EmpresaLogistica getEmpresaLogistica() {
        return empresaLogistica;
    }

    public void inicializarDatos() {
        if (!empresaLogistica.getUsuarios().isEmpty()) return;

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
                .correo("juanuser@gmail.com")
                .telefono("3101112233")
                .password("1234")
                .build();

        Usuario usuario2 = new UsuarioBuilder()
                .idUsuario("U002")
                .nombre("Ana Gómez")
                .correo("anauser@gmail.com")
                .telefono("3124455667")
                .password("2233")
                .build();

        usuario1.getDirecciones().add(dirCasa);
        usuario2.getDirecciones().add(dirOficina);

        empresaLogistica.getUsuarios().add(usuario1);
        empresaLogistica.getUsuarios().add(usuario2);

        Repartidor repartidor1 = new RepartidorBuilder()
                .nombre("Carlos Ruiz")
                .correo("carlosdealer@gmail.com")
                .telefono("3001234567")
                .idRepartidor("R001")
                .password("22679")
                .disponibilidadRepartidor(DisponibilidadRepartidor.DISPONIBLE)
                .zonaCobertura("Eje Cafetero")
                .build();

        empresaLogistica.getRepartidores().add(repartidor1);

        Tarifa tarifa1 = new TarifaBuilder()
                .idTarifa("T001")
                .precio(15000)
                .build();

        empresaLogistica.getTarifas().add(tarifa1);

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

        empresaLogistica.getEnvios().add(envio1);

        Pago pago1 = new PagoBuilder()
                .idPago("P001")
                .monto(25000)
                .fecha(LocalDate.now())
                .metodoPago(MetodoPago.TARJETA_CREDITO)
                .resultado("APROBADO")
                .build();

        empresaLogistica.getPagos().add(pago1);

        ServicioAdicional servicio1 = new ServicioAdicionalBuilder()
                .idServicioAdd("S001")
                .tipoServicio("Empaque Premium")
                .costoServicioAdd(5000)
                .envioAsociado(envio1)
                .build();

        empresaLogistica.getServiciosAdicionales().add(servicio1);

        Incidencia incidencia1 = new IncidenciaBuilder()
                .idIncidencia("I001")
                .descripcion("Demora por condiciones climáticas")
                .fecha(LocalDate.now())
                .estadoIncidencia("Media")
                .envioAsociado(envio1)
                .build();

        empresaLogistica.getIncidencias().add(incidencia1);
        repartidor1.getEnviosAsignados().add(envio1);

        Administrador administrador1 = new AdministradorBuilder()
                .nombre("Douglas Albeiro")
                .correo("douglasadmin@gmail.com")
                .telefono("31344666566")
                .password("admin")
                .idAdministrador("456")
                .build();

        Administrador administrador2 = new AdministradorBuilder()
                .nombre("Sofia Lopez")
                .correo("sofiaadmin@gmail.com")
                .telefono("32234557432")
                .password("admin233")
                .idAdministrador("789")
                .build();

        empresaLogistica.getAdministradores().add(administrador1);
        empresaLogistica.getAdministradores().add(administrador2);
    }

    private Usuario usuarioActual;

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    private Administrador administradorActual;

    public Administrador getAdministradorActual() {
        return administradorActual;
    }

    public void setAdministradorActual(Administrador administradorActual) {
        this.administradorActual = administradorActual;
    }

    public void resetAll() {
        empresaLogistica.getUsuarios().clear();
        empresaLogistica.getRepartidores().clear();
        empresaLogistica.getEnvios().clear();
        empresaLogistica.getPagos().clear();
        empresaLogistica.getTarifas().clear();
        empresaLogistica.getIncidencias().clear();
        empresaLogistica.getServiciosAdicionales().clear();
        empresaLogistica.getAdministradores().clear();
    }
}