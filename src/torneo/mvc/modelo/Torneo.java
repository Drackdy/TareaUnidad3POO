package torneo.mvc.modelo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/** Modelo del torneo: reglas, sorteo recursivo y persistencia. */
public final class Torneo implements Serializable {
    private static final long serialVersionUID = 1L;
    private final List<Equipo> equipos;
    private final Map<String, List<Partido>> etapas = new LinkedHashMap<>();

    public Torneo(List<Equipo> equipos) { this.equipos = new ArrayList<>(equipos); }
    public List<Equipo> getEquipos() { return Collections.unmodifiableList(equipos); }
    public Map<String, List<Partido>> getEtapas() { return Collections.unmodifiableMap(etapas); }

    public void sortear() throws NumeroEquiposInvalidoException, SorteoInvalidoException {
        validarEquipos(equipos);
        etapas.clear();
        sortearEtapa(new ArrayList<>(equipos), 0);
    }

    private void sortearEtapa(List<Equipo> participantes, int indiceEtapa)
            throws NumeroEquiposInvalidoException, SorteoInvalidoException {
    	if (participantes.size() == 1) return;
    	validarEquipos(participantes);

        String[] nombresEtapas = {"Octavos de final", "Cuartos de final", "Semifinales", "Final"};
        if (indiceEtapa >= nombresEtapas.length) {
            throw new SorteoInvalidoException("El cuadro excede las etapas previstas.");
        }
        List<Equipo> mezclados = new ArrayList<>(participantes);
        Collections.shuffle(mezclados);
        List<Partido> partidos = new ArrayList<>();
        emparejarRecursivo(mezclados, 0, partidos);
        etapas.put(nombresEtapas[indiceEtapa], partidos);

        List<Equipo> ganadores = new ArrayList<>();
        for (Partido partido : partidos) ganadores.add(partido.getGanador());
        sortearEtapa(ganadores, indiceEtapa + 1);
    }

    /** Construye cada ronda de partidos mediante llamadas recursivas. */
    private void emparejarRecursivo(List<Equipo> equiposMezclados, int indice, List<Partido> salida)
            throws SorteoInvalidoException {
        if (indice == equiposMezclados.size()) return;
        if (indice + 1 >= equiposMezclados.size()) {
            throw new SorteoInvalidoException("Quedo un equipo sin oponente.");
        }
        Equipo primero = equiposMezclados.get(indice);
        Equipo segundo = equiposMezclados.get(indice + 1);
        if (primero == null || segundo == null || primero.equals(segundo)) {
            throw new SorteoInvalidoException("Emparejamiento invalido: equipos nulos o repetidos.");
        }
        Equipo ganador = ThreadLocalRandom.current().nextBoolean() ? primero : segundo;
        salida.add(new Partido(primero, segundo, ganador));
        emparejarRecursivo(equiposMezclados, indice + 2, salida);
    }

    private void validarEquipos(List<Equipo> lista) throws NumeroEquiposInvalidoException {
        int cantidad = lista.size();
        if (cantidad < 2 || (cantidad & (cantidad - 1)) != 0) {
            throw new NumeroEquiposInvalidoException("La cantidad debe ser potencia de 2 (2, 4, 8, 16, etc.).");
        }
        Set<Equipo> unicos = new HashSet<>();
        for (Equipo equipo : lista) {
            if (equipo == null || !unicos.add(equipo)) {
                throw new IllegalArgumentException("La lista contiene equipos nulos o nombres duplicados.");
            }
        }
    }

    public void guardar(String archivo) throws IOException {
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(archivo))) {
            salida.writeObject(this);
        }
    }

    public static Torneo cargar(String archivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(archivo))) {
            Object objeto = entrada.readObject();
            if (!(objeto instanceof Torneo)) throw new IOException("El archivo no contiene un torneo valido.");
            return (Torneo) objeto;
        }
    }
}
