package torneo.mvc.vista;

import torneo.mvc.modelo.Partido;
import torneo.mvc.modelo.Torneo;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/** Vista: entrada y salida por consola, sin reglas del torneo. */
public final class VistaConsola {
    private final Scanner teclado = new Scanner(System.in);

    public int mostrarMenu() {
        System.out.println("\n=== Sorteo de liga profesional ===");
        System.out.println("1. Crear torneo y sortear");
        System.out.println("2. Cargar torneo guardado");
        System.out.println("0. Salir");
        System.out.print("Opcion: ");
        try { return Integer.parseInt(teclado.nextLine().trim()); }
        catch (NumberFormatException e) { return -1; }
    }

    public void mostrarTorneo(Torneo torneo) {
        for (Map.Entry<String, List<Partido>> etapa : torneo.getEtapas().entrySet()) {
            System.out.println("\n--- " + etapa.getKey() + " ---");
            int numero = 1;
            for (Partido partido : etapa.getValue()) {
                System.out.printf("Partido %d: %s vs %s | Avanza: %s%n", numero++, partido.getEquipo1(),
                        partido.getEquipo2(), partido.getGanador());
            }
        }
    }

    public void mostrarMensaje(String mensaje) { System.out.println(mensaje); }
    public void cerrar() { teclado.close(); }
}
