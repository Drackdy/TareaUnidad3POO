package servicio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uni1a.Actor;
import uni1a.ContenidoAudiovisual;
import uni1a.Cortometraje;
import uni1a.Documental;
import uni1a.Investigador;
import uni1a.Pelicula;
import uni1a.SerieDeTV;
import uni1a.Temporada;
import uni1a.VideoYouTube;

/** Persiste el catálogo audiovisual en archivos CSV separados por entidad. */
public class AlmacenCsv implements RepositorioCatalogo {
    private static final String CONTENIDOS = "contenidos.csv";
    private static final String ACTORES = "actores.csv";
    private static final String TEMPORADAS = "temporadas.csv";
    private static final String INVESTIGADORES = "investigadores.csv";
    private final CsvArchivo csv = new CsvArchivo();

    /** Guarda el catálogo completo en la carpeta indicada, creando los CSV si hace falta. */
    @Override
    public void guardar(Path carpeta, List<ContenidoAudiovisual> catalogo) throws IOException {
        Files.createDirectories(carpeta);
        List<List<String>> contenidos = new ArrayList<List<String>>();
        List<List<String>> actores = new ArrayList<List<String>>();
        List<List<String>> temporadas = new ArrayList<List<String>>();
        List<List<String>> investigadores = new ArrayList<List<String>>();
        contenidos.add(fila("id", "tipo", "titulo", "duracion", "genero", "dato1", "dato2"));
        actores.add(fila("contenido_id", "nombre", "nacionalidad"));
        temporadas.add(fila("serie_id", "numero", "episodios", "anio"));
        investigadores.add(fila("documental_id", "nombre", "institucion", "especialidad"));

        for (ContenidoAudiovisual contenido : catalogo) {
            String tipo;
            String dato1 = "";
            String dato2 = "";
            if (contenido instanceof Pelicula) {
                Pelicula pelicula = (Pelicula) contenido;
                tipo = "PELICULA";
                dato1 = pelicula.getEstudio();
                for (Actor actor : pelicula.getActores()) {
                    actores.add(fila(id(contenido), actor.getNombre(), actor.getNacionalidad()));
                }
            } else if (contenido instanceof SerieDeTV) {
                tipo = "SERIE";
                for (Temporada temporada : ((SerieDeTV) contenido).getListaTemporadas()) {
                    temporadas.add(fila(id(contenido), numero(temporada.getNumero()),
                            numero(temporada.getEpisodios()), numero(temporada.getAnioEstreno())));
                }
            } else if (contenido instanceof Documental) {
                Documental documental = (Documental) contenido;
                tipo = "DOCUMENTAL";
                dato1 = documental.getTema();
                Investigador investigador = documental.getInvestigador();
                if (investigador != null) {
                    investigadores.add(fila(id(contenido), investigador.getNombre(),
                            investigador.getInstitucion(), investigador.getEspecialidad()));
                }
            } else if (contenido instanceof VideoYouTube) {
                VideoYouTube video = (VideoYouTube) contenido;
                tipo = "YOUTUBE";
                dato1 = video.getCanal();
                dato2 = video.getUrl();
            } else if (contenido instanceof Cortometraje) {
                Cortometraje corto = (Cortometraje) contenido;
                tipo = "CORTOMETRAJE";
                dato1 = corto.getDirector();
                dato2 = numero(corto.getAnio());
            } else {
                throw new IOException("Tipo de contenido no soportado: " + contenido.getClass().getName());
            }
            contenidos.add(fila(id(contenido), tipo, contenido.getTitulo(),
                    numero(contenido.getDuracionEnMinutos()), contenido.getGenero(), dato1, dato2));
        }

        csv.escribir(carpeta.resolve(CONTENIDOS), contenidos);
        csv.escribir(carpeta.resolve(ACTORES), actores);
        csv.escribir(carpeta.resolve(TEMPORADAS), temporadas);
        csv.escribir(carpeta.resolve(INVESTIGADORES), investigadores);
    }

