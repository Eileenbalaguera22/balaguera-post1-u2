# balaguera-post1-u2-
Post-contenido — Exportación de reportes académicos con patrones creacionales justificados


## Descripción

Repositorio del post-contenido de la Unidad 2 de Patrones de Diseño
de Software — Sexto Semestre. Un único proyecto Maven
(exportador-reportes/) que resuelve la exportación de reportes
académicos en múltiples formatos (Parte 1) y se extiende con
configuración compleja mediante Builder y evaluación de la necesidad
de utilizar Singleton (Parte 2).

## Cómo ejecutar

```bash
cd exportador-reportes
mvn compile
mvn exec:java "-Dexec.mainClass=com.patrones.u2.Main"

Decisiones de diseño
Decisión 1 — Factory Method vs. Abstract Factory (Parte 1)

Patrón elegido: Abstract Factory.

Se eligió Abstract Factory porque el sistema necesita crear una
familia de objetos relacionados para cada formato de exportación:
ReportBody y ReportHeaderFooter. La fábrica concreta de cada
formato permite crear ambos productos manteniendo su compatibilidad.

Factory Method también habría permitido encapsular la creación de
un producto, pero en este caso existen varios productos relacionados
que deben pertenecer a la misma familia de formato. Por esta razón,
Abstract Factory resulta más apropiado para mantener la consistencia
entre el cuerpo y el encabezado/pie de cada reporte.

Decisión 2 — Mecanismo de extensibilidad de formatos (Parte 1)

Opción elegida: registro de fábricas mediante ReportFactoryRegistry.

Se eligió un registro central de fábricas para asociar cada formato
con su fábrica correspondiente. De esta manera, el servicio de
exportación no necesita contener una cadena creciente de
if/else o switch para decidir qué fábrica utilizar.

La alternativa de implementar toda la selección mediante
condicionales fue descartada porque cada nuevo formato obligaría a
modificar la lógica central del servicio. Con el registro, agregar un
nuevo formato consiste principalmente en registrar su fábrica,
manteniendo la selección desacoplada de la lógica de exportación.

Decisión 3 — Builder vs. constructor telescópico vs. setters (Parte 2)

Patrón elegido: Builder.

Se eligió Builder para construir ExportConfig porque la configuración
tiene un parámetro obligatorio (format) y ocho parámetros opcionales:
outputPath, pageSize, orientation, locale, watermarkText,
includeLogo, compress y maxRowsPerPage.

La alternativa de utilizar un constructor con los nueve parámetros
fue descartada porque el cliente tendría que recordar el orden exacto
de todos los argumentos. Además, varios parámetros son del mismo tipo,
especialmente String y boolean, por lo que intercambiarlos podría
producir una configuración incorrecta sin generar un error de
compilación.

También se descartaron múltiples constructores sobrecargados porque
con ocho parámetros opcionales las combinaciones crecen rápidamente y
la clase terminaría teniendo numerosos constructores similares.

Finalmente, una clase mutable con setters sueltos permitiría que el
objeto quedara parcialmente configurado. No existiría un punto único
para verificar que toda la combinación de valores fuera consistente.

Builder resuelve estos problemas mediante métodos encadenables y un
método build() que valida la configuración antes de crear el objeto.
Además, ExportConfig mantiene sus atributos finales después de ser
construido.

Por ejemplo, la configuración:

new ExportConfig.Builder("pdf")
    .pageSize("LETTER")
    .orientation("LANDSCAPE")
    .locale("en-US")
    .watermarkText("BORRADOR")
    .maxRowsPerPage(25)
    .build();

permite identificar claramente qué valor corresponde a cada
parámetro.

La validación también evita configuraciones inconsistentes. Por
ejemplo, solicitar compresión sin indicar outputPath produce una
IllegalStateException durante build().

Decisión 4 — ¿ReportFactoryRegistry necesita ser Singleton? (Parte 2)

Conclusión: NO conviene convertir ReportFactoryRegistry en Singleton.

La decisión se tomó evaluando objetivamente la necesidad de una
instancia única.

Identidad de objeto: el sistema no necesita pasar
ReportFactoryRegistry como objeto, implementarlo mediante una
interfaz, sustituirlo mediante un mock o inyectarlo por constructor.
Actualmente basta con invocar sus métodos estáticos.

Inicialización costosa: el registro solamente mantiene un
Map estático con las fábricas disponibles. Su inicialización no
implica leer archivos, abrir conexiones ni realizar operaciones
costosas que justifiquen una inicialización perezosa.

Fuente única de verdad: el Map estático ya proporciona un
registro compartido dentro de la JVM. Convertir la clase en un
Singleton clásico agregaría getInstance(), una instancia y
mecanismos adicionales sin resolver una necesidad real del sistema.

Escenarios futuros razonables: si el sistema evolucionara hacia
una plataforma multiinstitución donde cada institución necesitara un
registro diferente de formatos, un Singleton sería una limitación
porque impediría disponer naturalmente de registros independientes.

Por estas razones, se mantiene el diseño de la Parte 1: una clase
final con constructor privado y miembros estáticos. No se agrega la
estructura adicional de un Singleton clásico porque no existe una
necesidad concreta que la justifique.

Herramientas utilizadas
Java 17
Apache Maven
VS Code
Git
GitHub
JUnit 5
Conclusiones

La Parte 2 permitió aplicar Builder a un problema donde existen
muchos parámetros opcionales y es necesario validar la configuración
antes de construir el objeto. También permitió comprobar que no todo
registro central necesita convertirse automáticamente en Singleton:
la decisión debe depender de la necesidad real de identidad,
compartición e inicialización del objeto. En conjunto, las dos partes
muestran que elegir un patrón no consiste solamente en reconocer su
nombre, sino en comparar el problema concreto con las consecuencias
de cada alternativa. Una solución sencilla es preferible cuando un
patrón adicional no aporta un beneficio real.