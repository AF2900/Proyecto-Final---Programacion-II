package co.edu.uniquindio.pr2.proyectofinal.factory;

import co.edu.uniquindio.pr2.proyectofinal.model.*;
import co.edu.uniquindio.pr2.proyectofinal.builder.*;
import co.edu.uniquindio.pr2.proyectofinal.mapping.dto.*;
import co.edu.uniquindio.pr2.proyectofinal.mapping.mappers.LogisticaMappingImpl;
import co.edu.uniquindio.pr2.proyectofinal.services.ILogisticaMapping;
import java.time.LocalDate;
import java.util.List;

public class ModelFactory {

    private final ILogisticaMapping logisticaMapping;

    private ModelFactory() {
        this.empresaLogistica = new EmpresaLogistica();
        this.logisticaMapping = new LogisticaMappingImpl();
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

    public ILogisticaMapping getLogisticaMapping() {
        return logisticaMapping;
    }

    public List<EnvioDTO> obtenerEnviosDTO() {
        return logisticaMapping.listaEnviosToDTO(empresaLogistica.getEnvios());
    }

    public List<RepartidorDTO> obtenerRepartidoresDTO() {
        return empresaLogistica.getRepartidores()
                .stream()
                .map(logisticaMapping::repartidorToDTO)
                .toList();
    }

    public List<UsuarioDTO> obtenerUsuariosDTO() {
        return empresaLogistica.getUsuarios()
                .stream()
                .map(logisticaMapping::usuarioToDTO)
                .toList();
    }

    public List<AdministradorDTO> obtenerAdministradoresDTO() {
        return empresaLogistica.getAdministradores()
                .stream()
                .map(logisticaMapping::administradorToDTO)
                .toList();
    }

    public List<PagoDTO> obtenerPagosDTO() {
        return empresaLogistica.getPagos()
                .stream()
                .map(logisticaMapping::pagoToDTO)
                .toList();
    }

    public List<TarifaDTO> obtenerTarifasDTO() {
        return empresaLogistica.getTarifas()
                .stream()
                .map(logisticaMapping::tarifaToDTO)
                .toList();
    }

    public List<IncidenciaDTO> obtenerIncidenciasDTO() {
        return empresaLogistica.getIncidencias()
                .stream()
                .map(logisticaMapping::incidenciaToDTO)
                .toList();
    }

    public List<ServicioAdicionalDTO> obtenerServiciosAdicionalesDTO() {
        return empresaLogistica.getServiciosAdicionales()
                .stream()
                .map(logisticaMapping::servicioAdicionalToDTO)
                .toList();
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
                .correo("juanpe@gmail.com")
                .telefono("3101112233")
                .password("1234")
                .build();

        Usuario usuario2 = new UsuarioBuilder()
                .idUsuario("U002")
                .nombre("Ana Gómez")
                .correo("anagomez21@gmail.com")
                .telefono("3124455667")
                .password("2233")
                .build();

        usuario1.getDirecciones().add(dirCasa);
        usuario2.getDirecciones().add(dirOficina);

        empresaLogistica.getUsuarios().add(usuario1);
        empresaLogistica.getUsuarios().add(usuario2);

        Repartidor repartidor1 = new RepartidorBuilder()
                .nombre("Carlos Ruiz")
                .cedula("1004")
                .telefono("3001234567")
                .idRepartidor("R001")
                .password("22679")
                .disponibilidadRepartidor(DisponibilidadRepartidor.DISPONIBLE)
                .zonaCobertura("Eje Cafetero")
                .build();

        Repartidor repartidor2 = new RepartidorBuilder()
                .nombre("Laura Sánchez")
                .cedula("1003")
                .telefono("3015558888")
                .idRepartidor("R002")
                .password("ls123")
                .disponibilidadRepartidor(DisponibilidadRepartidor.DISPONIBLE)
                .zonaCobertura("Quindío")
                .build();

        Repartidor repartidor3 = new RepartidorBuilder()
                .nombre("Andrés Gómez")
                .cedula("1002")
                .telefono("3026669999")
                .idRepartidor("R003")
                .password("ag456")
                .disponibilidadRepartidor(DisponibilidadRepartidor.DISPONIBLE)
                .zonaCobertura("Pereira - Dosquebradas")
                .build();

        Repartidor repartidor4 = new RepartidorBuilder()
                .nombre("María Fernanda López")
                .cedula("1004")
                .telefono("3026669999")
                .idRepartidor("R004")
                .password("mf789")
                .disponibilidadRepartidor(DisponibilidadRepartidor.DISPONIBLE)
                .zonaCobertura("Calarcá")
                .build();

        empresaLogistica.getRepartidores().add(repartidor1);
        empresaLogistica.getRepartidores().add(repartidor2);
        empresaLogistica.getRepartidores().add(repartidor3);
        empresaLogistica.getRepartidores().add(repartidor4);

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
                .tipoServicio(TipoServicio.FIRMA_REQUERIDA)
                .costoServicioAdd(5000)
                .envioAsociado(envio1)
                .build();

        empresaLogistica.getServiciosAdicionales().add(servicio1);

        Incidencia incidencia1 = new IncidenciaBuilder()
                .idIncidencia("I001")
                .descripcion("Demora por condiciones climáticas")
                .fecha(LocalDate.now())
                .estadoIncidencia(EstadoIncidencia.SINIESTRO)
                .envioAsociado(envio1)
                .build();

        empresaLogistica.getIncidencias().add(incidencia1);
        repartidor1.getEnviosAsignados().add(envio1);

        Administrador administrador1 = new AdministradorBuilder()
                .nombre("Douglas Albeiro")
                .correo("douglasmanias@gmail.com")
                .telefono("31344666566")
                .password("admin")
                .idAdministrador("456")
                .build();

        Administrador administrador2 = new AdministradorBuilder()
                .nombre("Sofia Lopez")
                .correo("sof23@gmail.com")
                .telefono("32234557432")
                .password("sofia123")
                .idAdministrador("789")
                .build();

        empresaLogistica.getAdministradores().add(administrador1);
        empresaLogistica.getAdministradores().add(administrador2);
    }

    private Usuario usuarioActual;
    public Usuario getUsuarioActual() { return usuarioActual; }
    public void setUsuarioActual(Usuario usuarioActual) { this.usuarioActual = usuarioActual; }
    private final EmpresaLogistica empresaLogistica;

    private Administrador administradorActual;
    public Administrador getAdministradorActual() { return administradorActual; }
    public void setAdministradorActual(Administrador administradorActual) { this.administradorActual = administradorActual; }

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