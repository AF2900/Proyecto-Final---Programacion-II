package co.edu.uniquindio.pr2.proyectofinal.controller;

import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import co.edu.uniquindio.pr2.proyectofinal.model.DisponibilidadRepartidor;
import co.edu.uniquindio.pr2.proyectofinal.model.Repartidor;
import co.edu.uniquindio.pr2.proyectofinal.builder.RepartidorBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RepartidorController {

    private final ModelFactory modelFactory = ModelFactory.getInstance();
    private ObservableList<Repartidor> listaRepartidores;

    public ObservableList<Repartidor> getListaRepartidores() {
        if (listaRepartidores == null) {
            listaRepartidores = FXCollections.observableArrayList(modelFactory.getEmpresaLogistica().getRepartidores());
        }
        return listaRepartidores;
    }

    public boolean camposValidos(String id, String nombre, String cedula, String telefono, String password, DisponibilidadRepartidor disponibilidad) {
        return !(id.isEmpty() || nombre.isEmpty() || cedula.isEmpty() || telefono.isEmpty() || password.isEmpty() || disponibilidad == null);
    }

    public Repartidor buscarRepartidorPorId(String id) {
        return modelFactory.getEmpresaLogistica().getRepartidores().stream()
                .filter(r -> r.getIdRepartidor().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    public void agregarRepartidor(String id, String nombre, String cedula, String telefono, String password, DisponibilidadRepartidor disponibilidad) {
        Repartidor nuevo = new RepartidorBuilder()
                .idRepartidor(id)
                .nombre(nombre)
                .cedula(cedula)
                .telefono(telefono)
                .password(password)
                .disponibilidadRepartidor(disponibilidad)
                .zonaCobertura("No definida")
                .build();

        modelFactory.getEmpresaLogistica().getRepartidores().add(nuevo);
        listaRepartidores.add(nuevo);
    }

    public void actualizarRepartidor(Repartidor repartidor, String nombre, String cedula, String telefono, String password, DisponibilidadRepartidor disponibilidad) {
        repartidor.setNombre(nombre);
        repartidor.setCedula(cedula);
        repartidor.setTelefono(telefono);
        repartidor.setPassword(password);
        repartidor.setDisponibilidadRepartidor(disponibilidad);
    }

    public void eliminarRepartidor(Repartidor repartidor) {
        modelFactory.getEmpresaLogistica().getRepartidores().remove(repartidor);
        listaRepartidores.remove(repartidor);
    }
}