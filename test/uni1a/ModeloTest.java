package uni1a;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class ModeloTest {
    @Test
    public void peliculaIncluyeSusActoresEnLosDetalles() {
        Pelicula pelicula = new Pelicula("La película", 100, "Drama", "Estudio Uno");
        pelicula.agregarActor(new Actor("Ana Pérez", "Ecuatoriana"));
        assertTrue(pelicula.obtenerDetalles().contains("Ana Pérez"));
    }

    @Test
    public void seriePermiteAgregarYQuitarTemporadas() {
        SerieDeTV serie = new SerieDeTV("Serie", 45, "Drama", 0);
        serie.agregarTemporada(new Temporada(1, 8, 2024));
        assertEquals(1, serie.getTemporadas());
        assertTrue(serie.quitarTemporada(1));
        assertEquals(0, serie.getTemporadas());
    }

    @Test
    public void documentalMuestraInvestigadorAsignado() {
        Documental documental = new Documental("Mar", 50, "Ciencia", "Océanos");
        documental.setInvestigador(new Investigador("Eva Mar", "UPS", "Biología"));
        assertTrue(documental.obtenerDetalles().contains("Eva Mar"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void contenidoRechazaDuracionNoPositiva() {
        new Pelicula("Inválida", 0, "Drama", "Estudio");
    }

    @Test(expected = IllegalArgumentException.class)
    public void actorRechazaNombreVacio() {
        new Actor("  ", "Ecuatoriana");
    }

    @Test
    public void contenidosEspecializadosConservanSusDatos() {
        VideoYouTube video = new VideoYouTube("Curso", 12, "Educación", "Canal UPS", "https://example.com");
        Cortometraje corto = new Cortometraje("Relato", 15, "Drama", "Ana Ruiz", 2024);
        assertTrue(video.obtenerDetalles().contains("Canal UPS"));
        assertTrue(corto.obtenerDetalles().contains("Ana Ruiz"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void serieRechazaNumeroDeTemporadaDuplicado() {
        SerieDeTV serie = new SerieDeTV("Serie", 45, "Drama", 0);
        serie.agregarTemporada(new Temporada(1, 8, 2024));
        serie.agregarTemporada(new Temporada(1, 10, 2025));
    }
}
