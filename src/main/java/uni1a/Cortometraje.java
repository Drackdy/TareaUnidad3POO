package uni1a;

public class Cortometraje extends ContenidoAudiovisual {
    private String director;
    private int anio;
    public Cortometraje(String titulo, int duracionEnMinutos, String genero, String director, int anio) {
        super(titulo, duracionEnMinutos, genero); setDirector(director); setAnio(anio);
    }
    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = texto(director, "director"); }
    public int getAnio() { return anio; }
    public void setAnio(int anio) { if (anio < 1888) throw new IllegalArgumentException("Año inválido."); this.anio = anio; }
    @Override public String obtenerDetalles() { return "Cortometraje #" + getId() + ": " + getTitulo() + " | " + getGenero() + " | " + getDuracionEnMinutos() + " min | Director: " + director + " | Año: " + anio; }
}
