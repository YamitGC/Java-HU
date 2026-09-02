package com.mycompany.corporate.talent.hub.control.flujo.contoller;

import com.mycompany.corporate.talent.hub.control.flujo.modelo.*;

import java.util.List;

public class EmpleadoController {

    // TASK 3: El controlador coordina las acciones entre la vista y el DAO (Persistencia JDBC)
    private final EmpleadoDAO dao = new EmpleadoDAOImpl();

    public String registrarEmpleado(int id, String nombre, byte edad, double salario, double[] calificaciones) {
        try {
            double promedio = calcularPromedio(calificaciones);
            Empleado empleado = new Empleado(id, nombre, edad, salario);
            empleado.setPromedioDesempeno(promedio);

            dao.insertar(empleado);
            return "Empleado registrado correctamente en la base de datos.";
        } catch (Exception e) {
            return "Error de validación o persistencia: " + e.getMessage();
        }
    }

    public String listarEmpleados() {
        List<Empleado> empleados = dao.listar();
        if (empleados.isEmpty()) {
            return "No hay empleados registrados en la base de datos.";
        }
        StringBuilder listado = new StringBuilder("\nLISTADO DE EMPLEADOS\n");
        for (Empleado e : empleados) {
            listado.append(String.format("ID: %d | Nombre: %s | Salario: %.2f | Promedio: %.2f%n",
                    e.getId(), e.getNombre(), e.getSalario(), e.getPromedioDesempeno()));
        }
        return listado.toString();
    }

    public String buscarEmpleado(int id) {
        Empleado empleado = dao.buscarPorId(id);
        if (empleado == null) {
            return "Error: No existe un empleado con ID: " + id;
        }
        return "Empleado encontrado -> ID: " + empleado.getId()
                + " | Nombre: " + empleado.getNombre()
                + " | Salario: " + empleado.getSalario();
    }

    public String eliminarEmpleado(int id) {
        Empleado empleado = dao.buscarPorId(id);
        if (empleado == null) {
            return "Error: No existe un empleado con ID: " + id;
        }
        dao.eliminar(id);
        return "Empleado eliminado correctamente de la base de datos.";
    }

    public String obtenerPrimerEmpleado() {
        List<Empleado> empleados = dao.listar();
        if (empleados.isEmpty()) {
            return "Error: No hay empleados en la base de datos.";
        }
        Empleado primer = empleados.getFirst(); // Java 21 Sequenced Collections
        return "Primer empleado registrado -> ID: " + primer.getId() + " | Nombre: " + primer.getNombre();
    }

    public String obtenerUltimoEmpleado() {
        List<Empleado> empleados = dao.listar();
        if (empleados.isEmpty()) {
            return "Error: No hay empleados en la base de datos.";
        }
        Empleado ultimo = empleados.getLast(); // Java 21 Sequenced Collections
        return "Último empleado registrado -> ID: " + ultimo.getId() + " | Nombre: " + ultimo.getNombre();
    }

    public String obtenerListaInvertida() {
        List<Empleado> empleados = dao.listar();
        if (empleados.isEmpty()) {
            return "No hay empleados registrados.";
        }
        List<Empleado> invertida = empleados.reversed(); // Java 21 Sequenced Collections
        StringBuilder listado = new StringBuilder("\nLISTADO INVERTIDO (últimos primero)\n");
        for (Empleado e : invertida) {
            listado.append(String.format("ID: %d | Nombre: %s%n", e.getId(), e.getNombre()));
        }
        return listado.toString();
    }

    public String eliminarEmpleadosBajoPuntaje(double puntajeMinimo) {
        List<Empleado> empleados = dao.listar();
        int contador = 0;
        for (Empleado emp : empleados) {
            if (emp.getPromedioDesempeno() < puntajeMinimo) {
                dao.eliminar(emp.getId());
                contador++;
            }
        }
        return "Empleados eliminados por bajo puntaje: " + contador;
    }

    public String registrarDesarrollador(int id, String nombre, byte edad, double salario,
                                         double[] calificaciones, String lenguajePrincipal) {
        try {
            double promedio = calcularPromedio(calificaciones);
            Desarrollador dev = new Desarrollador(id, nombre, edad, salario, lenguajePrincipal);
            dev.setPromedioDesempeno(promedio);

            dao.insertar(dev);
            return "Desarrollador registrado correctamente en la base de datos.";
        } catch (Exception e) {
            return "Error de validación: " + e.getMessage();
        }
    }

    public String registrarGerente(int id, String nombre, byte edad, double salario,
                                   double[] calificaciones, double presupuestoMensual) {
        try {
            double promedio = calcularPromedio(calificaciones);
            Gerente ger = new Gerente(id, nombre, edad, salario, presupuestoMensual);
            ger.setPromedioDesempeno(promedio);

            dao.insertar(ger);
            return "Gerente registrado correctamente en la base de datos.";
        } catch (Exception e) {
            return "Error de validación: " + e.getMessage();
        }
    }

    public String validarRol(int id, boolean usarSintaxisModerna) {
        Empleado empleado = dao.buscarPorId(id);
        if (empleado == null) {
            return "Error: No existe un empleado con ID: " + id;
        }

        if (usarSintaxisModerna) {
            // Pattern Matching for instanceof (Java 17+)
            if (empleado instanceof Desarrollador dev) {
                return "Desarrollador: lenguaje principal = " + dev.getLenguajePrincipal();
            } else if (empleado instanceof Gerente ger) {
                return "Gerente: presupuesto mensual = " + ger.getPresupuestoMensual();
            }
        } else {
            // Legacy casting
            if (empleado instanceof Desarrollador) {
                Desarrollador dev = (Desarrollador) empleado;
                return "Desarrollador: lenguaje principal = " + dev.getLenguajePrincipal();
            } else if (empleado instanceof Gerente) {
                Gerente ger = (Gerente) empleado;
                return "Gerente: presupuesto mensual = " + ger.getPresupuestoMensual();
            }
        }
        return "Empleado estándar sin especialización.";
    }

    // TASK 4: Text Blocks + Records
    public String obtenerReporteFinalConsolidado() {
        List<DesempenoReport> reportes = dao.obtenerReportesComplex();
        if (reportes.isEmpty()) {
            return "No hay datos para generar el reporte consolidado.";
        }

        StringBuilder sb = new StringBuilder("\n================ REPORTE CONSOLIDADOS ================\n");
        for (DesempenoReport r : reportes) {
            String bloque = """
                ----------------------------------------
                ID Empleado : %d
                Promedio    : %.2f
                Diagnóstico : %s
                ----------------------------------------
                """.formatted(r.idEmpleado(), r.promedio(), r.feedback());
            sb.append(bloque);
        }
        return sb.toString();
    }

    public void registrarLogBono(int id) {
        Empleado empleado = dao.buscarPorId(id);
        if (empleado != null) {
            empleado.registrarLog(empleado.getNombre());
        }
    }

    private double calcularPromedio(double[] calificaciones) {
        if (calificaciones == null || calificaciones.length == 0) return 0.0;
        double suma = 0;
        for (double c : calificaciones) {
            if (c < 0.0 || c > 100.0) {
                throw new IllegalArgumentException("La calificación debe estar entre 0 y 100.");
            }
            suma += c;
        }
        return suma / calificaciones.length;
    }
}