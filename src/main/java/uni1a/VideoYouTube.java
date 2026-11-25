package uni1a;

public class VideoYouTube extends ContenidoAudiovisual {
    private String canal;
    private String url;
    public VideoYouTube(String titulo, int duracionEnMinutos, String genero, String canal, String url) {
        super(titulo, duracionEnMinutos, genero); setCanal(canal); setUrl(url);
    }
    public String getCanal() { return canal; }
    public void setCanal(String canal) { this.canal = texto(canal, "canal"); }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = texto(url, "url"); }
    @Override public String obtenerDetalles() { return "Video de YouTube #" + getId() + ": " + getTitulo() + " | " + getGenero() + " | " + getDuracionEnMinutos() + " min | Canal: " + canal + " | " + url; }
}
