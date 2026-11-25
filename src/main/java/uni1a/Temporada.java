package uni1a;

public class Temporada {
    private final int numero;
    private int episodios;
    private int anioEstreno;
    public Temporada(int numero, int episodios, int anioEstreno) {
        if (numero < 1 || episodios < 1 || anioEstreno < 1888) throw new IllegalArgumentException("Datos de temporada inválidos.");
        this.numero = numero; setEpisodios(episodios); setAnioEstreno(anioEstreno);
    }
    public int getNumero() { return numero; }
    public int getEpisodios() { return episodios; }
    public void setEpisodios(int episodios) { if (episodios < 1) throw new IllegalArgumentException("Debe existir al menos un episodio."); this.episodios = episodios; }
    public int getAnioEstreno() { return anioEstreno; }
    public void setAnioEstreno(int anio) { if (anio < 1888) throw new IllegalArgumentException("Año inválido."); this.anioEstreno = anio; }
    @Override public String toString() { return "Temporada " + numero + " (" + episodios + " episodios, " + anioEstreno + ")"; }
}
