package co.edu.uniquindio.pr2.proyectofinal.mapping.mappers;

import co.edu.uniquindio.pr2.proyectofinal.mapping.dto.*;
import co.edu.uniquindio.pr2.proyectofinal.model.*;
import co.edu.uniquindio.pr2.proyectofinal.services.ILogisticaMapping;
import java.util.ArrayList;
import java.util.List;

public class LogisticaMappingImpl implements ILogisticaMapping {

    @Override
    public UsuarioDTO usuarioToDTO(Usuario usuario) {
        if (usuario == null) return null;
        List<DireccionDTO> direccionesDTO = usuario.getDirecciones().stream()
                .map(this::direccionToDTO)
                .toList();
        return new UsuarioDTO(usuario.getIdUsuario(), usuario.getNombre(), usuario.getCorreo(), usuario.getTelefono(), direccionesDTO);
    }

    @Override
    public Usuario dtoToUsuario(UsuarioDTO dto) {
        if (dto == null) return null;
        Usuario usuario = new Usuario(dto.idUsuario(), "", dto.nombre(), dto.correo(), dto.telefono());
        usuario.setDirecciones(dto.direcciones().stream().map(this::dtoToDireccion).toList());
        return usuario;
    }

    @Override
    public RepartidorDTO repartidorToDTO(Repartidor repartidor) {
        if (repartidor == null) return null;
        List<String> idsEnvios = repartidor.getEnviosAsignados() != null ?
                repartidor.getEnviosAsignados().stream().map(Envio::getIdEnvio).toList() :
                new ArrayList<>();
        return new RepartidorDTO(
                repartidor.getCedula(),
                repartidor.getIdRepartidor(),
                repartidor.getNombre(),
                repartidor.getCorreo(),
                repartidor.getTelefono(),
                repartidor.getDisponibilidadRepartidor(),
                repartidor.getZonaCobertura(),
                idsEnvios
        );
    }

    @Override
    public Repartidor dtoToRepartidor(RepartidorDTO dto) {
        if (dto == null) return null;
        return new Repartidor(
                dto.nombre(),
                dto.correo(),
                dto.telefono(),
                "",
                dto.cedula(),
                dto.idRepartidor(),
                dto.zonaCobertura(),
                dto.disponibilidadRepartidor(),
                new ArrayList<>()
        );
    }

    @Override
    public AdministradorDTO administradorToDTO(Administrador admin) {
        if (admin == null) return null;
        return new AdministradorDTO(admin.getIdAdministrador(), admin.getNombre(), admin.getCorreo(), admin.getTelefono());
    }

    @Override
    public Administrador dtoToAdministrador(AdministradorDTO dto) {
        if (dto == null) return null;
        return new Administrador(dto.nombre(), dto.correo(), dto.telefono(), "", "");
    }

    @Override
    public DireccionDTO direccionToDTO(Direccion direccion) {
        if (direccion == null) return null;
        return new DireccionDTO(
                direccion.getIdDireccion(),
                direccion.getAlias(),
                direccion.getCalle(),
                direccion.getCiudad(),
                direccion.getLatitud(),
                direccion.getLongitud()
        );
    }

    @Override
    public Direccion dtoToDireccion(DireccionDTO dto) {
        if (dto == null) return null;
        return new Direccion(dto.idDireccion(), dto.alias(), dto.calle(), dto.ciudad(), dto.latitud(), dto.longitud());
    }

    @Override
    public EnvioDTO envioToDTO(Envio envio) {
        if (envio == null) return null;
        return new EnvioDTO(
                envio.getIdEnvio(),
                direccionToDTO(envio.getOrigen()),
                direccionToDTO(envio.getDestino()),
                envio.getPeso(),
                envio.getLargo(),
                envio.getAncho(),
                envio.getAlto(),
                envio.getCosto(),
                envio.getFechaCreacion(),
                envio.getFechaEstimadaEntrega(),
                envio.getEstado(),
                envio.getDescripcionRepartidor(),
                envio.getRepartidor() != null ? envio.getRepartidor().getIdRepartidor() : null,
                envio.getUsuario() != null ? envio.getUsuario().getIdUsuario() : null,
                envio.getListaServiciosAdicionales() != null
                        ? envio.getListaServiciosAdicionales().stream().map(this::servicioAdicionalToDTO).toList()
                        : List.of(),
                envio.getListaIncidencias() != null
                        ? envio.getListaIncidencias().stream().map(this::incidenciaToDTO).toList()
                        : List.of()
        );
    }

    @Override
    public Envio dtoToEnvio(EnvioDTO dto) {
        if (dto == null) return null;
        Envio envio = new Envio(
                dto.idEnvio(),
                dtoToDireccion(dto.origen()),
                dtoToDireccion(dto.destino()),
                dto.peso(),
                dto.largo(),
                dto.ancho(),
                dto.alto(),
                dto.fechaCreacion(),
                dto.fechaEstimadaEntrega(),
                dto.estado(),
                ""
        );
        envio.setCosto(dto.costo());
        envio.setListaServiciosAdicionales(dto.listaServiciosAdicionales().stream().map(this::dtoToServicioAdicional).toList());
        envio.setListaIncidencias(dto.listaIncidencias().stream().map(this::dtoToIncidencia).toList());
        return envio;
    }

