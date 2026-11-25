package poo;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.nio.file.Paths;
import servicio.AlmacenCsv;
import uni1a.*;

/** Ejecuta un recorrido demostrativo de todas las clases y relaciones del modelo. */
public class PruebaAudioVisual {
    public static void main(String[] args) {
        Actor actor1 = new Actor("Zoe Saldaña", "Estadounidense");
        Actor actor2 = new Actor("Sam Worthington", "Australiano");
        Pelicula pelicula = new Pelicula("Avatar", 162, "Ciencia ficción", "20th Century Studios");
        pelicula.agregarActor(actor1);
        pelicula.agregarActor(actor2);
        pelicula.quitarActor(actor2); // demuestra manipulación de la agregación
        pelicula.agregarActor(actor2);

        SerieDeTV serie = new SerieDeTV("Planeta Tierra", 50, "Naturaleza", 1);
        serie.agregarTemporada(new Temporada(2, 6, 2016));
        serie.quitarTemporada(1); // temporadas pertenecen al ciclo de vida de la serie

        Investigador investigadora = new Investigador("Dra. Sylvia Earle", "Oceanógrafa", "Biología marina");
        Documental documental = new Documental("Océano profundo", 52, "Ciencia", "Ecosistemas marinos");
        documental.setInvestigador(investigadora);

        VideoYouTube video = new VideoYouTube("Introducción a Java", 12, "Educación", "Aula POO", "https://youtube.com/");
        Cortometraje corto = new Cortometraje("La última carta", 18, "Drama", "Lucía Torres", 2024);

        List<ContenidoAudiovisual> catalogo = new ArrayList<ContenidoAudiovisual>();
        catalogo.add(pelicula); catalogo.add(serie); catalogo.add(documental); catalogo.add(video); catalogo.add(corto);
        System.out.println("=== Catálogo audiovisual de demostración ===");
        for (ContenidoAudiovisual contenido : catalogo) System.out.println(contenido.obtenerDetalles());
        System.out.println("Total de contenidos: " + catalogo.size());

        try {
            AlmacenCsv almacen = new AlmacenCsv();
            almacen.guardar(Paths.get("datos"), catalogo);
            List<ContenidoAudiovisual> catalogoCargado = almacen.cargar(Paths.get("datos"));
            System.out.println("Catálogo guardado y cargado desde CSV. Total recuperado: " + catalogoCargado.size());
        } catch (IOException ex) {
            System.err.println("No se pudo guardar o cargar el catálogo: " + ex.getMessage());
        }
    }
}
