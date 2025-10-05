package co.edu.uniquindio.pr2.proyectofinal.services;

import co.edu.uniquindio.pr2.proyectofinal.model.Envio;
import co.edu.uniquindio.pr2.proyectofinal.model.Repartidor;

public interface GestionEnvios {
    void asignarEnvioARepartidor(Repartidor repartidor, Envio envio);
    void eliminarEnvioDeRepartidor(Repartidor repartidor, Envio envio);
    double calcularCostoEnvio(Envio envio);
    double calcularVolumen(Envio envio);
}