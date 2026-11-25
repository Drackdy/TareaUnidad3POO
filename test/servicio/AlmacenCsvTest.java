package servicio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import uni1a.Actor;
import uni1a.ContenidoAudiovisual;
import uni1a.Documental;
import uni1a.Investigador;
import uni1a.Pelicula;
import uni1a.SerieDeTV;
import uni1a.Temporada;

public class AlmacenCsvTest {
    @Rule public TemporaryFolder carpetaTemporal = new TemporaryFolder();

    @Test
    public void guardaYCargaContenidosYRelaciones() throws Exception {
        Path carpeta = carpetaTemporal.newFolder("catalogo").toPath();
        List<ContenidoAudiovisual> originales = new ArrayList<ContenidoAudiovisual>();
        String tituloConFormato = "Título, con coma\nsegunda línea";
        Pelicula pelicula = new Pelicula(tituloConFormato, 120, "Drama", "Estudio \"A\"");
        pelicula.agregarActor(new Actor("Actor Uno", "Ecuatoriana"));
        SerieDeTV serie = new SerieDeTV("Serie", 40, "Ciencia", 0);
        serie.agregarTemporada(new Temporada(1, 6, 2025));
        Documental documental = new Documental("Documental", 55, "Naturaleza", "Bosques");
        documental.setInvestigador(new Investigador("Investigadora", "UPS", "Ecología"));
        originales.add(pelicula);
        originales.add(serie);
        originales.add(documental);

        AlmacenCsv almacen = new AlmacenCsv();
        almacen.guardar(carpeta, originales);
        List<ContenidoAudiovisual> cargados = almacen.cargar(carpeta);

        assertEquals(3, cargados.size());
        Pelicula peliculaCargada = (Pelicula) cargados.get(0);
        assertEquals(tituloConFormato, peliculaCargada.getTitulo());
        assertEquals("Estudio \"A\"", peliculaCargada.getEstudio());
        assertEquals(1, peliculaCargada.getActores().size());
        assertEquals(1, ((SerieDeTV) cargados.get(1)).getTemporadas());
        assertEquals("Investigadora", ((Documental) cargados.get(2)).getInvestigador().getNombre());
        assertTrue(carpetaTemporal.getRoot().list().length > 0);
    }
}
