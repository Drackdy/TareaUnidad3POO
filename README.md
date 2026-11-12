# Sorteo de liga profesional - Unidad 3

Aplicacion Java de consola para sortear un torneo eliminatorio de 16 equipos. El proyecto aplica MVC, emparejamiento recursivo, excepciones propias y persistencia con serializacion de objetos.

## Requisitos

- JDK 11 o superior.
- Git para publicar el proyecto en GitHub.

## Compilar y ejecutar

Desde la carpeta `TorneoMVC`, en Windows PowerShell:

```powershell
javac -encoding UTF-8 -d out (Get-ChildItem -Recurse src -Filter *.java | ForEach-Object FullName)
java -cp out torneo.mvc.Main
```

Elige `1` para generar el sorteo completo y guardar `torneo.ser` en la carpeta desde la que ejecutaste Java. Elige `2` para cargar y mostrar el sorteo guardado. La aplicacion simula al azar el equipo que avanza en cada partido para poder construir las etapas siguientes.

## Organizacion MVC

- `modelo`: equipos, partidos, torneo, reglas recursivas, excepciones y serializacion.
- `vista`: menu y presentacion de resultados por consola.
- `controlador`: flujo de opciones y conexion entre vista y modelo.
- `Main.java`: punto de entrada.

## Requisitos implementados

- 16 participantes y cuatro etapas: octavos, cuartos, semifinales y final.
- Mezcla aleatoria de cada ronda y emparejamiento recursivo; cada equipo aparece una sola vez por etapa.
- `NumeroEquiposInvalidoException` valida cantidades que no son potencia de dos.
- `SorteoInvalidoException` reporta estados de emparejamiento imposibles.
- `Torneo` implementa `Serializable` y guarda/carga con `ObjectOutputStream` y `ObjectInputStream`.
- Captura de excepciones de sorteo y de E/S en el controlador.
