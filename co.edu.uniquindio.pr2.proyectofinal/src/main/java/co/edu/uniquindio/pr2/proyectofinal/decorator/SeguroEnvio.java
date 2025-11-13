package co.edu.uniquindio.pr2.proyectofinal.decorator;

public class SeguroEnvio extends EnvioDecorator {
    public SeguroEnvio(IEnvioDecorator envioDecorado) {
        super(envioDecorado);
    }

    @Override
    public double calcularCosto() {
        return envioDecorado.calcularCosto() + 10000;
    }

    @Override
    public String descripcion() {
        return envioDecorado.descripcion() + " + Seguro";
    }
}