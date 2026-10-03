package crudalumnosnio;

import java.util.List;
import java.util.Scanner;

public class CRUDAlumnosNIO {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            AlumnoDAO dao = new AlumnoDAO();
            int opcion = 0;
            
            do {
                System.out.println("\n=== MENÚ GESTIÓN DE ALUMNOS (JAVA NIO) ===");
                System.out.println("1. Registrar nuevo alumno");
                System.out.println("2. Ver todos los alumnos");
                System.out.println("3. Buscar alumno por ID");
                System.out.println("4. Actualizar nombre de alumno");
                System.out.println("5. Eliminar alumno");
                System.out.println("6. Salir");
                System.out.print("Seleccione una opción: ");
                
                try {
                    opcion = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Ingrese un número válido.");
                    continue;
                }
                
                switch (opcion) {
                    case 1 -> {
                        System.out.print("Ingrese ID: ");
                        String idReg = scanner.nextLine();
                        System.out.print("Ingrese Nombre: ");
                        String nombreReg = scanner.nextLine();
                        if (dao.registrar(new Alumno(idReg, nombreReg))) {
                            System.out.println("¡Alumno registrado con éxito!");
                        } else {
                            System.out.println("Error: El ID ya existe.");
                        }
                    }
                    case 2 -> {
                        List<String> alumnos = dao.obtenerTodos();
                        if (alumnos.isEmpty()) {
                            System.out.println("No hay alumnos registrados.");
                        } else {
                            for (String a : alumnos) {
                                System.out.println(a);
                            }
                        }
                    }
                    case 3 -> {
                        System.out.print("Ingrese ID a buscar: ");
                        String idBusq = scanner.nextLine();
                        String resultado = dao.buscarPorId(idBusq);
                        if (resultado != null) {
                            System.out.println("Encontrado: " + resultado);
                        } else {
                            System.out.println("Alumno no encontrado.");
                        }
                    }
                    case 4 -> {
                        System.out.print("Ingrese ID del alumno a modificar: ");
                        String idAct = scanner.nextLine();
                        if (dao.buscarPorId(idAct) != null) {
                            System.out.print("Ingrese nuevo nombre: ");
                            String nuevoNombre = scanner.nextLine();
                            dao.actualizar(idAct, nuevoNombre);
                            System.out.println("¡Alumno actualizado con éxito!");
                        } else {
                            System.out.println("Error: Alumno no encontrado.");
                        }
                    }
                    case 5 -> {
                        System.out.print("Ingrese ID del alumno a eliminar: ");
                        String idElim = scanner.nextLine();
                        if (dao.eliminar(idElim)) {
                            System.out.println("¡Alumno eliminado con éxito!");
                        } else {
                            System.out.println("Error: Alumno no encontrado.");
                        }
                    }
                    case 6 -> System.out.println("Cerrando programa...");
                    default -> System.out.println("Opción no válida.");
                }
            } while (opcion != 6);
        }
    }
}
