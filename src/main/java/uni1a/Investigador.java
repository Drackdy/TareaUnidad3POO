package uni1a;

public class Investigador {
    private String nombre;
    private String institucion;
    private String especialidad;
    public Investigador(String nombre, String institucion, String especialidad) {
        setNombre(nombre); setInstitucion(institucion); setEspecialidad(especialidad);
    }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = ContenidoAudiovisual.texto(nombre, "nombre"); }
    public String getInstitucion() { return institucion; }
    public void setInstitucion(String institucion) { this.institucion = ContenidoAudiovisual.texto(institucion, "institucion"); }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = ContenidoAudiovisual.texto(especialidad, "especialidad"); }
    @Override public String toString() { return nombre + " — " + especialidad + " (" + institucion + ")"; }
}
