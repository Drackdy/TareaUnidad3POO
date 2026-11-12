package torneo.mvc.modelo;

import java.io.Serializable;
import java.util.Objects;

/** Representa un equipo participante. */
public final class Equipo implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String nombre;

    public Equipo(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del equipo no puede estar vacio.");
        }
        this.nombre = nombre.trim();
    }

    public String getNombre() { return nombre; }

    @Override public String toString() { return nombre; }
    @Override public boolean equals(Object otro) {
        return otro instanceof Equipo && nombre.equalsIgnoreCase(((Equipo) otro).nombre);
    }
    @Override public int hashCode() { return Objects.hash(nombre.toLowerCase()); }
}
