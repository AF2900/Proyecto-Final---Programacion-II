package co.edu.uniquindio.pr2.proyectofinal.services;

import co.edu.uniquindio.pr2.proyectofinal.mapping.dto.*;
import co.edu.uniquindio.pr2.proyectofinal.model.*;
import java.util.List;

public interface ILogisticaMapping {

    UsuarioDTO usuarioToDTO(Usuario usuario);
    Usuario dtoToUsuario(UsuarioDTO dto);

    RepartidorDTO repartidorToDTO(Repartidor repartidor);
    Repartidor dtoToRepartidor(RepartidorDTO dto);

    AdministradorDTO administradorToDTO(Administrador admin);
    Administrador dtoToAdministrador(AdministradorDTO dto);

    DireccionDTO direccionToDTO(Direccion direccion);
    Direccion dtoToDireccion(DireccionDTO dto);

    EnvioDTO envioToDTO(Envio envio);
    Envio dtoToEnvio(EnvioDTO dto);

    ServicioAdicionalDTO servicioAdicionalToDTO(ServicioAdicional servicio);
    ServicioAdicional dtoToServicioAdicional(ServicioAdicionalDTO dto);

    IncidenciaDTO incidenciaToDTO(Incidencia incidencia);
    Incidencia dtoToIncidencia(IncidenciaDTO dto);

    PagoDTO pagoToDTO(Pago pago);
    Pago dtoToPago(PagoDTO dto);

    TarifaDTO tarifaToDTO(Tarifa tarifa);
    Tarifa dtoToTarifa(TarifaDTO dto);

    List<EnvioDTO> listaEnviosToDTO(List<Envio> lista);
    List<Envio> listaDTOToEnvios(List<EnvioDTO> lista);
    EnvioDTO mapFromEnvio(Envio envio);

    Object mapFromAdministrador(Administrador admin);
    Object mapFromUsuario(Usuario usuario);
    Object mapFromRepartidor(Repartidor repartidor);
}