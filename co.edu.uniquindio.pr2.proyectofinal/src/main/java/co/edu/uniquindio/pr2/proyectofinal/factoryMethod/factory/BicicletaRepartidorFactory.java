package co.edu.uniquindio.pr2.proyectofinal.factoryMethod.factory;

import co.edu.uniquindio.pr2.proyectofinal.factoryMethod.model.IRepartidorBase;
import co.edu.uniquindio.pr2.proyectofinal.factoryMethod.model.RepartidorBicicleta;

public class BicicletaRepartidorFactory extends RepartidorFactory {
    @Override
    public IRepartidorBase crearRepartidor() {
        return new RepartidorBicicleta();
    }
}