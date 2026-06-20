package patrones.strategy;

import modelo.Jugador;
import modelo.Partido;
import modelo.RankingFifa;
import modelo.Resultado;
import modelo.enums.FaseTorneo;
import modelo.enums.PosicionJugador;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Simula resultados con peso FIFA y distribuye los goles
 * entre los jugadores del equipo (solo DELANTERO y MEDIOCAMPISTA).
 * Los delanteros tienen 3x mas probabilidad que los mediocampistas.
 */
public class ResultadoAleatorioStrategy implements IEstrategiaResultado {

    private Random random;

    public ResultadoAleatorioStrategy() {
        this.random = new Random(System.nanoTime());
    }

    @Override
    public Resultado simularResultado(Partido p) {
        String nombreLocal     = p.getEquipoLocal().getNombre();
        String nombreVisitante = p.getEquipoVisitante().getNombre();

        double probLocal   = RankingFifa.probabilidadLocal(nombreLocal, nombreVisitante);
        int    diferencial = RankingFifa.diferencialEsperado(nombreLocal, nombreVisitante);

        int g1 = random.nextInt(4);
        int g2 = random.nextInt(4);

        double dado = random.nextDouble();

        if (dado < probLocal) {
            if (g1 <= g2) {
                g1 = g2 + 1 + (diferencial > 0 ? random.nextInt(diferencial + 1) : 0);
            }
        } else if (dado > probLocal + 0.10) {
            if (g2 <= g1) {
                g2 = g1 + 1 + (diferencial > 0 ? random.nextInt(diferencial + 1) : 0);
            }
        }

        g1 = Math.min(g1, 7);
        g2 = Math.min(g2, 7);

        // ── Distribuye goles entre jugadores ──────────────────────
        distribuirGoles(p.getEquipoLocal().getJugadores(), g1);
        distribuirGoles(p.getEquipoVisitante().getJugadores(), g2);

        boolean esEliminatoria = p.getFase() != FaseTorneo.GRUPOS;

        if (!esEliminatoria) {
            return new Resultado(g1, g2);
        }

        if (g1 != g2) {
            return new Resultado(g1, g2);
        }

        // Tiempo extra
        boolean seRompeEnAlargue = random.nextInt(10) < 6;
        if (seRompeEnAlargue) {
            if (random.nextDouble() < probLocal) {
                g1++;
                distribuirGoles(p.getEquipoLocal().getJugadores(), 1);
            } else {
                g2++;
                distribuirGoles(p.getEquipoVisitante().getJugadores(), 1);
            }
            return new Resultado(g1, g2);
        }

        // Penales
        int[] pen = simularTandaPenales();
        return new Resultado(g1, g2, pen[0], pen[1]);
    }

    /**
     * Distribuye goles entre los jugadores habilitados del equipo.
     * Solo DELANTERO (peso 3) y MEDIOCAMPISTA (peso 2) pueden marcar.
     * Si el equipo no tiene jugadores de esas posiciones, no se registra.
     */
    private void distribuirGoles(List<Jugador> jugadores, int goles) {
        if (jugadores == null || jugadores.isEmpty() || goles <= 0) return;

        // Filtra jugadores habilitados con sus pesos
        List<Jugador> habilitados = new ArrayList<>();
        List<Integer> pesos       = new ArrayList<>();

        for (Jugador j : jugadores) {
            if (j.getPosicion() == PosicionJugador.DELANTERO) {
                habilitados.add(j);
                pesos.add(3); // 3x mas probable
            } else if (j.getPosicion() == PosicionJugador.MEDIOCAMPISTA) {
                habilitados.add(j);
                pesos.add(2);
            }
        }

        if (habilitados.isEmpty()) return;

        // Distribuye cada gol individualmente
        int totalPeso = pesos.stream().mapToInt(Integer::intValue).sum();

        for (int g = 0; g < goles; g++) {
            int dado = random.nextInt(totalPeso);
            int acum = 0;
            for (int i = 0; i < habilitados.size(); i++) {
                acum += pesos.get(i);
                if (dado < acum) {
                    habilitados.get(i).incrementarGoles();
                    break;
                }
            }
        }
    }

    private int[] simularTandaPenales() {
        int p1 = 0, p2 = 0;
        final int PROB = 75;

        for (int tiro = 1; tiro <= 5; tiro++) {
            boolean c1 = random.nextInt(100) < PROB;
            boolean c2 = random.nextInt(100) < PROB;
            if (c1) p1++;
            if (c2) p2++;
            int restantes = 5 - tiro;
            if (p1 - p2 > restantes || p2 - p1 > restantes) break;
        }

        if (p1 != p2) return new int[]{p1, p2};

        for (int ronda = 0; ronda < 20; ronda++) {
            boolean c1 = random.nextInt(100) < PROB;
            boolean c2 = random.nextInt(100) < PROB;
            if  (c1 && !c2) { p1++; break; }
            if  (!c1 && c2) { p2++; break; }
            if  (c1)        { p1++; p2++; }
        }

        if (p1 == p2) {
            if (random.nextBoolean()) p1++;
            else                      p2++;
        }

        return new int[]{p1, p2};
    }
}