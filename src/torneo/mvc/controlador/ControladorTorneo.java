package torneo.mvc.controlador;

import torneo.mvc.modelo.Equipo;
import torneo.mvc.modelo.NumeroEquiposInvalidoException;
import torneo.mvc.modelo.SorteoInvalidoException;
import torneo.mvc.modelo.Torneo;
import torneo.mvc.vista.VistaConsola;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** Controlador: conecta las opciones de la vista con el modelo. */
public final class ControladorTorneo {
    private static final String ARCHIVO = "torneo.ser";
    private final VistaConsola vista;

    public ControladorTorneo(VistaConsola vista) { this.vista = vista; }

    public void iniciar() {
        boolean continuar = true;
        while (continuar) {
            switch (vista.mostrarMenu()) {
                case 1: crearSortearYGuardar(); break;
                case 2: cargarYMostrar(); break;
                case 0: continuar = false; break;
                default: vista.mostrarMensaje("Opcion no valida. Intente nuevamente.");
            }
        }
        vista.cerrar();
    }

    private void crearSortearYGuardar() {
        try {
            Torneo torneo = new Torneo(crearEquipos());
            torneo.sortear();
            vista.mostrarTorneo(torneo);
            torneo.guardar(ARCHIVO);
            vista.mostrarMensaje("Torneo guardado en " + ARCHIVO);
        } catch (NumeroEquiposInvalidoException | SorteoInvalidoException e) {
            vista.mostrarMensaje("No se pudo realizar el sorteo: " + e.getMessage());
        } catch (IOException e) {
            vista.mostrarMensaje("Error al guardar el torneo: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            vista.mostrarMensaje("Datos invalidos: " + e.getMessage());
        }
    }

    private void cargarYMostrar() {
        try {
            Torneo torneo = Torneo.cargar(ARCHIVO);
            vista.mostrarTorneo(torneo);
        } catch (IOException e) {
            vista.mostrarMensaje("No se pudo leer " + ARCHIVO + ": " + e.getMessage());
        } catch (ClassNotFoundException e) {
            vista.mostrarMensaje("No se encontro una clase necesaria para cargar el torneo.");
        }
    }

    private List<Equipo> crearEquipos() {
        String[] nombres = {"Andes FC", "Costa United", "Sierra Azul", "Oriente SC",
                "Capital Deportivo", "Portuario", "Amazonas", "Rayo del Sur",
                "Estrella Roja", "Real del Valle", "Atletico Central", "Union Pacifico",
                "Deportivo Norte", "Leon Dorado", "Juventud FC", "Titanes"};
        List<Equipo> equipos = new ArrayList<>();
        for (String nombre : nombres) equipos.add(new Equipo(nombre));
        return equipos;
    }
}
