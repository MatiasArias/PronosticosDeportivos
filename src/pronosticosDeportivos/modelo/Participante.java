package pronosticosDeportivos.modelo;

public class Participante {
	String nombre;
	Pronostico[] pronosticos;

	public Participante() {

	}

	public Participante(String nombre, Pronostico[] pronosticos) {
		super();
		this.nombre = nombre;
		this.pronosticos = pronosticos;
	}
	public Participante(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Pronostico[] getPronosticos() {
		return pronosticos;
	}

	public void setPronosticos(Pronostico[] pronosticos) {
		this.pronosticos = pronosticos;
	}

}
