package pronosticosDeportivos;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import pronosticosDeportivos.modelo.Ronda;
import pronosticosDeportivos.modelo.Equipo;
import pronosticosDeportivos.modelo.Partido;
import pronosticosDeportivos.modelo.Pronostico;
import pronosticosDeportivos.modelo.ResultadoEnum;

public class GestorPronosticosDeportivos {
	private Ronda ronda;
	private Pronostico[] pronosticos;
	private String path = "src//pronosticosDeportivos//archivos//";

	public static void main(String[] args) {
		new GestorPronosticosDeportivos();
	}

	public GestorPronosticosDeportivos() {
		this.ronda = cargarRonda();
		this.pronosticos = cargarPronosticos(ronda);
		mostrarPartidos();
		System.out.println("Total de puntos: " + contarPuntos(pronosticos));
	}

	public Ronda cargarRonda() {
		Ronda ronda = new Ronda();
		List<String> resultados = leerArchivo("resultados.csv");
		Partido[] partidosRonda = new Partido[resultados.size()];
		
		int i = 0;
		for (String resultado : resultados) {
			String[] vectorResultado = resultado.split(";");
			
			Equipo equipo1 = new Equipo();
			equipo1.setNombre(vectorResultado[0]);
			Equipo equipo2 = new Equipo();
			equipo2.setNombre(vectorResultado[3]);
			
			Partido partido = new Partido();
			partido.setEquipo1(equipo1);
			partido.setEquipo2(equipo2);
			partido.setGolesEquipo1(vectorResultado[1]);
			partido.setGolesEquipo2(vectorResultado[2]);
			partidosRonda[i] = partido;
			i++;
		}
		ronda.setPartidos(partidosRonda);
		return ronda;
	}

	public Pronostico[] cargarPronosticos(Ronda ronda) {
		List<String> pronosticos = leerArchivo("pronostico.csv");
		Pronostico[] pronosticosRonda = new Pronostico[ronda.getCantidadPartidos()];
		Partido[] partidosRonda = ronda.getPartidos();

		for (int i = 0; i < ronda.getCantidadPartidos(); i++) {
			Pronostico pronostico = new Pronostico();
			ResultadoEnum resultadoPronostico;
			if (pronosticos.get(i).split(";")[1].isEmpty()) {
				if (pronosticos.get(i).split(";")[3].isEmpty()) {
					resultadoPronostico = ResultadoEnum.EMPATE;
				} else {
					resultadoPronostico = ResultadoEnum.PERDEDOR;
				}
			} else {
				resultadoPronostico = ResultadoEnum.GANADOR;
			}
			pronostico.setPartido(partidosRonda[i]);
			pronostico.setEquipo(partidosRonda[i].getEquipo1());
			pronostico.setResultado(resultadoPronostico);
			pronosticosRonda[i] = pronostico;
		}
		return pronosticosRonda;
	}

	public int contarPuntos(Pronostico[] pronosticos) {
		int totalPuntos = 0;
		for (Pronostico pronostico : pronosticos) {
			totalPuntos += pronostico.puntos();
		}
		return totalPuntos;
	}

	public List<String> leerArchivo(String nombreArchivo) {
		List<String> contenidoArchivo = new ArrayList<String>();
		try {
			contenidoArchivo = Files.readAllLines(Paths.get(path, nombreArchivo), StandardCharsets.ISO_8859_1);
			contenidoArchivo.remove(0);
		} catch (IOException e) {
			System.out.println("Error al leer el archivo");
			e.printStackTrace();
		}
		return contenidoArchivo;
	}

	public void mostrarPartidos() {
		for (int i = 0; i < ronda.getCantidadPartidos(); i++) {
			Partido partidoPronostico = ronda.getPartidos()[i];
			Equipo equipoPronostico = partidoPronostico.getEquipo1();
			System.out.println(
					"Partido: " + equipoPronostico.getNombre() + " - " + partidoPronostico.getEquipo2().getNombre()
							+ " | Resultado: " + partidoPronostico.resultado(equipoPronostico) + " - Pronostico: "
							+ pronosticos[i].getResultado());
		}
	}
}
