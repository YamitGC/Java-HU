package main.java.com.mycompany.corporate.talent.hub.control.flujo.modelo;

/**
 * SINTAXIS MODERNA (Java 17/21) — record:
 * Un record es una clase inmutable de solo-datos. Con esta única línea,
 * el compilador genera automáticamente:
 *  - Un constructor canónico (idEmpleado, promedio, feedback).
 *  - Getters con el nombre del campo: idEmpleado(), promedio(), feedback().
 *  - equals(), hashCode() y toString() basados en todos los campos.
 *  - Los campos son "final" por naturaleza -> el record es inmutable:
 *    una vez creado un DesempeñoReport, no puede modificarse. Ideal para
 *    un reporte de fin de mes que no debería cambiar después de emitido.
 *
 * Compara esto con la versión POJO tradicional en
 * DesempeñoReportLegacyPOJO.java (mismo resultado, ~40 líneas más).
 */
public record DesempenoReport(int idEmpleado, double promedio, String feedback) {
}