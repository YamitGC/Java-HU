package com.mycompany.corporate.talent.hub.control.flujo.modelo;


import com.mycompany.corporate.talent.hub.control.flujo.modelo.Empleado;
import com.mycompany.corporate.talent.hub.control.flujo.modelo.DesempenoReport;
import com.mycompany.corporate.talent.hub.control.flujo.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAOImpl implements EmpleadoDAO {

    @Override
    public void insertar(Empleado empleado) {
        String sql = "INSERT INTO empleados (id, nombre, edad, salario, promedio_desempeno) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, empleado.getId());
            stmt.setString(2, empleado.getNombre());
            stmt.setByte(3, empleado.getEdad());
            stmt.setDouble(4, empleado.getSalario());
            stmt.setDouble(5, empleado.getPromedioDesempeno());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar en la BD: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Empleado> listar() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT id, nombre, edad, salario, promedio_desempeno FROM empleados";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Empleado emp = new Empleado(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getByte("edad"),
                        rs.getDouble("salario")
                );
                emp.setPromedioDesempeno(rs.getDouble("promedio_desempeno"));
                empleados.add(emp);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar empleados: " + e.getMessage(), e);
        }
        return empleados;
    }

    @Override
    public void actualizar(Empleado empleado) {
        String sql = "UPDATE empleados SET nombre = ?, edad = ?, salario = ?, promedio_desempeno = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, empleado.getNombre());
            stmt.setByte(2, empleado.getEdad());
            stmt.setDouble(3, empleado.getSalario());
            stmt.setDouble(4, empleado.getPromedioDesempeno());
            stmt.setInt(5, empleado.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM empleados WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar: " + e.getMessage(), e);
        }
    }

    @Override
    public Empleado buscarPorId(int id) {
        String sql = "SELECT id, nombre, edad, salario, promedio_desempeno FROM empleados WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Empleado emp = new Empleado(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getByte("edad"),
                            rs.getDouble("salario")
                    );
                    emp.setPromedioDesempeno(rs.getDouble("promedio_desempeno"));
                    return emp;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar por ID: " + e.getMessage(), e);
        }
        return null;
    }

    /*
     * TASK 4: Mapeo de SELECT mediante el Record existente (DesempenoReport).
     */
    @Override
    public List<DesempenoReport> obtenerReportesComplex() {
        List<DesempenoReport> reportes = new ArrayList<>();
        String sql = "SELECT id, promedio_desempeno FROM empleados";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                double prom = rs.getDouble("promedio_desempeno");
                String feedback = prom >= 80.0 ? "Promoción recomendada" : "Desempeño estándar";

                // Instanciación directa del Record 'DesempenoReport' que tenías definido
                reportes.add(new DesempenoReport(rs.getInt("id"), prom, feedback));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al generar reporte con Records: " + e.getMessage(), e);
        }
        return reportes;
    }
}