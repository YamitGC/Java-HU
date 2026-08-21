package main.java.com.mycompany.corporate.talent.hub.control.flujo.service;
import main.java.com.mycompany.corporate.talent.hub.control.flujo.modelo.Desarrollador;
import main.java.com.mycompany.corporate.talent.hub.control.flujo.modelo.Gerente;
import main.java.com.mycompany.corporate.talent.hub.control.flujo.modelo.DesempeñoReport;
import main.java.com.mycompany.corporate.talent.hub.control.flujo.modelo.Empleado;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoService {
    private final List<Empleado> empleados = new ArrayList<>();
    private final Map<Integer, Empleado> empleadosPorId = new HashMap<>();
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
        empleadosPorId.put(id, nuevoEmpleado);
    }

    public Empleado buscarPorId(int id) {
        Empleado empleado = empleadosPorId.get(id);
        if (empleado == null) {
            throw new IllegalArgumentException("No existe un empleado con ID: "+ id);
        }
        return empleado;
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

    public List<Empleado> listarEmpleados() {
        return new ArrayList<>(empleados); // copia defensiva
    }

    public void eliminarEmpleado(int id) {
        Empleado empleado = empleadosPorId.get(id);
        if (empleado == null) {
            throw new IllegalArgumentException("No existe un empleado con ID: " + id);
        }
        empleadosPorId.remove(id);
        empleados.remove(empleado);
    }

    private boolean existeId(int id) {
        return empleadosPorId.containsKey(id);
    }

    /**
     * SINTAXIS LEGACY (Java 8/11):
     * Para obtener el primer y último elemento de una lista, había que
     * calcular manualmente los índices. Esto es propenso a errores, ya que
     * si la lista está vacía, lista.get(0) o lista.size() - 1 lanzan
     * IndexOutOfBoundsException, y hay que acordarse siempre de restar 1
     * para el último elemento.
     */
    public Empleado obtenerPrimerEmpleadoLegacy() {
        if (empleados.isEmpty()) {
            throw new IllegalArgumentException("No hay empleados registrados.");
        }
        return empleados.get(0);
    }

    public Empleado obtenerUltimoEmpleadoLegacy() {
        if (empleados.isEmpty()) {
            throw new IllegalArgumentException("No hay empleados registrados.");
        }
        return empleados.get(empleados.size() - 1);
    }

    /**
     * SINTAXIS MODERNA (Java 21 - Sequenced Collections):
     * A partir de Java 21, List (y otras colecciones ordenadas) implementan
     * la interfaz SequencedCollection, que añade getFirst(), getLast() y
     * reversed().
     *
     * Ventajas frente a la sintaxis Legacy:
     * 1. LEGIBILIDAD: getFirst()/getLast() expresan la intención directamente,
     *    en vez de obligar a leer una operación aritmética (size() - 1) para
     *    entender qué se está pidiendo.
     * 2. PREVENCIÓN DE ERRORES DE ÍNDICE: se elimina el riesgo de un
     *    "off-by-one" (ej. olvidar el -1) que antes era un error común y
     *    solo se detectaba en tiempo de ejecución.
     * 3. reversed() evita implementar manualmente un algoritmo de
     *    ordenamiento inverso (recorrer con un for en reversa o usar
     *    Collections.reverse(), que además modifica la lista original).
     *    reversed() devuelve una vista invertida sin alterar la lista base.
     * 4. CONSISTENCIA: el mismo patrón (getFirst/getLast/reversed) funciona
     *    igual en List, Deque y LinkedHashSet, entre otras.
     */
    public Empleado obtenerPrimerEmpleado() {
        if (empleados.isEmpty()) {
            throw new IllegalArgumentException("No hay empleados registrados.");
        }
        return empleados.getFirst();
    }

    public Empleado obtenerUltimoEmpleado() {
        if (empleados.isEmpty()) {
            throw new IllegalArgumentException("No hay empleados registrados.");
        }
        return empleados.getLast();
    }

    public List<Empleado> obtenerListaInvertida() {
        return empleados.reversed();
    }

    /**
     * FILTRADO AVANZADO con removeIf():
     * En vez de recorrer la lista manualmente con un Iterator para eliminar
     * elementos (como se hacía en Java 8 para evitar ConcurrentModificationException),
     * removeIf() aplica un Predicate y elimina en una sola línea, de forma segura.
     *
     * @return la cantidad de empleados eliminados
     */
    public int eliminarEmpleadosBajoPuntaje(double puntajeMinimo) {
        int tamanoAntes = empleados.size();

        empleados.removeIf(emp -> emp.getPromedioDesempeno() < puntajeMinimo);

        // Sincronizamos el HashMap, ya que removeIf solo afecta al ArrayList
        empleadosPorId.values().removeIf(emp -> emp.getPromedioDesempeno() < puntajeMinimo);

        return tamanoAntes - empleados.size();
    }

    /**
     * INFERENCIA DE TIPOS CON var (Java 11+):
     *
     * Java 8 (declaración explícita):
     *   double sumaSalarios = 0;
     *   for (Empleado emp : empleados) {
     *       double salario = emp.getSalario();
     *       sumaSalarios += salario;
     *   }
     *
     * Java 11+ (con var):
     *   var sumaSalarios = 0.0;
     *   for (var emp : empleados) {
     *       var salario = emp.getSalario();
     *       sumaSalarios += salario;
     *   }
     *
     * var no hace la variable "de tipo dinámico" (Java sigue siendo fuertemente
     * tipado y el chequeo ocurre en compilación); simplemente le pide al
     * compilador que infiera el tipo a partir del valor asignado. Esto reduce
     * la repetición visual (ej. "Empleado emp" en el for ya es obvio por el
     * contexto de "empleados"), aunque solo conviene usarlo cuando el tipo
     * sigue siendo claro por el contexto, para no perder legibilidad.
     */
    public String generarReporteFinal() {
        if (empleados.isEmpty()) {
            return "No hay empleados registrados para generar el reporte final.";
        }

        var totalEmpleados = empleados.size();
        var sumaSalarios = 0.0;

        for (var emp : empleados) {
            sumaSalarios += emp.getSalario();
        }

        var promedioSalarios = sumaSalarios / totalEmpleados;

        return String.format("""
            REPORTE FINAL
            Total de empleados: %d
            Promedio de salarios: %.2f
            """, totalEmpleados, promedioSalarios);
    }

    public void guardarDesarrollador(int id, String nombre, byte edad, double salario,
                                     double[] calificaciones, String lenguajePrincipal) {
        validarDatosBasicos(id, nombre, edad, salario, calificaciones);
        Desarrollador dev = new Desarrollador(id, nombre, edad, salario, lenguajePrincipal);
        dev.setPromedioDesempeno(calcularPromedio(calificaciones));
        empleados.add(dev);
        empleadosPorId.put(id, dev);
    }

    public void guardarGerente(int id, String nombre, byte edad, double salario,
                               double[] calificaciones, double presupuestoMensual) {
        validarDatosBasicos(id, nombre, edad, salario, calificaciones);
        Gerente ger = new Gerente(id, nombre, edad, salario, presupuestoMensual);
        ger.setPromedioDesempeno(calcularPromedio(calificaciones));
        empleados.add(ger);
        empleadosPorId.put(id, ger);
    }

    // Refactor interno: se extraen las validaciones que ya existían en
// guardarEmpleado() para poder reutilizarlas en los métodos de arriba.
    private void validarDatosBasicos(int id, String nombre, byte edad, double salario, double[] calificaciones) {
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
        for (double calificacion : calificaciones) {
            if (calificacion < 0.0 || calificacion > 100.0) {
                throw new IllegalArgumentException("La calificación debe estar entre 0 y 100.");
            }
        }
    }

    private double calcularPromedio(double[] calificaciones) {
        double suma = 0;
        for (double c : calificaciones) {
            suma += c;
        }
        return suma / calificaciones.length;
    }

    /**
     * SINTAXIS LEGACY (Java 8/11): instanceof + casting manual obligatorio.
     * Hay que preguntar el tipo y, si coincide, hacer un cast explícito
     * antes de poder usar los métodos propios de la subclase. Es repetitivo
     * y, si alguien cambia el orden o se olvida el cast, el error solo
     * aparece en tiempo de ejecución (ClassCastException).
     */
    public String validarRolLegacy(Empleado empleado) {
        if (empleado instanceof Desarrollador) {
            Desarrollador dev = (Desarrollador) empleado; // casting manual obligatorio
            return "Desarrollador: lenguaje principal = " + dev.getLenguajePrincipal();
        } else if (empleado instanceof Gerente) {
            Gerente ger = (Gerente) empleado; // casting manual obligatorio
            return "Gerente: presupuesto mensual = " + ger.getPresupuestoMensual();
        }
        return "Rol sin especialización registrada.";
    }

    /**
     * SINTAXIS MODERNA (Java 17/21) — Pattern Matching for instanceof:
     * El propio "if" declara y castea la variable ("des", "ger") solo si la
     * comprobación es verdadera. Se elimina el cast manual, se reduce el
     * código y el compilador garantiza que "des"/"ger" ya tienen el tipo
     * correcto dentro del bloque.
     */
    public String validarRolModerno(Empleado empleado) {
        if (empleado instanceof Desarrollador des) {
            return "Desarrollador: lenguaje principal = " + des.getLenguajePrincipal();
        } else if (empleado instanceof Gerente ger) {
            return "Gerente: presupuesto mensual = " + ger.getPresupuestoMensual();
        }
        return "Rol sin especialización registrada.";
    }

    /**
     * Emite un reporte de fin de mes por cada empleado usando el record
     * DesempeñoReport (TASK 2). Cada elemento de la lista es inmutable: una
     * vez generado, no puede alterarse, lo cual es justo lo que se espera
     * de un reporte "cerrado" de fin de mes.
     */
    public List<DesempeñoReport> generarReportesDeMes() {
        List<DesempeñoReport> reportes = new ArrayList<>();
        for (Empleado emp : empleados) {
            String feedback = emp.getPromedioDesempeno() >= 80.0
                    ? "Desempeño sobresaliente, listo para promoción."
                    : "Desempeño dentro de lo esperado, continuar plan de desarrollo.";
            reportes.add(new DesempeñoReport(emp.getId(), emp.getPromedioDesempeno(), feedback));
        }
        return reportes;
    }
}


