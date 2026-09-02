package com.mycompany.corporate.talent.hub.control.flujo.modelo;

/*
 * ANÁLISIS DE MANTENIBILIDAD (Records + JDBC Moderno vs POJO Java 8):
 * 1. Los Records eliminan la necesidad de escribir manualmente Getters, equals(),
 *    hashCode() y toString(), reduciendo el boilerplate de ~50 líneas a una sola.
 * 2. Inmutabilidad por diseño: Evita modificaciones accidentales de los datos
 *    obtenidos desde la base de datos mientras transitan entre las capas MVC.
 * 3. En combinación con Text Blocks, mapear y presentar reportes desde JDBC
 *    resulta directo, limpio y de fácil lectura.
 */
public record EmpleadoReporteRecord(
        int id,
        String nombre,
        byte edad,
        double salario,
        double promedioDesempeno
) {}