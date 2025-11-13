package co.edu.uniquindio.pr2.proyectofinal.decorator;

public class EnvioBase implements IEnvioDecorator {
    private final double costoBase;

    public EnvioBase(double costoBase) {
        this.costoBase = costoBase;
    }

    @Override
    public double calcularCosto() {
        return costoBase;
    }

    @Override
    public String descripcion() {
        return "Envío base";
    }
}