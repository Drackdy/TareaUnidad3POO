package torneo.mvc.modelo;

import java.io.Serializable;

/** Un enfrentamiento de una etapa y el equipo que avanza. */
public final class Partido implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Equipo equipo1;
    private final Equipo equipo2;
    private final Equipo ganador;

    public Partido(Equipo equipo1, Equipo equipo2, Equipo ganador) {
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
        this.ganador = ganador;
    }

    public Equipo getEquipo1() { return equipo1; }
    public Equipo getEquipo2() { return equipo2; }
    public Equipo getGanador() { return ganador; }
}
