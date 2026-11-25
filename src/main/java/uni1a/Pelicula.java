package uni1a;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pelicula extends ContenidoAudiovisual {
    private String estudio;
    private final List<Actor> actores = new ArrayList<Actor>();
    public Pelicula(String titulo, int duracionEnMinutos, String genero, String estudio) {
        super(titulo, duracionEnMinutos, genero); setEstudio(estudio);
    }
    public String getEstudio() { return estudio; }
    public void setEstudio(String estudio) { this.estudio = texto(estudio, "estudio"); }
    public List<Actor> getActores() { return Collections.unmodifiableList(actores); }
    public void agregarActor(Actor actor) { if (actor == null) throw new IllegalArgumentException("Actor requerido."); if (!actores.contains(actor)) actores.add(actor); }
    public boolean quitarActor(Actor actor) { return actores.remove(actor); }
    @Override public String obtenerDetalles() {
        return "Película #" + getId() + ": " + getTitulo() + " | " + getGenero() + " | "
                + getDuracionEnMinutos() + " min | Estudio: " + estudio + " | Actores: " + actores;
    }
}
