package pronosticosDeportivos;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import pronosticosDeportivos.modelo.Ronda;
import pronosticosDeportivos.modelo.Equipo;
import pronosticosDeportivos.modelo.Participante;
import pronosticosDeportivos.modelo.Partido;
import pronosticosDeportivos.modelo.Pronostico;
import pronosticosDeportivos.modelo.ResultadoEnum;

public class GestorPronosticosDeportivos {
	private Ronda[] rondas;
	private Pronostico[] pronosticos;
	private Participante[] participantes;
	private String path = "src//pronosticosDeportivos//archivos//";

	public static void main(String[] args) {
		new GestorPronosticosDeportivos();
	}

	public GestorPronosticosDeportivos() {
		participantes = new Participante[2];

		participantes[0] = new Participante("Matias");
		participantes[1] = new Participante("Ezequiel");

		this.rondas = cargarRonda();
		this.pronosticos = cargarPronosticos(rondas);
		mostrarPartidos();
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
		int i=0;
		for (Ronda ronda : rondas) {
			for (Participante participante : participantes) {
				Partido[] partidosRonda = ronda.getPartidos();
				for (Partido partido:partidosRonda) {
					Pronostico pronostico = new Pronostico();
					ResultadoEnum resultadoPronostico;
					if (pronosticos.get(i).split(";")[2].isEmpty()) {
						if (pronosticos.get(i).split(";")[4].isEmpty()) {
							resultadoPronostico = ResultadoEnum.EMPATE;
						} else {
							resultadoPronostico = ResultadoEnum.PERDEDOR;
						}
					} else {
						resultadoPronostico = ResultadoEnum.GANADOR;
					}
					pronostico.setParticipante(participante);
					pronostico.setPartido(partido);
					pronostico.setEquipo(partido.getEquipo1());
					pronostico.setResultado(resultadoPronostico);
					pronosticosRonda.add(pronostico);
					i++;
				}
			}
		}
		Pronostico[] pronosticosArray = new Pronostico[pronosticosRonda.size()];
		return pronosticosRonda.toArray(pronosticosArray);
	}

	public int[] contarPuntos() {
		int[] totalPuntos = new int[participantes.length];
		int numeroParticipante = 0;
		for (Participante participante : participantes) {
			for (Pronostico pronostico : pronosticos) {
				if (pronostico.getParticipante().equals(participante)) {
					totalPuntos[numeroParticipante] += pronostico.puntos();
				}
			}
			numeroParticipante++;
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
		System.out.println("Resultados:");

		for (Ronda ronda : rondas) {
			System.out.println("\n\nRonda: " + ronda.getNombre() + "\n");
			for (int i = 0; i < ronda.getCantidadPartidos(); i++) {
				Partido partidoPronostico = ronda.getPartidos()[i];
				Equipo equipoPronostico = partidoPronostico.getEquipo1();
				String resultado;
				if (partidoPronostico.resultado(equipoPronostico) != ResultadoEnum.EMPATE) {
					resultado = "Resultado: " + equipoPronostico.getNombre() + " "
							+ partidoPronostico.resultado(equipoPronostico);
				} else {
					resultado = "Resultado: " + partidoPronostico.resultado(equipoPronostico);
				}
				System.out.println("Partido: " + equipoPronostico.getNombre() + " - "
						+ partidoPronostico.getEquipo2().getNombre() + " | " + resultado);
			}
		}
		System.out.println("\nResultados pronsoticos: \n");
		for (int j = 0; j < participantes.length; j++) {
			System.out.println("Participante " + participantes[j].getNombre() + ": " + contarPuntos()[j]);
		}

	}

}
