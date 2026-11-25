package servicio;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Lee y escribe filas CSV UTF-8, incluyendo comas, comillas y saltos de línea entrecomillados. */
final class CsvArchivo {
    void escribir(Path archivo, List<List<String>> filas) throws IOException {
        StringBuilder contenido = new StringBuilder();
        for (List<String> fila : filas) {
            for (int indice = 0; indice < fila.size(); indice++) {
                if (indice > 0) contenido.append(',');
                contenido.append(escapar(fila.get(indice)));
            }
            contenido.append("\r\n");
        }
        Files.write(archivo, contenido.toString().getBytes(StandardCharsets.UTF_8));
    }

    List<List<String>> leer(Path archivo) throws IOException {
        List<List<String>> registros = new ArrayList<List<String>>();
        if (!Files.exists(archivo)) return registros;
        String contenido = new String(Files.readAllBytes(archivo), StandardCharsets.UTF_8);
        List<String> registro = new ArrayList<String>();
        StringBuilder campo = new StringBuilder();
        boolean entreComillas = false;

        for (int indice = 0; indice < contenido.length(); indice++) {
            char caracter = contenido.charAt(indice);
            if (caracter == '"') {
                if (entreComillas && indice + 1 < contenido.length() && contenido.charAt(indice + 1) == '"') {
                    campo.append('"');
                    indice++;
                } else {
                    entreComillas = !entreComillas;
                }
            } else if (caracter == ',' && !entreComillas) {
                registro.add(campo.toString());
                campo.setLength(0);
            } else if ((caracter == '\n' || caracter == '\r') && !entreComillas) {
                agregarRegistro(registros, registro, campo);
                registro = new ArrayList<String>();
                campo = new StringBuilder();
                if (caracter == '\r' && indice + 1 < contenido.length() && contenido.charAt(indice + 1) == '\n') indice++;
            } else {
                campo.append(caracter);
            }
        }
        if (entreComillas) throw new IOException("Hay comillas sin cerrar en " + archivo.getFileName() + ".");
        if (campo.length() > 0 || !registro.isEmpty()) agregarRegistro(registros, registro, campo);
        if (!registros.isEmpty()) registros.remove(0); // La primera fila contiene encabezados.
        return registros;
    }

    private static void agregarRegistro(List<List<String>> registros, List<String> registro, StringBuilder campo) {
        registro.add(campo.toString());
        boolean vacio = true;
        for (String valor : registro) {
            if (!valor.isEmpty()) { vacio = false; break; }
        }
        if (!vacio) registros.add(registro);
    }

    private static String escapar(String valor) {
        if (valor.indexOf(',') < 0 && valor.indexOf('"') < 0
                && valor.indexOf('\n') < 0 && valor.indexOf('\r') < 0) return valor;
        return '"' + valor.replace("\"", "\"\"") + '"';
    }
}
