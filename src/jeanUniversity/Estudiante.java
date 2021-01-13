package jeanUniversity;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;

public class Estudiante extends Persona implements IPersona {

	private String nombreMateria;
	private String carrera;
	private Integer unidadCreditos;
	private Integer semestre;
	private Double promedio = null;

	public Estudiante(Integer id, String nombre, String apellido, LocalDate fechaIngreso, String nombreMateria,
			String carrera, Integer unidadCreditos, Integer semestre) {
		super(id, nombre, apellido, fechaIngreso);
		this.nombreMateria = nombreMateria;
		this.carrera = carrera;
		this.unidadCreditos = unidadCreditos;
		this.semestre = semestre;
	}

	@Override
	public void cargarDatos() {

	}

	@Override
	public void imprimirReportes() {
		FileWriter fichero = null;
		PrintWriter pw = null;
		try {
			fichero = new FileWriter("/home/alaguna/Documents/Estudiante (" + nombre + " " + apellido + ") (" + nombreMateria + ").txt");
			pw = new PrintWriter(fichero);
			pw.println("Id: " + getId());
			pw.println("Nombre: " + nombre);
			pw.println("Apellido: " + apellido);
			pw.println("Fecha de ingreso: " + fechaIngreso);
			pw.println("Asignatura: " + nombreMateria);
			pw.println("Carrera: " + carrera);
			pw.println("Creditos: " + unidadCreditos);
			pw.println("Semestre: " + semestre);
			pw.print("Calificación: ");
			if(promedio == null) {
				pw.print("Aún sin evaluación");
			} else {
				pw.print(promedio);
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
		// TODO Auto-generated method stub
	}

	public String getNombreMateria() {
		return nombreMateria;
	}

	public void setNombreMateria(String nombreMateria) {
		this.nombreMateria = nombreMateria;
	}

	public String getCarrera() {
		return carrera;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	public Integer getUnidadCreditos() {
		return unidadCreditos;
	}

	public void setUnidadCreditos(Integer unidadCreditos) {
		this.unidadCreditos = unidadCreditos;
	}

	public Integer getSemestre() {
		return semestre;
	}

	public void setSemestre(Integer semestre) {
		this.semestre = semestre;
	}

	public Double getPromedio() {
		return promedio;
	}

	public void setPromedio(Double promedio) {
		this.promedio = promedio;
	}
}
