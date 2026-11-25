package servicio;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import uni1a.ContenidoAudiovisual;

/** Contrato pequeño para guardar y recuperar el catálogo. */
public interface RepositorioCatalogo {
    void guardar(Path carpeta, List<ContenidoAudiovisual> catalogo) throws IOException;
    List<ContenidoAudiovisual> cargar(Path carpeta) throws IOException;
}
