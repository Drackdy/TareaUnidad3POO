package vista;

import java.util.List;
import java.util.Scanner;
import uni1a.ContenidoAudiovisual;

/** Presenta opciones y resultados en la consola, sin acceder al almacenamiento. */
public class VistaConsola {
    private final Scanner entrada;

    public VistaConsola() { this(new Scanner(System.in)); }

    VistaConsola(Scanner entrada) { this.entrada = entrada; }

    public int mostrarMenuYLeerOpcion() {
        System.out.println("\n=== Catálogo audiovisual ===");
        System.out.println("1. Mostrar catálogo");
        System.out.println("2. Registrar película");
        System.out.println("3. Registrar serie de TV");
        System.out.println("4. Registrar documental");
        System.out.println("5. Guardar catálogo");
        System.out.println("6. Cargar catálogo");
        System.out.println("0. Salir");
        return leerEntero("Opción: ");
    }

    public String leerTexto(String indicacion) {
        System.out.print(indicacion);
        return entrada.nextLine().trim();
    }

    public int leerEntero(String indicacion) {
        while (true) {
            System.out.print(indicacion);
            String valor = entrada.nextLine().trim();
            try { return Integer.parseInt(valor); }
            catch (NumberFormatException ex) { mostrarMensaje("Ingresa un número entero válido."); }
        }
    }

    public void mostrarCatalogo(List<ContenidoAudiovisual> catalogo) {
        if (catalogo.isEmpty()) {
            mostrarMensaje("El catálogo está vacío.");
            return;
        }
        for (ContenidoAudiovisual contenido : catalogo) System.out.println(contenido.obtenerDetalles());
    }

    public void mostrarMensaje(String mensaje) { System.out.println(mensaje); }
}
