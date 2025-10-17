package co.edu.uniquindio.pr2.proyectofinal.model;

import co.edu.uniquindio.pr2.proyectofinal.builder.*;
import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import co.edu.uniquindio.pr2.proyectofinal.services.IGestionEnvios;
import co.edu.uniquindio.pr2.proyectofinal.services.IGestionUsuarios;
import co.edu.uniquindio.pr2.proyectofinal.services.IGestionPagos;
import java.util.ArrayList;
import java.util.List;

public class EmpresaLogistica implements IGestionEnvios, IGestionUsuarios, IGestionPagos {
    private final List<Usuario> usuarios = new ArrayList<>();
    private final List<Administrador> administradores = new ArrayList<>();
    private final List<Repartidor> repartidores = new ArrayList<>();
    private final List<Envio> envios = new ArrayList<>();
    private final List<Pago> pagos = new ArrayList<>();
    private final List<Tarifa> tarifas = new ArrayList<>();
    private final List<Incidencia> incidencias = new ArrayList<>();
    private final List<ServicioAdicional> serviciosAdicionales = new ArrayList<>();

    public EmpresaLogistica() {}

    public Usuario buscarUsuarioPorCorreo(String correo) {
        if (correo == null || correo.isEmpty()) return null;
        return usuarios.stream()
                .filter(u -> correo.equalsIgnoreCase(u.getCorreo()))
                .findFirst()
                .orElse(null);
    }

