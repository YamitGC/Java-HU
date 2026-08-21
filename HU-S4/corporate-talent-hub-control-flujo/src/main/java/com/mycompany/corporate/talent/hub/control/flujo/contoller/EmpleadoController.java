package main.java.com.mycompany.corporate.talent.hub.control.flujo.contoller;

import main.java.com.mycompany.corporate.talent.hub.control.flujo.service.EmpleadoService;
import main.java.com.mycompany.corporate.talent.hub.control.flujo.modelo.Empleado;
import main.java.com.mycompany.corporate.talent.hub.control.flujo.modelo.Desarrollador;
import main.java.com.mycompany.corporate.talent.hub.control.flujo.modelo.Gerente;
import main.java.com.mycompany.corporate.talent.hub.control.flujo.modelo.DesempenoReport;

import java.util.List;

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

    public String buscarEmpleado(int id) {
        try {
            Empleado empleado = service.buscarPorId(id);
            return "Empleado encontrado -> ID: " + empleado.getId()
                    + " | Nombre: " + empleado.getNombre()
                    + " | Salario: " + empleado.getSalario();
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    public String eliminarEmpleado(int id) {
        try {
            service.eliminarEmpleado(id);
            return "Empleado eliminado correctamente.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    public String listarEmpleados() {
        List<Empleado> empleados = service.listarEmpleados();
        if (empleados.isEmpty()) {
            return "No hay empleados registrados.";
        }
        StringBuilder listado = new StringBuilder("\nLISTADO DE EMPLEADOS\n");
        for (Empleado e : empleados) {
            listado.append(String.format("ID: %d | Nombre: %s | Salario: %.2f\n",
                    e.getId(), e.getNombre(), e.getSalario()));
        }
        return listado.toString();
    }

    public String obtenerPrimerEmpleado() {
        try {
            Empleado empleado = service.obtenerPrimerEmpleado();
            return "Primer empleado registrado -> ID: " + empleado.getId()
                    + " | Nombre: " + empleado.getNombre();
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    public String obtenerUltimoEmpleado() {
        try {
            Empleado empleado = service.obtenerUltimoEmpleado();
            return "Último empleado registrado -> ID: " + empleado.getId()
                    + " | Nombre: " + empleado.getNombre();
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    public String obtenerListaInvertida() {
        List<Empleado> invertida = service.obtenerListaInvertida();
        if (invertida.isEmpty()) {
            return "No hay empleados registrados.";
        }
        StringBuilder listado = new StringBuilder("\nLISTADO INVERTIDO (últimos primero)\n");
        for (Empleado e : invertida) {
            listado.append(String.format("ID: %d | Nombre: %s\n", e.getId(), e.getNombre()));
        }
        return listado.toString();
    }

    public String eliminarEmpleadosBajoPuntaje(double puntajeMinimo) {
        int eliminados = service.eliminarEmpleadosBajoPuntaje(puntajeMinimo);
        return "Empleados eliminados por bajo puntaje: " + eliminados;
    }

    public String obtenerReporteFinal() {
        return service.generarReporteFinal();
    }

    public String registrarDesarrollador(int id, String nombre, byte edad, double salario,
                                         double[] calificaciones, String lenguajePrincipal) {
        try {
            service.guardarDesarrollador(id, nombre, edad, salario, calificaciones, lenguajePrincipal);
            return "Desarrollador registrado correctamente.";
        } catch (IllegalArgumentException e) {
            return "Error de validación: " + e.getMessage();
        }
    }

    public String registrarGerente(int id, String nombre, byte edad, double salario,
                                   double[] calificaciones, double presupuestoMensual) {
        try {
            service.guardarGerente(id, nombre, edad, salario, calificaciones, presupuestoMensual);
            return "Gerente registrado correctamente.";
        } catch (IllegalArgumentException e) {
            return "Error de validación: " + e.getMessage();
        }
    }

    public String validarRol(int id, boolean usarSintaxisModerna) {
        try {
            Empleado empleado = service.buscarPorId(id);
            return usarSintaxisModerna
                    ? service.validarRolModerno(empleado)
                    : service.validarRolLegacy(empleado);
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    public String obtenerReporteMensual() {
        List<DesempenoReport> reportes = service.generarReportesDeMes();
        if (reportes.isEmpty()) {
            return "No hay reportes disponibles.";
        }
        StringBuilder sb = new StringBuilder("\nREPORTES DE FIN DE MES\n");
        for (DesempenoReport r : reportes) {
            sb.append(String.format("ID: %d | Promedio: %.2f | Feedback: %s%n",
                    r.idEmpleado(), r.promedio(), r.feedback()));
        }
        return sb.toString();
    }
    
       public void registrarLogBono(int id) {
       Empleado empleado = service.buscarPorId(id);
       empleado.registrarLog(empleado.getNombre());
   }
}
