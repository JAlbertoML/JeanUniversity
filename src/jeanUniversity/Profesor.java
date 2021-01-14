package jeanUniversity;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Profesor extends Persona implements IPersona {

	private Map<Integer, Grupo> grupos = new HashMap<Integer, Grupo>();
	private Scanner scanner = new Scanner(System.in);

	public Profesor(Integer id, String nombre, String apellido, LocalDate fechaIngreso) {
		super(id, nombre, apellido, fechaIngreso);
	}

	@Override
	public void cargarDatos() {
		Map<Integer, Estudiante> estudiantes = new HashMap<Integer, Estudiante>();
		Character respuesta = 's';
		String date;
		String[] dateSplited = new String[3];
		LocalDate fechaIngreso = null;
		System.out.print("\tIngresa la asignatura del grupo: ");
		String asignatura = scanner.nextLine();
		System.out.println("Ahora hay que agregar estudiantes al grupo.");
		do {
			System.out.print("\tIngresa el id del estudiante: ");
			Integer id = scanner.nextInt();
			scanner.nextLine();
			System.out.print("\tIngresa el nombre del estudiante: ");
			String nombre = scanner.nextLine();
			System.out.print("\tIngresa el apellido del estudiante: ");
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
			System.out.print("\tIngresa la carrera del estudiante: ");
			String carrera = scanner.nextLine();
			System.out.print("\tIngresa el numero de créditos del estudiante: ");
			Integer unidadCreditos = scanner.nextInt();
			scanner.nextLine();
			System.out.print("\tIngresa el semestre del estudiante: ");
			Integer semestre = scanner.nextInt();
			scanner.nextLine();
			estudiantes.put(estudiantes.size() + 1,
					new Estudiante(id, nombre, apellido, fechaIngreso, asignatura, carrera, unidadCreditos, semestre));
			System.out.print("Deseas agregar otro estudiante? (S: Si / N: No): ");
			respuesta = scanner.nextLine().charAt(0);
		} while (respuesta == 's' || respuesta == 'S');
		grupos.put(grupos.size() + 1, new Grupo(asignatura, estudiantes));
	}

	@Override
	public void imprimirReportes() {
		FileWriter fichero = null;
		PrintWriter pw = null;
		try {
			fichero = new FileWriter("/home/alaguna/Documents/Profesor (" + nombre + " " + apellido + ").txt");
			pw = new PrintWriter(fichero);
			pw.println("Id: " + getId());
			pw.println("Nombre: " + nombre);
			pw.println("Apellido: " + apellido);
			pw.println("Fecha de ingreso: " + fechaIngreso);
			pw.print("Grupos y alumnos: ");
			if (grupos.size() == 0) {
				pw.print("No tiene");
			} else {
				pw.print("\n");
				for (int i = 1; i <= grupos.size(); i++) {
					pw.println("\tGrupo de " + grupos.get(i).getAsignatura());
					for (int j = 1; j <= grupos.get(i).getEstudiantes().size(); j++) {
						pw.println("\t\t-" + grupos.get(i).getEstudiantes().get(j).getNombre() + " "
								+ grupos.get(i).getEstudiantes().get(j).getApellido());
					}
				}
			}
			System.out.println("El reporte se imprimió con éxito en la carpeta de documentos");
		} catch (Exception e) {
			System.err.println("El reporte no se pudo imprimir correctamente");
			e.printStackTrace();
		} finally {
			try {
				if (null != fichero)
					fichero.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	@Override
	public void calcularPromedios() {
		Integer opcion, examenes, practicas, valorExamenes, valorPracticas;
		Double promedio = 0.0, calEx = 0.0, calPrac = 0.0;
		Boolean repetir = true;
		if (grupos.size() == 0) {
			System.err.println("\nEste profesor no tiene grupos.");
		} else {
			System.out.println("Grupos que tiene:");
			for (int i = 1; i <= grupos.size(); i++) {
				System.out.println("\t" + i + ".- " + grupos.get(i).getAsignatura());
			}
			System.out.print("Elija un grupo: ");
			opcion = scanner.nextInt();
			scanner.nextLine();
			if (opcion > grupos.size() || opcion < 1) {
				System.err.println("La opción que elegiste no es valida.");
			} else {
				do {
					System.out.print("Numero de examanes realizados en este grupo: ");
					examenes = scanner.nextInt();
					scanner.nextLine();
					if (examenes == 0) {
						valorExamenes = 0;
					} else {
						System.out
								.print("Ingresa el porcentaje de valor de los examenes sobre la calificación final: ");
						valorExamenes = scanner.nextInt();
						scanner.nextLine();
					}
					System.out.print("Numero de practicas realizadas en este grupo: ");
					practicas = scanner.nextInt();
					scanner.nextLine();
					if (practicas == 0) {
						valorPracticas = 0;
					} else {
						System.out
								.print("Ingresa el porcentaje de valor de las prácticas sobre la calificación final: ");
						valorPracticas = scanner.nextInt();
						scanner.nextLine();
					}
					if (valorExamenes + valorPracticas == 0) {
						break;
					} else if (valorExamenes + valorPracticas == 100) {
						repetir = false;
					} else {
						System.err.println(
								"La suma del porcentaje de los examenes y el porcentaje de las practicas debe ser igual a 100");
					}
				} while (repetir);
				if (valorExamenes + valorPracticas == 0) {
					System.err.println("No se puede evaluar este grupo pues no se trabajó");
				} else {
					for (int i = 1; i <= grupos.get(opcion).getEstudiantes().size(); i++) {
						calEx = 0.0;
						calPrac = 0.0;
						for (int j = 0; j < examenes; j++) {
							System.out.print("Ingresa la calificación de "
									+ grupos.get(opcion).getEstudiantes().get(i).getNombre() + " "
									+ grupos.get(opcion).getEstudiantes().get(i).getApellido() + " en el examen "
									+ (j + 1) + " (sobre una escala de 20): ");
							calEx += scanner.nextDouble();
						}
						for (int j = 0; j < practicas; j++) {
							System.out.print("Ingresa la calificación de "
									+ grupos.get(opcion).getEstudiantes().get(i).getNombre() + " "
									+ grupos.get(opcion).getEstudiantes().get(i).getApellido() + " en la practica "
									+ (j + 1) + " (sobre una escala de 20): ");
							calPrac += scanner.nextDouble();
						}
						promedio = ((((double) calEx / (double) examenes) * ((double) valorExamenes / 100))
								+ ((double) calPrac / (double) practicas) * ((double) valorPracticas / 100));
						grupos.get(opcion).getEstudiantes().get(i).setPromedio(promedio);
						System.out.println("La calificación del alumno es: " + (promedio.toString()));
					}
				}
			}
		}
	}

	public Map<Integer, Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(Map<Integer, Grupo> grupos) {
		this.grupos = grupos;
	}
}
