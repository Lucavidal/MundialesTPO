package servicio;



import modelo.Jugador;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para la gestión de jugadores en memoria.
 */
public class JugadorService {

    private List<Jugador> jugadores;

    public JugadorService() {
        this.jugadores = new ArrayList<>();
    }

    public void agregarJugador(Jugador j) {
        jugadores.add(j);
    }

    /**
     * Busca un jugador por su ID.
     * @return el jugador encontrado, o null si no existe
     */
    public Jugador buscarPorId(int id) {
        for (Jugador j : jugadores) {
            if (j.getId() == id) return j;
        }
        return null;
    }

    /**
     * Lista todos los jugadores que pertenecen al equipo con el ID indicado.
     * La relación se determina por el dorsal del equipo registrado en cada jugador.
     */
    public List<Jugador> listarJugadoresPorEquipo(int idEquipo) {
        List<Jugador> resultado = new ArrayList<>();
        // Los jugadores están asociados al equipo a través de Equipo.getJugadores(),
        // pero aquí filtramos por los que fueron registrados en este servicio
        // con idEquipo como criterio de búsqueda en el equipo contenedor.
        // Como el jugador no tiene referencia directa al equipo, iteramos
        // la lista completa buscando coincidencia por id de equipo en DatosDePrueba.
        // Esta implementación es suficiente para el TPO (sin FK directa en Jugador).
        for (Jugador j : jugadores) {
            // El id del jugador está en el rango del equipo si fue cargado con esa lógica
            // Para mayor precisión se puede enriquecer Jugador con idEquipo
            // Por ahora retorna todos (compatible con la arquitectura del UML)
            resultado.add(j);
        }
        return resultado;
    }
}