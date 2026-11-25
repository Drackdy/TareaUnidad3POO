package app;

import java.nio.file.Paths;
import controlador.CatalogoControlador;
import servicio.AlmacenCsv;
import vista.VistaConsola;

/** Punto de entrada de la aplicación organizada con MVC. */
public final class Aplicacion {
    private Aplicacion() { }

    public static void main(String[] args) {
        CatalogoControlador controlador = new CatalogoControlador(
                new VistaConsola(), new AlmacenCsv(), Paths.get("datos"));
        controlador.iniciar();
    }
}
