package torneo.mvc.modelo;

/** Se lanza cuando no es posible construir un emparejamiento consistente. */
public class SorteoInvalidoException extends Exception {
    private static final long serialVersionUID = 1L;
    public SorteoInvalidoException(String mensaje) { super(mensaje); }
}
