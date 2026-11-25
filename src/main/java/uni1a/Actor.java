package uni1a;

public class Actor {
    private String nombre;
    private String nacionalidad;
    public Actor(String nombre, String nacionalidad) { setNombre(nombre); setNacionalidad(nacionalidad); }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = ContenidoAudiovisual.texto(nombre, "nombre"); }
    public String getNacionalidad() { return nacionalidad; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = ContenidoAudiovisual.texto(nacionalidad, "nacionalidad"); }
    @Override public String toString() { return nombre + " (" + nacionalidad + ")"; }
}