    /** Carga los datos CSV y reconstruye sus relaciones dentro de los objetos. */
    @Override
    public List<ContenidoAudiovisual> cargar(Path carpeta) throws IOException {
        List<ContenidoAudiovisual> catalogo = new ArrayList<ContenidoAudiovisual>();
        Map<String, Pelicula> peliculas = new HashMap<String, Pelicula>();
        Map<String, SerieDeTV> series = new HashMap<String, SerieDeTV>();
        Map<String, Documental> documentales = new HashMap<String, Documental>();

        for (List<String> fila : csv.leer(carpeta.resolve(CONTENIDOS))) {
            exigirColumnas(fila, 7, CONTENIDOS);
            String id = fila.get(0);
            String tipo = fila.get(1);
            String titulo = fila.get(2);
            int duracion = entero(fila.get(3), CONTENIDOS);
            String genero = fila.get(4);
            ContenidoAudiovisual contenido;
            if ("PELICULA".equals(tipo)) {
                Pelicula pelicula = new Pelicula(titulo, duracion, genero, fila.get(5));
                peliculas.put(id, pelicula);
                contenido = pelicula;
            } else if ("SERIE".equals(tipo)) {
                SerieDeTV serie = new SerieDeTV(titulo, duracion, genero, 0);
                series.put(id, serie);
                contenido = serie;
            } else if ("DOCUMENTAL".equals(tipo)) {
                Documental documental = new Documental(titulo, duracion, genero, fila.get(5));
                documentales.put(id, documental);
                contenido = documental;
            } else if ("YOUTUBE".equals(tipo)) {
                contenido = new VideoYouTube(titulo, duracion, genero, fila.get(5), fila.get(6));
            } else if ("CORTOMETRAJE".equals(tipo)) {
                contenido = new Cortometraje(titulo, duracion, genero, fila.get(5), entero(fila.get(6), CONTENIDOS));
            } else {
                throw new IOException("Tipo de contenido desconocido en " + CONTENIDOS + ": " + tipo);
            }
            catalogo.add(contenido);
        }

        for (List<String> fila : csv.leer(carpeta.resolve(ACTORES))) {
            exigirColumnas(fila, 3, ACTORES);
            Pelicula pelicula = peliculas.get(fila.get(0));
            if (pelicula == null) throw new IOException("Actor asociado a una película inexistente: " + fila.get(0));
            pelicula.agregarActor(new Actor(fila.get(1), fila.get(2)));
        }
        for (List<String> fila : csv.leer(carpeta.resolve(TEMPORADAS))) {
            exigirColumnas(fila, 4, TEMPORADAS);
            SerieDeTV serie = series.get(fila.get(0));
            if (serie == null) throw new IOException("Temporada asociada a una serie inexistente: " + fila.get(0));
            serie.agregarTemporada(new Temporada(entero(fila.get(1), TEMPORADAS),
                    entero(fila.get(2), TEMPORADAS), entero(fila.get(3), TEMPORADAS)));
        }
        for (List<String> fila : csv.leer(carpeta.resolve(INVESTIGADORES))) {
            exigirColumnas(fila, 4, INVESTIGADORES);
            Documental documental = documentales.get(fila.get(0));
            if (documental == null) throw new IOException("Investigador asociado a un documental inexistente: " + fila.get(0));
            documental.setInvestigador(new Investigador(fila.get(1), fila.get(2), fila.get(3)));
        }
        return catalogo;
    }

    private static String id(ContenidoAudiovisual contenido) { return Integer.toString(contenido.getId()); }
    private static String numero(int valor) { return Integer.toString(valor); }
    private static List<String> fila(String... valores) {
        List<String> resultado = new ArrayList<String>();
        for (String valor : valores) resultado.add(valor == null ? "" : valor);
        return resultado;
    }
    private static int entero(String valor, String archivo) throws IOException {
        try { return Integer.parseInt(valor); }
        catch (NumberFormatException ex) { throw new IOException("Número inválido en " + archivo + ": " + valor, ex); }
    }
    private static void exigirColumnas(List<String> fila, int cantidad, String archivo) throws IOException {
        if (fila.size() != cantidad) throw new IOException("Fila con cantidad de columnas inválida en " + archivo + ".");
    }
}
