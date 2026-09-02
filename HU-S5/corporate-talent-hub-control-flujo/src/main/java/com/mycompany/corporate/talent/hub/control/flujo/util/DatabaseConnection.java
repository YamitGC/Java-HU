package com.mycompany.corporate.talent.hub.control.flujo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/talent_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /*
     * SINTAXIS LEGACY (Java 8 hacia atrás):
     * Antes de Java 7/8, la gestión de conexiones se realizaba cerrando de forma
     * explícita los objetos en un bloque 'finally'. Esto obligaba a anidar bloques
     * try-catch dentro del finally para evitar que un fallo al cerrar un recurso
     * previniera el cierre de los demás:
     *
     * Connection conn = null;
     * PreparedStatement stmt = null;
     * ResultSet rs = null;
     * try {
     *     conn = DriverManager.getConnection(URL, USER, PASSWORD);
     *     stmt = conn.prepareStatement("SELECT * FROM empleados");
     *     rs = stmt.executeQuery();
     * } catch (SQLException e) {
     *     e.printStackTrace();
     * } finally {
     *     if (rs != null) { try { rs.close(); } catch (SQLException e) { /* ignore * / } }
     *     if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignore * / } }
     *     if (conn != null) { try { conn.close(); } catch (SQLException e) { /* ignore * / } }
     * }
     *
     * EXPLICACIÓN PREVENCIÓN DE MEMORY LEAKS (Java 17/21 - Try-With-Resources):
     * La sintaxis moderna utiliza try-with-resources. Cualquier objeto que implemente
     * java.lang.AutoCloseable o java.io.Closeable declarado dentro de los paréntesis
     * del 'try' se cerrará automáticamente al finalizar el bloque, incluso si ocurre
     * una excepción. Esto elimina las fugas de memoria (Memory Leaks) causadas por
     * conexiones huérfanas o sockets JDBC abiertos que agotan el pool de la BD.
     */
}