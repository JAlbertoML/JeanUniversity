package jeanUniversity;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Administrador {

	private static Map<Integer, Profesor> profesores = new HashMap<Integer, Profesor>();
	private static Scanner scanner = new Scanner(System.in);

	public static void ejecutar() {
		Integer opcion;
		Character repetir = 's';
		do {
			System.out.println("\nOpciones de administrador disponibles:");
			System.out.println(
					"\t1.- Agregar profesor\n\t2.- Agregar grupo a profesor\n\t3.- Imprimir reportes\n\t4.- Volver");
			System.out.print("Elige una opción: ");
			opcion = scanner.nextInt();
			scanner.nextLine();
			switch (opcion) {
			case 1:
				agregarProfesor();
				break;

			case 2:
				agregarGrupoAProfesor();
				break;

			case 3:
				imprimirReportes();
				break;

			case 4:
				Main.main(null);
				break;

			default:
				System.err.println("La opción que elegiste no es válida");
				break;
			}
			System.out.print("Deseas volver al menú de administrador? (S: si / N: no): ");
			repetir = scanner.nextLine().charAt(0);
		} while (repetir == 'S' || repetir == 's');
	}

	private static void agregarProfesor() {
		String date;
		String[] dateSplited = new String[3];
		LocalDate fechaIngreso = null;
		System.out.println("\nAGREGAR PROFESOR");
		System.out.print("\tIngresa el id: ");
		Integer id = scanner.nextInt();
		scanner.nextLine();
		System.out.print("\tIngresa el nombre: ");
		String nombre = scanner.nextLine();
		System.out.print("\tIngresa el apellido: ");
		String apellido = scanner.nextLine();
		do {
			System.out.print("\tIngresa la fecha de ingreso en formato DD/MM/AAAA (incluyendo las diagonales): ");
			date = scanner.nextLine();
			if (date.length() == 10) {
				dateSplited = date.split("/");
				try {
					fechaIngreso = LocalDate.of(Integer.parseInt(dateSplited[2]), Integer.parseInt(dateSplited[1]),
							Integer.parseInt(dateSplited[0]));
					if (!fechaIngreso.isAfter(LocalDate.now())) {
						break;
					} else {
						System.err.println("La fecha de ingreso de un profesor no puede ser posterior a hoy.");
					}
				} catch (DateTimeException e) {
					System.err.println("Asegurate de seguir el formato indicado para la fecha de ingreso.");
				}
			} else {
				System.err.println("Asegurate de seguir el formato indicado para la fecha de ingreso.");
			}
		} while (true);
		profesores.put(profesores.size() + 1, new Profesor(id, nombre, apellido, fechaIngreso));
		System.out.println("El profesor se ha agregado con éxito.");
	}

	private static void agregarGrupoAProfesor() {
		if (profesores.size() == 0) {
			System.err.println("\nPara asignarle un grupo a un profesor, primero debes agregar profesores.");
		} else {
			System.out.println("\nLista de profesores:");
			for (int i = 1; i <= profesores.size(); i++) {
				System.out.println("\t" + i + ".- " + profesores.get(i).nombre + " " + profesores.get(i).apellido);
			}
			System.out.print("Elige un profesor: ");
			Integer eleccion = scanner.nextInt();
			scanner.nextLine();
			if (eleccion > profesores.size()) {
				System.err.println("La opción que elgiste no es valida.");
			} else {
				profesores.get(eleccion).cargarDatos();
			}
		}
	}

	public static void asignarCalificaciones() {
		if (profesores.size() == 0) {
			System.err.println("\nNo hay profesores disponibles.");
		} else {
			System.out.println("Listado de profesores:");
			for (int i = 1; i <= profesores.size(); i++) {
				System.out.println(
						"\t" + i + ".- " + profesores.get(i).getNombre() + " " + profesores.get(i).getApellido());
			}
			System.out.print("Ingresa el numero que tiene tu nombre: ");
			Integer opcion = scanner.nextInt();
			scanner.nextLine();
			if (opcion > profesores.size()) {
				System.err.println("La opción que elgiste no es valida.");
			} else {
				profesores.get(opcion).calcularPromedios();
			}
		}
	}

	private static void imprimirReportes() {
		Integer eleccion;
		Character repetir = 's';
		do {
			System.out.println("\nOpciones para reportes disponibles:");
			System.out.println("\t1.- Profesores\n\t2.- Estudiantes\n\t3.- Volver");
			System.out.print("Elige una opción: ");
			eleccion = scanner.nextInt();
			scanner.nextLine();
			switch (eleccion) {
			case 1:
				if (profesores.size() == 0) {
					System.err.println("\nNo hay profesores disponibles.");
				} else {
					profesores.get(elegirProfesor()).imprimirReportes();
				}
				break;
			case 2:
				elegirEstudiante();
				break;

			case 3:
				ejecutar();
				break;

			default:
				System.err.println("La opción elegida no es valida.");
				break;
			}
			System.out.print("Deseas volver al menú de reportes? (S: Si / N: No): ");
			repetir = scanner.nextLine().charAt(0);
		} while (repetir == 's' || repetir == 'S');
	}

	private static Integer elegirProfesor() {
		Boolean repetir = true;
		Integer eleccion;
		do {
			System.out.println("Lista de profesores: ");
			for (int i = 1; i <= profesores.size(); i++) {
				System.out.println(
						"\t" + i + ".- " + profesores.get(i).getNombre() + " " + profesores.get(i).getApellido());
			}
			System.out.print("Elige un profesor: ");
			eleccion = scanner.nextInt();
			scanner.nextLine();
			if (eleccion <= 0 || eleccion > profesores.size()) {
				System.err.println("La opción que elegiste no es valida");
			} else {
				repetir = false;
			}
		} while (repetir);
		return eleccion;
	}

	public static void elegirEstudiante() {
		Boolean repetirp = true, repetirg = true, repetire = true;
		Integer eleccionProfe = null, eleccionGrupo = null, eleccionEtudiante = null;
		do {
			System.out.println("Lista de profesores: ");
			for (int i = 1; i <= profesores.size(); i++) {
				System.out.println(
						"\t" + i + ".- " + profesores.get(i).getNombre() + " " + profesores.get(i).getApellido());
			}
			System.out.print("Elige un profesor: ");
			eleccionProfe = scanner.nextInt();
			scanner.nextLine();
			if (eleccionProfe <= 0 || eleccionProfe > profesores.size()) {
				System.err.println("La opción que elegiste no es valida");
			} else {
				if (profesores.get(eleccionProfe).getGrupos().size() == 0) {
					System.err.println("Este profesor no tiene grupos.");
				} else {
					do {
						System.out.println("Grupos de " + profesores.get(eleccionProfe).getNombre() + " "
								+ profesores.get(eleccionProfe).getApellido() + ":");
						for (int i = 1; i <= profesores.get(eleccionProfe).getGrupos().size(); i++) {
							System.out.println("\t" + i + ".- "
									+ profesores.get(eleccionProfe).getGrupos().get(i).getAsignatura());
						}
						System.out.print("Elige un grupo: ");
						eleccionGrupo = scanner.nextInt();
						scanner.nextLine();
						if (eleccionGrupo <= 0 || eleccionGrupo > profesores.get(eleccionProfe).getGrupos().size()) {
							System.err.println("La opción que elegiste no es valida");
						} else {
							do {
								System.out.println("Estudiantes del grupo:");
								for (int i = 1; i <= profesores.get(eleccionProfe).getGrupos().get(eleccionGrupo)
										.getEstudiantes().size(); i++) {
									System.out.println("\t" + i + ".- "
											+ profesores.get(eleccionProfe).getGrupos().get(eleccionGrupo)
													.getEstudiantes().get(i).getNombre()
											+ " " + profesores.get(eleccionProfe).getGrupos().get(eleccionGrupo)
													.getEstudiantes().get(i).getApellido());
								}
								System.out.print("Elige un estudiante: ");
								eleccionEtudiante = scanner.nextInt();
								scanner.nextLine();
								if (eleccionEtudiante <= 0 || eleccionEtudiante > profesores.get(eleccionProfe)
										.getGrupos().get(eleccionGrupo).getEstudiantes().size()) {
									System.err.println("La opción que elegiste no es valida");
								} else {
									repetire = false;
								}
							} while (repetire);
							repetirg = false;
						}
					} while (repetirg);
				}
				repetirp = false;
			}
		} while (repetirp);
		if (eleccionEtudiante == null || eleccionGrupo == null || eleccionProfe == null) {
			System.err.println("error");
		} else {
			profesores.get(eleccionProfe).getGrupos().get(eleccionGrupo).getEstudiantes().get(eleccionEtudiante)
					.imprimirReportes();
		}
	}

	public Map<Integer, Profesor> getProfesores() {
		return profesores;
	}

	public void setProfesores(Map<Integer, Profesor> profesores) {
		Administrador.profesores = profesores;
	}
}
