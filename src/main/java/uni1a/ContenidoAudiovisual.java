package uni1a;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class ContenidoAudiovisual {
    private static final AtomicInteger CONTADOR = new AtomicInteger(1);
    private final int id;
    private String titulo;
    private int duracionEnMinutos;
    private String genero;

    protected ContenidoAudiovisual(String titulo, int duracionEnMinutos, String genero) {
        this.id = CONTADOR.getAndIncrement();
        setTitulo(titulo);
        setDuracionEnMinutos(duracionEnMinutos);
        setGenero(genero);
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = texto(titulo, "titulo"); }
    public int getDuracionEnMinutos() { return duracionEnMinutos; }
    public void setDuracionEnMinutos(int minutos) {
        if (minutos <= 0) throw new IllegalArgumentException("La duración debe ser positiva.");
        this.duracionEnMinutos = minutos;
    }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = texto(genero, "genero"); }
    protected static String texto(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) throw new IllegalArgumentException(campo + " no puede estar vacío.");
        return valor.trim();
    }
    /** Devuelve una representación legible del contenido sin imprimir desde el modelo. */
    public abstract String obtenerDetalles();
}
