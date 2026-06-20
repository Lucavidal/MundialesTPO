package util;



import modelo.Equipo;
import modelo.Jugador;
import modelo.Partido;

/**
 * Clase utilitaria con métodos estáticos de validación.
 * Verifica que los objetos del modelo estén correctamente inicializados
 * antes de ser usados en el sistema.
 */
public class Validador {

    /** Valida que el equipo tenga id, nombre y país no vacíos */
    public static boolean validarEquipo(Equipo e) {
        if (e == null) {
            System.out.println("⚠ Validación fallida: equipo nulo.");
            return false;
        }
        if (e.getId() <= 0) {
            System.out.println("⚠ Validación fallida: ID de equipo inválido.");
            return false;
        }
        if (e.getNombre() == null || e.getNombre().trim().isEmpty()) {
            System.out.println("⚠ Validación fallida: nombre de equipo vacío.");
            return false;
        }
        if (e.getPais() == null || e.getPais().trim().isEmpty()) {
            System.out.println("⚠ Validación fallida: país de equipo vacío.");
            return false;
        }
        return true;
    }

    /** Valida que el jugador tenga id, nombre, dorsal y posición válidos */
    public static boolean validarJugador(Jugador j) {
        if (j == null) {
            System.out.println("⚠ Validación fallida: jugador nulo.");
            return false;
        }
        if (j.getId() <= 0) {
            System.out.println("⚠ Validación fallida: ID de jugador inválido.");
            return false;
        }
        if (j.getNombre() == null || j.getNombre().trim().isEmpty()) {
            System.out.println("⚠ Validación fallida: nombre de jugador vacío.");
            return false;
        }
        if (j.getDorsal() < 1 || j.getDorsal() > 99) {
            System.out.println("⚠ Validación fallida: dorsal fuera de rango (1-99).");
            return false;
        }
        if (j.getPosicion() == null) {
            System.out.println("⚠ Validación fallida: posición de jugador nula.");
            return false;
        }
        return true;
    }

    /** Valida que el partido tenga equipos distintos, estadio y fase definidos */
    public static boolean validarPartido(Partido p) {
        if (p == null) {
            System.out.println("⚠ Validación fallida: partido nulo.");
            return false;
        }
        if (p.getEquipoLocal() == null || p.getEquipoVisitante() == null) {
            System.out.println("⚠ Validación fallida: equipos del partido nulos.");
            return false;
        }
        if (p.getEquipoLocal().getId() == p.getEquipoVisitante().getId()) {
            System.out.println("⚠ Validación fallida: local y visitante son el mismo equipo.");
            return false;
        }
        if (p.getFase() == null) {
            System.out.println("⚠ Validación fallida: fase del partido nula.");
            return false;
        }
        return true;
    }
}