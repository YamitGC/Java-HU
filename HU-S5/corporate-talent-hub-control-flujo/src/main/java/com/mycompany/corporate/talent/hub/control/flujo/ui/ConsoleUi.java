package com.mycompany.corporate.talent.hub.control.flujo.ui;

import com.mycompany.corporate.talent.hub.control.flujo.contoller.EmpleadoController;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleUi {
    private final EmpleadoController controller = new EmpleadoController();

    public void iniciar() {
        try (var scanner = new Scanner(System.in)) {
            var sistemaActivo = true;

            do {
                mostrarMenu();
                try {
                    System.out.print("Seleccione una opción: ");
                    var opcion = scanner.nextInt();
                    scanner.nextLine();

                    switch (opcion) {
                        case 1 -> registrarEmpleadoFormulario(scanner);
                        case 2 -> registrarDesarrolladorFormulario(scanner);
                        case 3 -> registrarGerenteFormulario(scanner);
                        case 4 -> buscarEmpleadoFormulario(scanner);
                        case 5 -> eliminarEmpleadoFormulario(scanner);
                        case 6 -> System.out.println(controller.listarEmpleados());
                        case 7 -> System.out.println(controller.obtenerPrimerEmpleado());
                        case 8 -> System.out.println(controller.obtenerUltimoEmpleado());
                        case 9 -> System.out.println(controller.obtenerListaInvertida());
                        case 10 -> eliminarPorPuntajeFormulario(scanner);
                        case 11 -> System.out.println(controller.obtenerReporteFinalConsolidado());
                        case 12 -> validarRolFormulario(scanner);
                        case 0 -> {
                            sistemaActivo = false;
                            System.out.println("Sesión finalizada.");
                        }
                        default -> System.out.println("Opción fuera del menú.");
                    }
                } catch (InputMismatchException excepcion) {
                    System.out.println("Entrada inválida. Debe escribir un valor numérico.");
                    scanner.nextLine();
                }
            } while (sistemaActivo);
        }
    }

    private void mostrarMenu() {
        System.out.println("""
                =====================================
                     CORPORATE TALENT HUB (JDBC)
                =====================================
                1. Registrar Empleado Estándar
                2. Registrar Desarrollador
                3. Registrar Gerente
                4. Buscar empleado por ID
                5. Eliminar empleado por ID
                6. Listar todos los empleados
                7. Ver primer empleado registrado
                8. Ver último empleado registrado
                9. Ver listado en orden inverso
                10. Eliminar empleados bajo puntaje mínimo
                11. Ver reporte consolidado (Text Blocks + Records)
                12. Validar rol de un empleado (Pattern Matching)
                0. Salir
                """);
    }

    private void registrarEmpleadoFormulario(Scanner scanner) {
        try {
            System.out.print("ID positivo: ");
            var id = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Nombre: ");
            var nombre = scanner.nextLine().trim();

            System.out.print("Edad entre 18 y 100: ");
            var edad = scanner.nextByte();

            System.out.print("Salario mayor que cero: ");
            var salario = scanner.nextDouble();

            double[] calificaciones = pedirCalificaciones(scanner);

            String respuesta = controller.registrarEmpleado(id, nombre, edad, salario, calificaciones);
            System.out.println(respuesta);

        } catch (InputMismatchException e) {
            System.out.println("Error: Tipo de dato inválido en el formulario.");
            scanner.nextLine();
        }
    }

    private void registrarDesarrolladorFormulario(Scanner scanner) {
        try {
            System.out.print("ID positivo: ");
            var id = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Nombre: ");
            var nombre = scanner.nextLine().trim();

            System.out.print("Edad entre 18 y 100: ");
            var edad = scanner.nextByte();

            System.out.print("Salario mayor que cero: ");
            var salario = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Lenguaje Principal: ");
            var lenguaje = scanner.nextLine().trim();

            double[] calificaciones = pedirCalificaciones(scanner);

            String respuesta = controller.registrarDesarrollador(id, nombre, edad, salario, calificaciones, lenguaje);
            System.out.println(respuesta);

        } catch (InputMismatchException e) {
            System.out.println("Error: Tipo de dato inválido en el formulario.");
            scanner.nextLine();
        }
    }

    private void registrarGerenteFormulario(Scanner scanner) {
        try {
            System.out.print("ID positivo: ");
            var id = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Nombre: ");
            var nombre = scanner.nextLine().trim();

            System.out.print("Edad entre 18 y 100: ");
            var edad = scanner.nextByte();

            System.out.print("Salario mayor que cero: ");
            var salario = scanner.nextDouble();

            System.out.print("Presupuesto Mensual: ");
            var presupuesto = scanner.nextDouble();

            double[] calificaciones = pedirCalificaciones(scanner);

            String respuesta = controller.registrarGerente(id, nombre, edad, salario, calificaciones, presupuesto);
            System.out.println(respuesta);

        } catch (InputMismatchException e) {
            System.out.println("Error: Tipo de dato inválido en el formulario.");
            scanner.nextLine();
        }
    }

    private double[] pedirCalificaciones(Scanner scanner) {
        double[] calificaciones = new double[3];
        for (var i = 0; i < 3; i++) {
            System.out.printf("Calificación del trimestre %d (0 a 100): ", i + 1);
            calificaciones[i] = scanner.nextDouble();
        }
        scanner.nextLine();
        return calificaciones;
    }

    private void buscarEmpleadoFormulario(Scanner scanner) {
        try {
            System.out.print("Ingrese el ID del empleado a buscar: ");
            var id = scanner.nextInt();
            scanner.nextLine();

            System.out.println(controller.buscarEmpleado(id));
        } catch (InputMismatchException e) {
            System.out.println("Error: Debe ingresar un ID numérico.");
            scanner.nextLine();
        }
    }

    private void eliminarEmpleadoFormulario(Scanner scanner) {
        try {
            System.out.print("Ingrese el ID del empleado a eliminar: ");
            var id = scanner.nextInt();
            scanner.nextLine();

            System.out.println(controller.eliminarEmpleado(id));
        } catch (InputMismatchException e) {
            System.out.println("Error: Debe ingresar un ID numérico.");
            scanner.nextLine();
        }
    }

    private void eliminarPorPuntajeFormulario(Scanner scanner) {
        try {
            System.out.print("Ingrese el puntaje mínimo requerido: ");
            var puntajeMinimo = scanner.nextDouble();
            scanner.nextLine();
            System.out.println(controller.eliminarEmpleadosBajoPuntaje(puntajeMinimo));
        } catch (InputMismatchException e) {
            System.out.println("Error: Debe ingresar un valor numérico decimal.");
            scanner.nextLine();
        }
    }

    private void validarRolFormulario(Scanner scanner) {
        try {
            System.out.print("Ingrese el ID del empleado a validar: ");
            var idValidar = scanner.nextInt();
            scanner.nextLine();
            System.out.println(controller.validarRol(idValidar, true));
        } catch (InputMismatchException e) {
            System.out.println("Error: Debe ingresar un ID numérico.");
            scanner.nextLine();
        }
    }
}

