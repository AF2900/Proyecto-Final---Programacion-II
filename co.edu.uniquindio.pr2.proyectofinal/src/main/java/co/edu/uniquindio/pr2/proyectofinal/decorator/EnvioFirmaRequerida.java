package co.edu.uniquindio.pr2.proyectofinal.decorator;

public class EnvioFirmaRequerida extends EnvioDecorator {
    public EnvioFirmaRequerida(IEnvioDecorator envioDecorado) {
        super(envioDecorado);
    }

    @Override
    public double calcularCosto() {
        return envioDecorado.calcularCosto() + 3000;
    }

    @Override
    public String descripcion() {
        return envioDecorado.descripcion() + " + Firma Requerida";
    }
}