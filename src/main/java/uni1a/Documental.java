package uni1a;

public class Documental extends ContenidoAudiovisual {
    private String tema;
    private Investigador investigador;
    public Documental(String titulo, int duracionEnMinutos, String genero, String tema) {
        super(titulo, duracionEnMinutos, genero); setTema(tema);
    }
    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = texto(tema, "tema"); }
    public Investigador getInvestigador() { return investigador; }
    public void setInvestigador(Investigador investigador) { if (investigador == null) throw new IllegalArgumentException("Investigador requerido."); this.investigador = investigador; }
    @Override public String obtenerDetalles() {
        return "Documental #" + getId() + ": " + getTitulo() + " | " + getGenero() + " | "
                + getDuracionEnMinutos() + " min | Tema: " + tema + " | Investigador: "
                + (investigador == null ? "sin asignar" : investigador);
    }
}
