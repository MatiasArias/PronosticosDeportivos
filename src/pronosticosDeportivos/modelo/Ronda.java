package pronosticosDeportivos.modelo;

public class Ronda {
	private String nro;
	private Partido[] partidos;
	private int cantidadPartidos;
	
	public int puntos() {
		return partidos.length;
	}
	public Ronda() {
		
	}
	public Ronda(String nro, Partido[] partidos) {
		super();
		this.nro = nro;
		this.partidos = partidos;
	}

	public String getNro() {
		return nro;
	}

	public void setNro(String nro) {
		this.nro = nro;
	}

	public Partido[] getPartidos() {
		return partidos;
	}

	public void setPartidos(Partido[] partidos) {
		this.partidos = partidos;
	}
	public int getCantidadPartidos() {
		return cantidadPartidos;
	}
	public void setCantidadPartidos(int cantidadPartidos) {
		this.cantidadPartidos = cantidadPartidos;
	}
	
}
