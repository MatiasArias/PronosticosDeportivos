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
	private Ronda[] rondas;
	private Pronostico[] pronosticos;
	private String path = "src//pronosticosDeportivos//archivos//";

	public static void main(String[] args) {
		new GestorPronosticosDeportivos();
	}

	public GestorPronosticosDeportivos() {
		this.rondas = cargarRonda();
		this.pronosticos = cargarPronosticos(rondas);
		mostrarPartidos();
		// System.out.println("Total de puntos: " + contarPuntos(pronosticos));
	}

	public Ronda[] cargarRonda() {
		List<String> resultados = leerArchivo("resultados.csv");
		int cantidadRondas = (int) resultados.stream().map(s -> s.split(";")[0]).distinct().count();
		Ronda[] rondas = new Ronda[cantidadRondas];

		int i = 0;
		int j = 0;
		List<Partido> partidosRondaList = new ArrayList<Partido>();
		String nombreRonda = "";
		for (String resultado : resultados) {
			String[] vectorResultado = resultado.split(";");
			if (nombreRonda.isEmpty()) {
				nombreRonda = vectorResultado[0];
			}
			if (!vectorResultado[0].equals(nombreRonda) && !nombreRonda.isEmpty()) {
				Ronda ronda = new Ronda();
				Partido[] partidos = new Partido[partidosRondaList.size()];
				ronda.setNombre(nombreRonda);
				ronda.setPartidos(partidosRondaList.toArray(partidos));
				rondas[j] = ronda;
				partidosRondaList.clear();
				nombreRonda = vectorResultado[0];
				j++;
			}
			Equipo equipo1 = new Equipo();
			equipo1.setNombre(vectorResultado[1]);
			Equipo equipo2 = new Equipo();
			equipo2.setNombre(vectorResultado[4]);
			Partido partido = new Partido();
			partido.setEquipo1(equipo1);
			partido.setEquipo2(equipo2);
			partido.setGolesEquipo1(vectorResultado[2]);
			partido.setGolesEquipo2(vectorResultado[3]);
			partidosRondaList.add(partido);
			i++;
		}
		Ronda ronda = new Ronda();
		Partido[] partidos = new Partido[partidosRondaList.size()];
		ronda.setNombre(nombreRonda);
		ronda.setPartidos(partidosRondaList.toArray(partidos));
		rondas[j] = ronda;

		return rondas;
	}

	public Pronostico[] cargarPronosticos(Ronda[] rondas) {
		List<String> pronosticos = leerArchivo("pronostico.csv");
		List<Pronostico> pronosticosRonda = new ArrayList<Pronostico>();
		for (Ronda ronda : rondas) {
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
				pronosticosRonda.add(pronostico);
			}
		}
		Pronostico[] pronosticosArray = new Pronostico[pronosticosRonda.size()];
		return pronosticosRonda.toArray(pronosticosArray);
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
		for (Ronda ronda : rondas) {
			System.out.println("\n\nRonda: "+ronda.getNombre()+"\n");
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
}