    @Override
    public ServicioAdicionalDTO servicioAdicionalToDTO(ServicioAdicional servicio) {
        if (servicio == null) return null;
        return new ServicioAdicionalDTO(servicio.getIdServicioAdd(), servicio.getTipoServicio(), servicio.getCostoServicioAdd());
    }

    @Override
    public ServicioAdicional dtoToServicioAdicional(ServicioAdicionalDTO dto) {
        if (dto == null) return null;
        return new ServicioAdicional(dto.idServicioAdd(), dto.tipoServicio(), dto.costoServicioAdd());
    }

    @Override
    public IncidenciaDTO incidenciaToDTO(Incidencia incidencia) {
        if (incidencia == null) return null;
        return new IncidenciaDTO(incidencia.getIdIncidencia(), incidencia.getDescripcion(), incidencia.getFecha(), incidencia.getEstadoIncidencia());
    }

    @Override
    public Incidencia dtoToIncidencia(IncidenciaDTO dto) {
        if (dto == null) return null;
        return new Incidencia(dto.idIncidencia(), dto.descripcion(), dto.fecha(), dto.estadoIncidencia());
    }

    @Override
    public PagoDTO pagoToDTO(Pago pago) {
        if (pago == null) return null;
        return new PagoDTO(pago.getIdPago(), pago.getMonto(), pago.getFecha(), pago.getMetodoPago(), pago.getResultado());
    }

    @Override
    public Pago dtoToPago(PagoDTO dto) {
        if (dto == null) return null;
        return new Pago(dto.idPago(), dto.monto(), dto.fecha(), dto.metodoPago(), dto.resultado());
    }

    @Override
    public TarifaDTO tarifaToDTO(Tarifa tarifa) {
        if (tarifa == null) return null;
        return new TarifaDTO(tarifa.getIdTarifa(), tarifa.getPrecio());
    }

    @Override
    public Tarifa dtoToTarifa(TarifaDTO dto) {
        if (dto == null) return null;
        return new Tarifa(dto.idTarifa(), dto.precio());
    }

    @Override
    public List<EnvioDTO> listaEnviosToDTO(List<Envio> lista) {
        if (lista == null) return new ArrayList<>();
        return lista.stream().map(this::envioToDTO).toList();
    }

    @Override
    public List<Envio> listaDTOToEnvios(List<EnvioDTO> lista) {
        if (lista == null) return new ArrayList<>();
        return lista.stream().map(this::dtoToEnvio).toList();
    }

    @Override
    public EnvioDTO mapFromEnvio(Envio envio) {
        if (envio == null) return null;

        return new EnvioDTO(
                envio.getIdEnvio(),
                direccionToDTO(envio.getOrigen()),
                direccionToDTO(envio.getDestino()),
                envio.getPeso(),
                envio.getLargo(),
                envio.getAncho(),
                envio.getAlto(),
                envio.getCosto(),
                envio.getFechaCreacion(),
                envio.getFechaEstimadaEntrega(),
                envio.getEstado(),
                envio.getDescripcionRepartidor(),
                envio.getRepartidor() != null ? envio.getRepartidor().getIdRepartidor() : null,
                envio.getUsuario() != null ? envio.getUsuario().getIdUsuario() : null,
                envio.getListaServiciosAdicionales() != null
                        ? envio.getListaServiciosAdicionales().stream().map(this::servicioAdicionalToDTO).toList()
                        : List.of(),
                envio.getListaIncidencias() != null
                        ? envio.getListaIncidencias().stream().map(this::incidenciaToDTO).toList()
                        : List.of()
        );
    }

    @Override
    public AdministradorDTO mapFromAdministrador(Administrador administrador) {
        if (administrador == null) return null;

        return new AdministradorDTO(
                administrador.getIdAdministrador(),
                administrador.getNombre(),
                administrador.getCorreo(),
                administrador.getTelefono()
        );
    }

    @Override
    public UsuarioDTO mapFromUsuario(Usuario usuario) {
        if (usuario == null) return null;

        List<DireccionDTO> direcciones = usuario.getDirecciones() == null
                ? List.of()
                : usuario.getDirecciones().stream()
                .map(this::mapFromDireccion)
                .toList();

        return new UsuarioDTO(
                usuario.getIdUsuario(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getTelefono(),
                direcciones
        );
    }

    private DireccionDTO mapFromDireccion(Direccion direccion) {
        if (direccion == null) return null;
        return new DireccionDTO(
                direccion.getIdDireccion(),
                direccion.getAlias(),
                direccion.getCalle(),
                direccion.getCiudad(),
                direccion.getLatitud(),
                direccion.getLongitud()
        );
    }

    @Override
    public RepartidorDTO mapFromRepartidor(Repartidor repartidor) {
        if (repartidor == null) return null;

        List<String> idsEnvios = repartidor.getEnviosAsignados() == null
                ? List.of()
                : repartidor.getEnviosAsignados().stream()
                .map(envio -> envio.getIdEnvio())
                .toList();

        return new RepartidorDTO(
                repartidor.getCedula(),
                repartidor.getIdRepartidor(),
                repartidor.getNombre(),
                repartidor.getCorreo(),
                repartidor.getTelefono(),
                repartidor.getDisponibilidadRepartidor(),
                repartidor.getZonaCobertura(),
                idsEnvios
        );
    }
}