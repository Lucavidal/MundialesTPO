package servicio;



import modelo.Equipo;
import modelo.Jugador;
import modelo.Partido;
import modelo.enums.EstadoPartido;

import java.util.List;

/**
 * Servicio de consulta de estadísticas del torneo.
 * Solo tiene responsabilidad de lectura (sin modificar estado).
 */
public class EstadisticaService {

    /**
     * Retorna el jugador con más goles del equipo.
     * @return el máximo goleador, o null si el equipo no tiene jugadores
     */
    public Jugador getMaxGoleador(Equipo equipo) {
        Jugador maxGoleador = null;
        for (Jugador j : equipo.getJugadores()) {
            if (maxGoleador == null || j.getGoles() > maxGoleador.getGoles()) {
                maxGoleador = j;
            }
        }
        return maxGoleador;
    }

    /**
     * Suma todos los goles de los partidos finalizados de la lista.
     */
    public int getTotalGoles(List<Partido> partidos) {
        int total = 0;
        for (Partido p : partidos) {
            if (p.getEstado() == EstadoPartido.FINALIZADO && p.getResultado() != null) {
                total += p.getResultado().getGolesLocal();
                total += p.getResultado().getGolesVisitante();
            }
        }
        return total;
    }

    /**
     * Cuenta cuántos partidos de la lista están finalizados.
     */
    public int getPartidosJugados(List<Partido> partidos) {
        int count = 0;
        for (Partido p : partidos) {
            if (p.getEstado() == EstadoPartido.FINALIZADO) {
                count++;
            }
        }
        return count;
    }
}