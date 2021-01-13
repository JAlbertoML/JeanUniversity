package jeanUniversity;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new java.util.Scanner(System.in);
		Integer opcion;
		Character repetir = 's';
		do {
			System.out.println("\nRoles disponibles:");
			System.out.println("\t1.- Administrador\n\t2.- Profesor\n\t3.- Salir");
			System.out.print("Elije un rol: ");
			opcion = scanner.nextInt();
			switch (opcion) {
			case 1:
				Administrador.ejecutar();
				break;

			case 2:
				Administrador.asignarCalificaciones();
				break;

			case 3:
				System.out.println("¡¡¡BYE!!!");
				System.exit(0);
				break;

			default:
				System.err.println("La opción que elegiste no es válida");
				break;
			}
			System.out.print("Deseas volver al menú principal? (S: si / N: no): ");
			repetir = scanner.next().charAt(0);
		} while (repetir == 'S' || repetir == 's');
		scanner.close();
	}
}
