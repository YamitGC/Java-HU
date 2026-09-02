package com.mycompany.corporate.talent.hub.control.flujo.modelo;

import java.util.Objects;

/**
 * ESTILO LEGACY (Java 8/11) — POJO tradicional.
 * Este archivo hace EXACTAMENTE lo mismo que el record DesempeñoReport,
 * pero requiere escribir manualmente: campos, constructor, getters,
 * equals(), hashCode() y toString(). No se usa en el flujo de la app;
 * se deja solo como comparación directa contra el record del PASO 7.
 */
public final class DesempenoReportLegacyPOJO {

    private final int idEmpleado;
    private final double promedio;
    private final String feedback;

    public DesempenoReportLegacyPOJO(int idEmpleado, double promedio, String feedback) {
        this.idEmpleado = idEmpleado;
        this.promedio = promedio;
        this.feedback = feedback;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public double getPromedio() {
        return promedio;
    }

    public String getFeedback() {
        return feedback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DesempenoReportLegacyPOJO that)) return false;
        return idEmpleado == that.idEmpleado
                && Double.compare(promedio, that.promedio) == 0
                && Objects.equals(feedback, that.feedback);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEmpleado, promedio, feedback);
    }

    @Override
    public String toString() {
        return "DesempeñoReportLegacyPOJO{idEmpleado=" + idEmpleado
                + ", promedio=" + promedio + ", feedback='" + feedback + "'}";
    }
}