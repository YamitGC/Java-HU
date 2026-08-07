package com.mycompany.corporate.talent.hub.control.flujo.service;

import com.mycompany.corporate.talent.hub.control.flujo.modelo.Empleado;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoService {
    private final List<Empleado> empleados = new ArrayList<>();
    private static final int MAXIMO_EMPLEADOS = 50;

    public void guardarEmpleado(int id, String nombre, byte edad, double salario, double[] calificaciones) {
        if (empleados.size() >= MAXIMO_EMPLEADOS) {
            throw new IllegalArgumentException("No hay espacio para más empleados.");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor que cero.");
        }
        if (existeId(id)) {
            throw new IllegalArgumentException("Ya existe un empleado con ese ID.");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (edad < 18 || edad > 100) {
            throw new IllegalArgumentException("La edad está fuera del rango permitido (18-100).");
        }
        if (salario <= 0) {
            throw new IllegalArgumentException("El salario debe ser mayor que cero.");
        }

        double suma = 0;
        for (double calificacion : calificaciones) {
            if (calificacion < 0.0 || calificacion > 100.0) {
                throw new IllegalArgumentException("La calificación debe estar entre 0 y 100.");
            }
            suma += calificacion;
        }

        double promedio = suma / calificaciones.length;

        Empleado nuevoEmpleado = new Empleado(id, nombre, edad, salario);
        nuevoEmpleado.setPromedioDesempeno(promedio); 
        empleados.add(nuevoEmpleado);
    }

    public String generarReporte() {
        if (empleados.isEmpty()) {
            return "Todavía no hay empleados registrados.";
        }

        StringBuilder reporte = new StringBuilder("\nREPORTE DE DESEMPEÑO\n");
        for (Empleado emp : empleados) {
            double promedio = emp.getPromedioDesempeno();
            int promedioSimplificado = (int) promedio;
            
            // Determina el estado del empleado según la nota
            String estado = (promedio >= 80.0) ? "PROMOVIDO" : "REGULAR";
            
            // Determina la categoría del empleado según los rangos de salario entregados
            String categoria;
            double salario = emp.getSalario();
            if (salario < 2000000) {
                categoria = "JUNIOR";
            } else if (salario < 4000000) {
                categoria = "SEMISENIOR";
            } else if (salario < 7000000) {
                categoria = "SENIOR";
            } else {
                categoria = "LÍDER";
            }

            // Agrega la línea formateada al reporte final
            reporte.append(String.format("ID: %d | Nombre: %s | Promedio: %.2f | Simplificado: %d | Estado: %s | Categoría: %s\n", 
                    emp.getId(), emp.getNombre(), promedio, promedioSimplificado, estado, categoria));
        }
        return reporte.toString();
    }

    private boolean existeId(int id) {
        return empleados.stream().anyMatch(e -> e.getId() == id);
    }
}