    public Administrador buscarAdministradorPorCorreo(String correo) {
        if (correo == null || correo.isEmpty()) return null;
        return administradores.stream()
                .filter(a -> correo.equalsIgnoreCase(a.getCorreo()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void asignarEnvioARepartidor(Repartidor repartidor, Envio envio) {
        if (repartidor != null && envio != null) {
            repartidor.getEnviosAsignados().add(envio);
            envio.setRepartidor(repartidor);
            envios.add(envio);
        }
    }

    @Override
    public void eliminarEnvioDeRepartidor(Repartidor repartidor, Envio envio) {
        if (repartidor != null && envio != null) {
            repartidor.getEnviosAsignados().remove(envio);
            envios.remove(envio);
        }
    }

    @Override
    public double calcularVolumen(Envio envio) {
        if (envio == null) return 0.0;
        return envio.getLargo() * envio.getAncho() * envio.getAlto();
    }

    @Override
    public double calcularCostoEnvio(Envio envio) {
        if (envio == null) return 0.0;
        double base = 500;
        double costoPeso = envio.getPeso() * 1500;
        double costoVolumen = calcularVolumen(envio) * 3000;
        double total = base + costoPeso + costoVolumen;
        envio.setCosto(Math.round(total * 100) / 100.0);
        return envio.getCosto();
    }

    @Override
    public boolean esPagoAprobado(Pago pago) {
        return pago != null && "APROBADO".equalsIgnoreCase(pago.getResultado());
    }

    @Override
    public void validarPago(Pago pago) {
        if (pago != null) {
            pago.setResultado(pago.getMonto() > 0 ? "APROBADO" : "RECHAZADO");
        }
    }

    @Override
    public void agregarDireccionAUsuario(Usuario usuario, Direccion direccion) {
        if (usuario != null && direccion != null && !usuario.getDirecciones().contains(direccion)) {
            usuario.getDirecciones().add(direccion);
        }
    }

    @Override
    public void eliminarDireccionDeUsuario(Usuario usuario, Direccion direccion) {
        if (usuario != null && direccion != null) {
            usuario.getDirecciones().remove(direccion);
        }
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public List<Administrador> getAdministradores() {
        return administradores;
    }

    public List<Repartidor> getRepartidores() {
        return repartidores;
    }

    public List<Envio> getEnvios() {
        return envios;
    }

    public List<Pago> getPagos() {
        return pagos;
    }

    public List<Tarifa> getTarifas() {
        return tarifas;
    }

    public List<Incidencia> getIncidencias() {
        return incidencias;
    }

    public List<ServicioAdicional> getServiciosAdicionales() {
        return serviciosAdicionales;
    }

    public void agregarUsuario(Usuario usuario) {
        if (usuario != null && buscarUsuarioPorCorreo(usuario.getCorreo()) == null) {
            usuarios.add(new UsuarioBuilder().from(usuario).build());
        }
    }

    public void agregarAdministrador(Administrador admin) {
        if (admin != null) {
            administradores.add(new AdministradorBuilder().from(admin).build());
        }
    }

    public void agregarRepartidor(Repartidor repartidor) {
        if (repartidor != null) {
            repartidores.add(new RepartidorBuilder().from(repartidor).build());
        }
    }

    public void agregarEnvio(Envio envio) {
        if (envio != null) {
            envios.add(new EnvioBuilder().from(envio).build());
        }
    }

    public void agregarPago(Pago pago) {
        if (pago != null) {
            pagos.add(new PagoBuilder().from(pago).build());
        }
    }

    public void agregarTarifa(Tarifa tarifa) {
        if (tarifa != null) {
            tarifas.add(new TarifaBuilder().from(tarifa).build());
        }
    }

    public void agregarIncidencia(Incidencia incidencia) {
        if (incidencia != null) {
            incidencias.add(new IncidenciaBuilder().from(incidencia).build());
        }
    }

    public void agregarServicioAdicional(ServicioAdicional servicio) {
        if (servicio != null) {
            serviciosAdicionales.add(new ServicioAdicionalBuilder().from(servicio).build());
        }
    }

    public double calcularTarifa(String origen, String destino, double peso, double volumen, String prioridad) {
        double base = 5000;
        double costoPeso = peso * 1000;
        double costoVolumen = volumen * 0.5;
        double recargoPrioridad = switch (prioridad) {
            case "Alta" -> 1.2;
            case "Urgente" -> 1.5;
            default -> 1.0;
        };
        return Math.round((base + costoPeso + costoVolumen) * recargoPrioridad * 100) / 100.0;
    }

    public boolean crearEnvio(String origenTexto,
                              String destinoTexto,
                              double peso, double volumen,
                              String prioridad,
                              boolean seguro,
                              boolean fragil,
                              boolean firma) {
        try {
            Direccion origen = new DireccionBuilder()
                    .idDireccion(String.format("%05d", (int)(Math.random() * 100000)))
                    .alias("Origen")
                    .calle(origenTexto)
                    .ciudad("CiudadX")
                    .build();

            Direccion destino = new DireccionBuilder()
                    .idDireccion(String.format("%05d", (int)(Math.random() * 100000)))
                    .alias("Destino")
                    .calle(destinoTexto)
                    .ciudad("CiudadY")
                    .build();

            EstadoEnvio estadoInicial;
            if ("Urgente".equalsIgnoreCase(prioridad)) {
                estadoInicial = EstadoEnvio.ENTREGADO;
            } else if ("Rápida".equalsIgnoreCase(prioridad)) {
                estadoInicial = EstadoEnvio.EN_CAMINO;
            } else {
                estadoInicial = EstadoEnvio.PENDIENTE;
            }

            Envio envio = new EnvioBuilder()
                    .idEnvio(String.format("%05d", (int)(Math.random() * 100000)))
                    .origen(origen)
                    .destino(destino)
                    .peso(peso)
                    .largo(1)
                    .ancho(1)
                    .alto(1)
                    .fechaCreacion(java.time.LocalDate.now())
                    .fechaEstimadaEntrega(java.time.LocalDate.now().plusDays(2))
                    .estado(estadoInicial)
                    .build();

            if (seguro) envio.getListaServiciosAdicionales().add(new ServicioAdicionalBuilder()
                    .idServicioAdd(String.format("%05d", (int)(Math.random() * 100000)))
                    .tipoServicio("Seguro")
                    .costoServicioAdd(3000)
                    .build());

            if (fragil) envio.getListaServiciosAdicionales().add(new ServicioAdicionalBuilder()
                    .idServicioAdd(String.format("%05d", (int)(Math.random() * 100000)))
                    .tipoServicio("Frágil")
                    .costoServicioAdd(2000)
                    .build());

            if (firma) envio.getListaServiciosAdicionales().add(new ServicioAdicionalBuilder()
                    .idServicioAdd(String.format("%05d", (int)(Math.random() * 100000)))
                    .tipoServicio("Firma requerida")
                    .costoServicioAdd(1500)
                    .build());

            agregarEnvio(envio);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String consultarEstadoEnvio(String codigo) {
        return envios.stream()
                .filter(e -> e.getIdEnvio().equalsIgnoreCase(codigo))
                .map(e -> e.getEstado().name())
                .findFirst()
                .orElse("No encontrado");
    }
}