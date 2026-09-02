package com.mycompany.corporate.talent.hub.control.flujo.modelo;

import java.util.List;
import java.util.Map;

/**
 * Catálogo corporativo de datos fijos: Tecnologías y Sedes.
 *
 * Se usan List.of() y Map.of() (Factory Methods introducidos en Java 9 y
 * consolidados en Java 11) en lugar de un ArrayList o HashMap tradicional.
 *
 * ¿Por qué son más seguras que ArrayList/HashMap?
 * 1. INMUTABILIDAD: una vez creadas, no se pueden modificar. Cualquier intento
 *    de usar .add(), .remove() o .put() lanza una UnsupportedOperationException
 *    en tiempo de ejecución. Esto evita que otra parte del código modifique
 *    "por accidente" datos que deberían ser fijos (como el catálogo de sedes).
 * 2. THREAD-SAFETY: al no poder modificarse, son seguras para usar entre
 *    múltiples hilos sin necesidad de sincronización, algo que un ArrayList
 *    normal no garantiza.
 * 3. NO ACEPTAN null: List.of() y Map.of() lanzan NullPointerException si se
 *    intenta agregar un elemento null, evitando bugs por datos faltantes
 *    que un ArrayList sí permitiría silenciosamente.
 * 4. INTENCIÓN CLARA EN EL CÓDIGO: cualquiera que lea "List.of(...)" sabe
 *    de inmediato que esos datos son constantes y no deben cambiar en
 *    tiempo de ejecución, algo que un ArrayList no comunica por sí mismo.
 *
 * LIMITACIÓN A TENER EN CUENTA:
 * Como son inmutables, NO sirven para colecciones que necesiten crecer o
 * cambiar en tiempo de ejecución (como la lista de empleados). Para eso
 * seguimos usando ArrayList y HashMap, como en EmpleadoService.
 */
public final class CatalogoCorporativo {

    // Lista inmutable de tecnologías que maneja la empresa
    public static final List<String> TECNOLOGIAS = List.of(
            "Java",
            "Spring Boot",
            "React",
            "PostgreSQL",
            "Docker",
            "Kubernetes"
    );

    // Mapa inmutable de sedes: código de sede -> nombre de ciudad
    public static final Map<String, String> SEDES = Map.of(
            "BOG", "Bogotá",
            "MDE", "Medellín",
            "BAQ", "Barranquilla",
            "CLO", "Cali"
    );

    // Constructor privado: esta clase es solo un contenedor de constantes,
    // no debe instanciarse.
    private CatalogoCorporativo() {
    }
}
