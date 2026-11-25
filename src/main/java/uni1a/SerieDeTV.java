package uni1a;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SerieDeTV extends ContenidoAudiovisual {
    private final List<Temporada> temporadas = new ArrayList<Temporada>();
    public SerieDeTV(String titulo, int duracionEnMinutos, String genero, int cantidadTemporadas) {
        super(titulo, duracionEnMinutos, genero);
        if (cantidadTemporadas < 0) throw new IllegalArgumentException("La cantidad no puede ser negativa.");
        for (int i = 1; i <= cantidadTemporadas; i++) temporadas.add(new Temporada(i, 10, 2020));
    }
    public List<Temporada> getListaTemporadas() { return Collections.unmodifiableList(temporadas); }
    public int getTemporadas() { return temporadas.size(); }
    public void agregarTemporada(Temporada temporada) {
        if (temporada == null) throw new IllegalArgumentException("Temporada requerida.");
        for (Temporada existente : temporadas) if (existente.getNumero() == temporada.getNumero()) throw new IllegalArgumentException("El número de temporada ya existe.");
        temporadas.add(temporada);
    }
    public boolean quitarTemporada(int numero) { return temporadas.removeIf(t -> t.getNumero() == numero); }
    @Override public String obtenerDetalles() {
        return "Serie #" + getId() + ": " + getTitulo() + " | " + getGenero() + " | "
                + getDuracionEnMinutos() + " min por episodio | Temporadas: " + temporadas;
    }
}
