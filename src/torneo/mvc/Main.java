package torneo.mvc;

import torneo.mvc.controlador.ControladorTorneo;
import torneo.mvc.vista.VistaConsola;

/** Punto de entrada de la aplicacion. */
public final class Main {
    private Main() { }
    public static void main(String[] args) { new ControladorTorneo(new VistaConsola()).iniciar(); }
}
