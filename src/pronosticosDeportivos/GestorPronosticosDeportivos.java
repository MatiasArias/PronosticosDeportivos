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
	
	public static void main(String[] args) {
		new GestorPronosticosDeportivos();
	}
	
	public GestorPronosticosDeportivos() {
		this.ronda = cargarRonda("src//pronosticosDeportivos//archivos//");
		this.pronosticos = cargarPronosticos("src//pronosticosDeportivos//archivos//",ronda);
		System.out.println("Total de puntos: " + contarPuntos(pronosticos));
	}

	public Ronda cargarRonda(String path) {
		Ronda ronda = new Ronda();
		List<String> resultados = new ArrayList<String>();
		try {
			 resultados = Files.readAllLines(Paths.get(path,"resultados.csv"),StandardCharsets.ISO_8859_1);
			 resultados.remove(0);
		} catch (IOException e) {
			System.out.println("Error al leer el archivo");
			e.printStackTrace();
		}
		Partido[] partidosRonda = new Partido[resultados.size()];
		int i=0;
		for (String resultado : resultados){
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
			partidosRonda[i]=partido;	
			i++;
		}
		ronda.setPartidos(partidosRonda);
		ronda.setCantidadPartidos(resultados.size());
		return ronda;
	}
	
	public Pronostico[] cargarPronosticos(String path,Ronda ronda) {
		List<String> pronosticos = new ArrayList<String>();
		Pronostico[] pronosticosRonda = new Pronostico[ronda.getCantidadPartidos()];
		try {
			 pronosticos = Files.readAllLines(Paths.get(path,"pronostico.csv"),StandardCharsets.ISO_8859_1);
			 pronosticos.remove(0);
		} catch (IOException e) {
			System.out.println("Error al leer el archivo");
			e.printStackTrace();
		}
		Partido[] partidosRonda = ronda.getPartidos();
		for (int i=0;i<ronda.getCantidadPartidos();i++) {
			Pronostico pronostico = new Pronostico();
			Partido partidoPronostico = partidosRonda[i];
			Equipo equipoPronostico = partidoPronostico.getEquipo1();
			ResultadoEnum resultadoPronostico;
			if(pronosticos.get(i).split(";")[1].isEmpty()) {
				if(pronosticos.get(i).split(";")[3].isEmpty()) {
					resultadoPronostico = ResultadoEnum.EMPATE;
				}else {
					resultadoPronostico = ResultadoEnum.PERDEDOR;
				}
			}else {
				resultadoPronostico = ResultadoEnum.GANADOR;
			}
			System.out.println("Partido: "+equipoPronostico.getNombre()+" - "+partidoPronostico.getEquipo2().getNombre()+" | Resultado: "+partidoPronostico.resultado(equipoPronostico)+" - Pronostico: "+resultadoPronostico);
			pronostico.setPartido(partidoPronostico);
			pronostico.setEquipo(equipoPronostico);
			pronostico.setResultado(resultadoPronostico);
			pronosticosRonda[i]=pronostico;
		}
		return pronosticosRonda;
	}
		
	public int contarPuntos(Pronostico[] pronosticos) {
		int totalPuntos=0;
		for(Pronostico pronostico : pronosticos){
			totalPuntos+=pronostico.puntos();
		}
	return totalPuntos;
	}
}
