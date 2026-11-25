# Ampliación de Contenidos Audiovisuales

Estudiante: Jordy Joel Chambi Romero

Proyecto Java educativo de Programación Orientada a Objetos basado en el ejemplo público de la Unidad 2 de la UPS. Mantiene los paquetes originales `uni1a` y `poo`.

## Objetivo

Modelar contenidos audiovisuales y sus relaciones usando herencia, asociación, agregación y composición.

## Clases y relaciones

- `ContenidoAudiovisual` es la clase abstracta base; `Pelicula`, `SerieDeTV`, `Documental`, `VideoYouTube` y `Cortometraje` la extienden.
- `Pelicula` agrega actores (`Actor`), que pueden existir y reutilizarse independientemente.
- `SerieDeTV` compone temporadas (`Temporada`), cuya vida se administra desde la serie.
- `Documental` se asocia con un investigador (`Investigador`) independiente.
- `VideoYouTube` se asocia con un canal; `Cortometraje` añade datos de producción.

## Requisitos

- JDK 8 o posterior.
- JUnit 4 para las pruebas; Maven descarga la dependencia al ejecutar `mvn test`.

## Abrir en Eclipse

1. Descomprime el ZIP en una carpeta fácil de encontrar.
2. En Eclipse elige **File > Import... > General > Existing Projects into Workspace**.
3. En **Select root directory**, selecciona la carpeta `ampliacion-contenidos-audiovisuales` que acabas de descomprimir.
4. Marca el proyecto encontrado y pulsa **Finish**. Si Eclipse solicita un JDK, elige Java 8 o posterior en **Window > Preferences > Java > Installed JREs**.
5. Abre `src/main/java/app/Aplicacion.java`, haz clic derecho y selecciona **Run As > Java Application**.

La carpeta `evidencias/` enumera capturas sugeridas para documentar la tarea.

## Pruebas unitarias

Las pruebas están en `test/` y cubren el modelo, validaciones, relaciones entre contenidos y la persistencia CSV. Para ejecutarlas se requiere Maven y conexión a internet la primera vez, para descargar JUnit:

```bash
mvn test
```

La configuración del proyecto y de JUnit está en `pom.xml`.

## Clonar y ejecutar

```bash
git clone https://github.com/Drackdy/ampliacion-contenidos-audiovisuales.git
cd ampliacion-contenidos-audiovisuales
```

En Windows (PowerShell), desde la carpeta del proyecto:

```powershell
New-Item -ItemType Directory -Force out
javac -encoding UTF-8 -d out src/main/java/uni1a/*.java src/main/java/servicio/*.java src/main/java/vista/*.java src/main/java/controlador/*.java src/main/java/app/*.java src/main/java/poo/*.java
java -cp out app.Aplicacion
```

En macOS/Linux:

```bash
mkdir -p out
javac -encoding UTF-8 -d out src/main/java/uni1a/*.java src/main/java/servicio/*.java src/main/java/vista/*.java src/main/java/controlador/*.java src/main/java/app/*.java src/main/java/poo/*.java
java -cp out app.Aplicacion
```

La clase `poo.PruebaAudioVisual` crea objetos de demostración y muestra sus detalles. La aplicación interactiva MVC se inicia con `app.Aplicacion`: permite registrar películas con sus actores, series con sus temporadas y documentales con sus investigadores, consultar el catálogo y guardar o cargar CSV en la carpeta `datos/`. Se generan `contenidos.csv`, `actores.csv`, `temporadas.csv` e `investigadores.csv`; los archivos relacionados usan el identificador del contenido para reconstruir sus asociaciones.

`diagrama-clases.svg` es el diagrama visual; `diagrama-clases.puml` contiene su fuente PlantUML.

## Diseño aplicado

- **Responsabilidad única:** el modelo representa el dominio, `VistaConsola` maneja la interacción, `CatalogoControlador` coordina las acciones, `AlmacenCsv` mapea entidades y `CsvArchivo` se ocupa del formato CSV.
- **Abierto/cerrado y sustitución de Liskov:** los tipos concretos extienden `ContenidoAudiovisual` y cada uno implementa `obtenerDetalles()`. La vista puede mostrarlos sin ramificarse por tipo. El mapeo CSV enumera los tipos persistidos y requiere ampliarse al agregar uno nuevo.
- **Segregación de interfaces e inversión de dependencias:** el controlador depende del contrato pequeño `RepositorioCatalogo`; `AlmacenCsv` es la implementación CSV que se inyecta desde `Aplicacion`.

## Estructura

```text
src/main/java/uni1a/  Clases del dominio
src/main/java/vista/  Interfaz de usuario en consola
src/main/java/controlador/ Coordinación del catálogo y el flujo de usuario
src/main/java/app/    Punto de entrada MVC
src/main/java/poo/    Programa demostrativo
src/main/java/servicio/ Almacenamiento del catálogo en CSV
datos/                 Archivos CSV generados al ejecutar
diagrama-clases.puml  Diagrama UML actualizado con MVC y persistencia
test/                 Pruebas unitarias JUnit
pom.xml               Configuración de compilación y pruebas
```

## GitHub

https://github.com/Drackdy/ampliacion-contenidos-audiovisuales
