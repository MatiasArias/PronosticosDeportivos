package pronosticosDeportivos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import pronosticosDeportivos.modelo.Equipo;
import pronosticosDeportivos.modelo.Participante;
import pronosticosDeportivos.modelo.Partido;
import pronosticosDeportivos.modelo.Pronostico;
import pronosticosDeportivos.modelo.ResultadoEnum;
import pronosticosDeportivos.modelo.Ronda;

class GestorPronosticosDeportivosTest {

	@Test
	void testPuntajeRondasConsecutivas() {
		//Equipos
		Equipo argentina = new Equipo("Argentina","Futbol");
		Equipo arabiaSaudita = new Equipo("Arabia Saudita","Futbol");
		Equipo polonia= new Equipo("Polonia","Futbol");
		Equipo mexico= new Equipo("Mexico","Futbol");
		// 1er Ronda
		Partido partido1Ronda1 = new Partido(argentina,arabiaSaudita,1,2);
		Partido partido2Ronda1 = new Partido(polonia,mexico,0,0);
		Partido[] vectorPartidosRonda1 = {partido1Ronda1,partido2Ronda1};
		Ronda ronda1 = new Ronda("1", vectorPartidosRonda1) ;
		// 2da ronda
		Partido partido1Ronda2 = new Partido(argentina,mexico,2,0);
		Partido partido2Ronda2 = new Partido(arabiaSaudita,polonia,0,2);
		Partido[] vectorPartidosRonda2 = {partido1Ronda2,partido2Ronda2};
		Ronda ronda2 = new Ronda("2", vectorPartidosRonda1) ;
		//Participante
		Participante participante = new Participante();
		Pronostico pronostico1 = new Pronostico(partido1Ronda1,argentina,ResultadoEnum.GANADOR);
		Pronostico pronostico2 = new Pronostico(partido2Ronda1,polonia,ResultadoEnum.EMPATE);
		Pronostico pronostico3 = new Pronostico(partido1Ronda2,argentina,ResultadoEnum.GANADOR);
		Pronostico pronostico4 = new Pronostico(partido2Ronda2,arabiaSaudita,ResultadoEnum.GANADOR);
		Pronostico[] pronostico = {pronostico1,pronostico2,pronostico3,pronostico4};
		participante.setPronosticos(pronostico);
		assertEquals(2, participante.getPuntaje());
	}

}
