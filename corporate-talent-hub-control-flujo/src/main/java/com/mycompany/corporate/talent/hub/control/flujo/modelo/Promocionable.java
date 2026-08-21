package main.java.com.mycompany.corporate.talent.hub.control.flujo.modelo;

/**
 * Contrato de comportamiento para cualquier Persona ascendible dentro
 * del sistema (en este dominio, solo Empleado y sus subtipos).
 */
public interface Promocionable {

    /** Método abstracto: cada implementación define su propia regla de bono. */
    double calcularBonoAscenso();

    /**
     * EVOLUCIÓN DE INTERFACES (Java 8+) — métodos default:
     * Antes de Java 8, agregar un método nuevo a una interfaz existente
     * era un "breaking change": TODAS las clases que ya la implementaban
     * dejaban de compilar hasta implementar el método nuevo. Esto hacía
     * casi imposible evolucionar interfaces públicas ya usadas por
     * terceros (como pasó con las interfaces del propio JDK, ej. List,
     * antes de Java 8).
     *
     * Con "default" podemos añadir este método de logging a Promocionable
     * DESPUÉS de que ya existan clases implementándola, y ninguna de esas
     * clases se rompe ni está obligada a sobreescribirlo: hereda esta
     * implementación automáticamente.
     */
    default void registrarLog(String nombrePersona) {
        System.out.printf("[LOG] Bono de ascenso calculado para %s -> $%.2f%n",
                nombrePersona, calcularBonoAscenso());
    }
}