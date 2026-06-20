package modelo;



import java.util.HashMap;
import java.util.Map;

/**
 * Ranking FIFA de los 48 equipos del Mundial 2026.
 * Se usa para ponderar la probabilidad de ganar en la simulacion:
 * equipos con mayor puntaje tienen mas chances de ganar.
 */
public class RankingFifa {

    private static final Map<String, Integer> RANKING = new HashMap<>();

    static {
        RANKING.put("Argentina",       1874);
        RANKING.put("Espana",          1873);
        RANKING.put("Francia",         1869);
        RANKING.put("Inglaterra",      1864);
        RANKING.put("Portugal",        1763);
        RANKING.put("Brasil",          1756);
        RANKING.put("Marruecos",       1756);
        RANKING.put("Paises Bajos",    1751);
        RANKING.put("Alemania",        1731);
        RANKING.put("Croacia",         1712);
        RANKING.put("Italia",          1700);
        RANKING.put("Colombia",        1697);
        RANKING.put("Mexico",          1682);
        RANKING.put("Senegal",         1679);
        RANKING.put("Uruguay",         1670);
        RANKING.put("Estados Unidos",  1668);
        RANKING.put("Japon",           1661);
        RANKING.put("Suiza",           1658);
        RANKING.put("Iran",            1650);
        RANKING.put("Dinamarca",       1641);
        RANKING.put("Turquia",         1619);
        RANKING.put("Ecuador",         1610);
        RANKING.put("Belgica",         1605);
        RANKING.put("Austria",         1600);
        RANKING.put("Australia",       1598);
        RANKING.put("Polonia",         1595);
        RANKING.put("Corea del Sur",   1590);
        RANKING.put("Canada",          1585);
        RANKING.put("Noruega",         1580);
        RANKING.put("Tunez",           1575);
        RANKING.put("Egipto",          1570);
        RANKING.put("Uzbekistan",      1560);
        RANKING.put("Argelia",         1555);
        RANKING.put("Ghana",           1550);
        RANKING.put("Costa de Marfil", 1545);
        RANKING.put("Paraguay",        1540);
        RANKING.put("Bolivia",         1520);
        RANKING.put("Arabia Saudita",  1510);
        RANKING.put("Sudafrica",       1505);
        RANKING.put("Jamaica",         1500);
        RANKING.put("Panama",          1498);
        RANKING.put("Escocia",         1495);
        RANKING.put("Gales",           1490);
        RANKING.put("Jordania",        1480);
        RANKING.put("Nueva Zelanda",   1475);
        RANKING.put("Curazao",         1460);
        RANKING.put("Cabo Verde",      1455);
        RANKING.put("Haiti",           1430);
        RANKING.put("Catar",           1425);
        RANKING.put("Irak",            1420);
    }

    /**
     * Retorna el puntaje FIFA del equipo.
     * Si no se encuentra, retorna 1500 como valor neutro.
     */
    public static int getPuntaje(String nombreEquipo) {
        return RANKING.getOrDefault(nombreEquipo, 1500);
    }

    /**
     * Calcula la probabilidad de que el equipo local gane (0.0 a 1.0).
     *
     * Usa la formula ELO simplificada:
     *   P(local) = 1 / (1 + 10^((puntos_visitante - puntos_local) / 400))
     *
     * Con un leve bonus de localidad del 5% para el equipo local.
     * Resultado: valor entre 0.20 y 0.80 (nunca totalmente determinista).
     */
    public static double probabilidadLocal(String nombreLocal, String nombreVisitante) {
        double pLocal     = getPuntaje(nombreLocal);
        double pVisitante = getPuntaje(nombreVisitante);

        // Formula ELO
        double prob = 1.0 / (1.0 + Math.pow(10.0, (pVisitante - pLocal) / 400.0));

        // Bonus localidad leve (+5% al local)
        prob = prob * 0.95 + 0.05;

        // Clamp: minimo 20%, maximo 80% — siempre hay sorpresa posible
        prob = Math.max(0.20, Math.min(0.80, prob));

        return prob;
    }

    /**
     * Calcula la diferencia de goles esperada segun el diferencial de ranking.
     * Sirve para generar resultados mas realistas.
     * Retorna entre 0 y 2.
     */
    public static int diferencialEsperado(String nombreLocal, String nombreVisitante) {
        int diff = Math.abs(getPuntaje(nombreLocal) - getPuntaje(nombreVisitante));
        if (diff > 300) return 2;
        if (diff > 150) return 1;
        return 0;
    }
}
