package torneo.mvc.modelo;

/** Se lanza cuando la cantidad de participantes no permite un cuadro valido. */
public class NumeroEquiposInvalidoException extends Exception {
    private static final long serialVersionUID = 1L;
    public NumeroEquiposInvalidoException(String mensaje) { super(mensaje); }
}
