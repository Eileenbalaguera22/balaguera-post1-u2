# balaguera-post1-u2-
Post-contenido — Exportación de reportes académicos con patrones creacionales justificados

## Decisiones de diseño

### Decisión 1: Abstract Factory

Se utilizó el patrón Abstract Factory para crear familias de productos relacionados
para cada formato de reporte: PDF, Excel y HTML.

Cada fábrica concreta (`PdfReportFactory`, `ExcelReportFactory` y
`HtmlReportFactory`) crea un `ReportBody` y un `ReportHeaderFooter` compatibles
entre sí.

De esta manera, el código cliente (`ReportExportService`) trabaja únicamente
con las abstracciones y no necesita conocer las clases concretas.

Esto permite mantener la consistencia entre los productos de una misma familia
y facilita la incorporación de nuevos formatos.

### Decisión 2: Registry para extensibilidad

Se utilizó un registro (`ReportFactoryRegistry`) para asociar dinámicamente el
nombre de cada formato con su fábrica correspondiente.

El registro utiliza `Supplier<ReportFormatFactory>` para crear las fábricas
cuando son solicitadas.

Esto evita utilizar cadenas de `if/else` o `switch` para seleccionar el formato.

Además, permite registrar nuevos formatos mediante:

```java
ReportFactoryRegistry.register("csv", CsvReportFactory::new);