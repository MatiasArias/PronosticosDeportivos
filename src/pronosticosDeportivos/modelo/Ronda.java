package pronosticosDeportivos.modelo;

public class Ronda {
	private String nombre;
	private Partido[] partidos;
	
	public int puntos() {
		return partidos.length;
	}
	public Ronda() {
		
	}
	public Ronda(String nombre, Partido[] partidos) {
		super();
		this.nombre = nombre;
		this.partidos = partidos;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Partido[] getPartidos() {
		return partidos;
	}

	public void setPartidos(Partido[] partidos) {
		this.partidos = partidos;
	}
	public int getCantidadPartidos() {
		return partidos.length;
	}
	
}
