package com.mycompany.corporate.talent.hub.control.flujo.contoller;

import com.mycompany.corporate.talent.hub.control.flujo.service.EmpleadoService;

public class EmpleadoController {
    private final EmpleadoService service = new EmpleadoService();

    public String registrarEmpleado(int id, String nombre, byte edad, double salario, double[] calificaciones) {
        try {
            service.guardarEmpleado(id, nombre, edad, salario, calificaciones);
            return "Empleado registrado correctamente.";
        } catch (IllegalArgumentException e) {
            return "Error de validación: " + e.getMessage();
        }
    }

    public String obtenerReporteDesempeno() {
        return service.generarReporte();
    }
}
