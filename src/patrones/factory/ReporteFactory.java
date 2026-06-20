package patrones.factory;

import modelo.*;
import modelo.enums.PosicionJugador;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ReporteFactory {

    public IReporte crearReporte(String tipo, Torneo torneo) {
        switch (tipo.toLowerCase()) {

            case "partidos":
                List<Partido> todos = new ArrayList<>();
                for (Grupo g : torneo.getGrupos()) {
                    todos.addAll(g.getPartidos());
                }
                todos.addAll(torneo.getPartidosEliminatorios());
                return new ReportePartidos(todos);

            case "tabla":
                return new ReporteTablaPosiciones(torneo.getGrupos());

            case "campeon":
                return new ReporteCampeon(torneo.getCampeon());

            case "goleador":
                return crearReporteGoleador(torneo);

            case "asistidor":
                return crearReporteAsistidor(torneo);

            default:
                throw new IllegalArgumentException(
                        "Tipo desconocido: '" + tipo
                                + "'. Validos: partidos, tabla, campeon, goleador, asistidor.");
        }
    }

    // ---------------------------------------------------------------
    //  TOP GOLEADORES — solo DELANTERO y MEDIOCAMPISTA
    // ---------------------------------------------------------------

    private IReporte crearReporteGoleador(Torneo torneo) {
        return new IReporte() {

            @Override
            public void generar() {
                System.out.println("\n========================================");
                System.out.println("  " + getTitulo());
                System.out.println("========================================");

                List<Jugador> goleadores = new ArrayList<>();
                for (Equipo equipo : torneo.getEquipos()) {
                    for (Jugador j : equipo.getJugadores()) {
                        if ((j.getPosicion() == PosicionJugador.DELANTERO
                                || j.getPosicion() == PosicionJugador.MEDIOCAMPISTA)
                                && j.getGoles() > 0) {
                            goleadores.add(j);
                        }
                    }
                }

                goleadores.sort(Comparator.comparingInt(Jugador::getGoles).reversed());

                if (goleadores.isEmpty()) {
                    System.out.println("  No hay goles registrados aun.");
                    System.out.println("  Asegurate de simular la fase de grupos primero.");
                } else {
                    int pos = 1;
                    for (Jugador j : goleadores) {
                        // getpais() no existe en Jugador — se muestra posicion en su lugar
                        System.out.printf("  %2d. %-10s %-18s [%-13s]  %d gol%s%n",
                                pos++,
                                j.getNombre(),
                                j.getApellido(),
                                j.getPosicion(),
                                j.getGoles(),
                                j.getGoles() == 1 ? "" : "es");
                        if (pos > 10) break; // <- adentro del for
                    }
                }

                System.out.println("========================================\n");
            }

            @Override
            public String getTitulo() { return "TOP 10 GOLEADORES"; }
        };
    }

    // ---------------------------------------------------------------
    //  TOP ASISTIDORES — solo MEDIOCAMPISTA
    // ---------------------------------------------------------------

    private IReporte crearReporteAsistidor(Torneo torneo) {
        return new IReporte() {

            @Override
            public void generar() {
                System.out.println("\n========================================");
                System.out.println("  " + getTitulo());
                System.out.println("========================================");

                List<Jugador> asistidores = new ArrayList<>();
                for (Equipo equipo : torneo.getEquipos()) {
                    for (Jugador j : equipo.getJugadores()) {
                        if (j.getPosicion() == PosicionJugador.MEDIOCAMPISTA
                                && j.getGoles() > 0) {
                            asistidores.add(j);
                        }
                    }
                }

                asistidores.sort(Comparator.comparingInt(Jugador::getGoles).reversed());

                if (asistidores.isEmpty()) {
                    System.out.println("  No hay asistencias registradas aun.");
                    System.out.println("  Asegurate de simular la fase de grupos primero.");
                } else {
                    int pos = 1;
                    for (Jugador j : asistidores) {
                        System.out.printf("  %2d. %-10s %-18s [MEDIOCAMPISTA]  %d participacion%s%n",
                                pos++,
                                j.getNombre(),
                                j.getApellido(),
                                j.getGoles(),
                                j.getGoles() == 1 ? "" : "es");
                        if (pos > 10) break; // <- adentro del for
                    }
                }

                System.out.println("========================================\n");
            }

            @Override
            public String getTitulo() { return "TOP 10 ASISTIDORES (MEDIOCAMPISTAS)"; }
        };
    }
}