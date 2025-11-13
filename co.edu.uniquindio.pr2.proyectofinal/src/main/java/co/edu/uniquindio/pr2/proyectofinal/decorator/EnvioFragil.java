package co.edu.uniquindio.pr2.proyectofinal.decorator;

public class EnvioFragil extends EnvioDecorator {
    public EnvioFragil(IEnvioDecorator envioDecorado) {
        super(envioDecorado);
    }

    @Override
    public double calcularCosto() {
        return envioDecorado.calcularCosto() + 5000;
    }

    @Override
    public String descripcion() {
        return envioDecorado.descripcion() + " + Frágil";
    }
}