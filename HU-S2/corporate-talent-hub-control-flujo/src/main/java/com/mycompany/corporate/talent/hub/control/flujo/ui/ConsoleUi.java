package com.mycompany.corporate.talent.hub.control.flujo.ui;

import com.mycompany.corporate.talent.hub.control.flujo.contoller.EmpleadoController;
import com.mycompany.corporate.talent.hub.control.flujo.modelo.Empleado;
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
                        case 1:
                            registrarEmpleadoFormulario(scanner);
                            break;
                        case 2:
                            System.out.println(controller.obtenerReporteDesempeno());
                            break;
                        case 3:
                            mostrarCategoriasSalariales();
                            break;
                        case 0:
                            sistemaActivo = false;
                            System.out.println("Sesión finalizada.");
                            break;
                        default:
                            System.out.println("Opción fuera del menú.");
                            break;
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
                     CORPORATE TALENT HUB
                =====================================
                1. Registrar empleado y calificaciones
                2. Mostrar reporte de desempeño
                3. Consultar categorías salariales
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

            double[] calificaciones = new double[3];
            for (var i = 0; i < 3; i++) {
                System.out.printf("Calificación del trimestre %d (0 a 100): ", i + 1);
                calificaciones[i] = scanner.nextDouble();
            }
            scanner.nextLine();

            String respuesta = controller.registrarEmpleado(id, nombre, edad, salario, calificaciones);
            System.out.println(respuesta);

        } catch (InputMismatchException e) {
            System.out.println("Error: Tipo de dato inválido en el formulario.");
            scanner.nextLine();
        }
    }

    private static void mostrarCategoriasSalariales() {
        System.out.println("""
                Categorías:
                - Menos de $2.000.000: JUNIOR
                - Desde $2.000.000 y menos de $4.000.000: SEMISENIOR
                - Desde $4.000.000 y menos de $7.000.000: SENIOR
                - Desde $7.000.000: LÍDER
                """);
    }
}
