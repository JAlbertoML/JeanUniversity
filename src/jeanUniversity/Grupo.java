package jeanUniversity;

import java.util.Map;

public class Grupo {
	
	private String asignatura;
	private Map<Integer, Estudiante> estudiantes;
	
	public Grupo(String asignatura, Map<Integer, Estudiante> estudiantes) {
		super();
		this.asignatura = asignatura;
		this.estudiantes = estudiantes;
	}

	public String getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(String asignatura) {
		this.asignatura = asignatura;
	}

	public Map<Integer, Estudiante> getEstudiantes() {
		return estudiantes;
	}

	public void setEstudiantes(Map<Integer, Estudiante> estudiantes) {
		this.estudiantes = estudiantes;
	}
}
