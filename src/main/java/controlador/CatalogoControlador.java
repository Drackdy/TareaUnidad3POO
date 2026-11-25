package controlador;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import servicio.RepositorioCatalogo;
import uni1a.ContenidoAudiovisual;
import uni1a.Actor;
import uni1a.Documental;
import uni1a.Investigador;
import uni1a.Pelicula;
import uni1a.SerieDeTV;
import uni1a.Temporada;
import vista.VistaConsola;

/** Coordina las acciones del usuario, el catálogo de dominio y el almacenamiento. */
public class CatalogoControlador {
    private final VistaConsola vista;
    private final RepositorioCatalogo almacen;
    private final Path carpetaDatos;
    private final List<ContenidoAudiovisual> catalogo;

    public CatalogoControlador(VistaConsola vista, RepositorioCatalogo almacen, Path carpetaDatos) {
        if (vista == null || almacen == null || carpetaDatos == null) throw new IllegalArgumentException("Las dependencias son obligatorias.");
        this.vista = vista;
        this.almacen = almacen;
        this.carpetaDatos = carpetaDatos;
        this.catalogo = new ArrayList<ContenidoAudiovisual>();
        cargarCatalogo();
    }

    public void iniciar() {
        boolean continuar = true;
        while (continuar) {
            try {
                switch (vista.mostrarMenuYLeerOpcion()) {
                    case 1: vista.mostrarCatalogo(catalogo); break;
                    case 2: registrarPelicula(); break;
                    case 3: registrarSerie(); break;
                    case 4: registrarDocumental(); break;
                    case 5: guardarCatalogo(); break;
                    case 6: cargarCatalogo(); break;
                    case 0: continuar = false; break;
                    default: vista.mostrarMensaje("Opción fuera del menú.");
                }
            } catch (IllegalArgumentException ex) {
                vista.mostrarMensaje("No se pudo completar la acción: " + ex.getMessage());
            }
        }
    }

    private void registrarPelicula() {
        String titulo = vista.leerTexto("Título: ");
        int duracion = vista.leerEntero("Duración en minutos: ");
        String genero = vista.leerTexto("Género: ");
        String estudio = vista.leerTexto("Estudio: ");
        Pelicula pelicula = new Pelicula(titulo, duracion, genero, estudio);
        int cantidadActores = vista.leerEntero("Cantidad de actores: ");
        if (cantidadActores < 0) throw new IllegalArgumentException("La cantidad de actores no puede ser negativa.");
        for (int i = 1; i <= cantidadActores; i++) {
            String nombre = vista.leerTexto("Nombre del actor " + i + ": ");
            String nacionalidad = vista.leerTexto("Nacionalidad del actor " + i + ": ");
            pelicula.agregarActor(new Actor(nombre, nacionalidad));
        }
        catalogo.add(pelicula);
        vista.mostrarMensaje("Película agregada al catálogo.");
    }

    private void registrarSerie() {
        String titulo = vista.leerTexto("Título: ");
        int duracion = vista.leerEntero("Duración por episodio en minutos: ");
        String genero = vista.leerTexto("Género: ");
        int cantidadTemporadas = vista.leerEntero("Cantidad de temporadas: ");
        if (cantidadTemporadas < 0) throw new IllegalArgumentException("La cantidad de temporadas no puede ser negativa.");
        SerieDeTV serie = new SerieDeTV(titulo, duracion, genero, 0);
        for (int i = 1; i <= cantidadTemporadas; i++) {
            int numero = vista.leerEntero("Número de temporada " + i + ": ");
            int episodios = vista.leerEntero("Episodios: ");
            int anio = vista.leerEntero("Año de estreno: ");
            serie.agregarTemporada(new Temporada(numero, episodios, anio));
        }
        catalogo.add(serie);
        vista.mostrarMensaje("Serie agregada al catálogo.");
    }

    private void registrarDocumental() {
        String titulo = vista.leerTexto("Título: ");
        int duracion = vista.leerEntero("Duración en minutos: ");
        String genero = vista.leerTexto("Género: ");
        String tema = vista.leerTexto("Tema: ");
        String nombre = vista.leerTexto("Nombre del investigador: ");
        String institucion = vista.leerTexto("Institución: ");
        String especialidad = vista.leerTexto("Especialidad: ");
        Documental documental = new Documental(titulo, duracion, genero, tema);
        documental.setInvestigador(new Investigador(nombre, institucion, especialidad));
        catalogo.add(documental);
        vista.mostrarMensaje("Documental agregado al catálogo.");
    }

    private void guardarCatalogo() {
        try {
            almacen.guardar(carpetaDatos, catalogo);
            vista.mostrarMensaje("Catálogo guardado en " + carpetaDatos.toString() + ".");
        } catch (IOException ex) {
            vista.mostrarMensaje("Error al guardar el catálogo: " + ex.getMessage());
        }
    }

    private void cargarCatalogo() {
        try {
            List<ContenidoAudiovisual> cargado = almacen.cargar(carpetaDatos);
            catalogo.clear();
            catalogo.addAll(cargado);
            vista.mostrarMensaje("Catálogo cargado. Elementos: " + catalogo.size() + ".");
        } catch (IOException ex) {
            vista.mostrarMensaje("No se pudo cargar el catálogo: " + ex.getMessage());
        }
    }
}
