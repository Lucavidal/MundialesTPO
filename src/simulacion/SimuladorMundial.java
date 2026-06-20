package simulacion;

import clasificacion.ClasificadorEliminatoria;
import clasificacion.ClasificadorGrupos;
import simulacion.ClasificadorMejoresTerceros;
import modelo.Equipo;
import modelo.Grupo;
import modelo.Partido;
import modelo.RegistroTabla;
import modelo.Resultado;
import modelo.Torneo;
import modelo.enums.FaseTorneo;
import patrones.strategy.ClasificacionPorPuntosStrategy;

import java.util.ArrayList;
import java.util.List;

public class SimuladorMundial {

    private static final int CANTIDAD_GRUPOS  = 12;
    private static final int MEJORES_TERCEROS = 8;

    private Torneo torneo;
    private SimuladorPartido             simuladorPartido;
    private GeneradorGrupos              generadorGrupos;
    private GeneradorFixture             generadorFixture;
    private GeneradorLlavesEliminatorias generadorLlaves;

    public SimuladorMundial(Torneo torneo) {
        this.torneo           = torneo;
        this.simuladorPartido = new SimuladorPartido();
        this.generadorGrupos  = new GeneradorGrupos(CANTIDAD_GRUPOS);
        this.generadorFixture = new GeneradorFixture();
        this.generadorLlaves  = new GeneradorLlavesEliminatorias();
    }

    // ---------------------------------------------------------------
    //  PASO 1: genera grupos y fixture SIN simular
    // ---------------------------------------------------------------

    public void generarFaseGrupos() {
        torneo.setFaseActual(FaseTorneo.GRUPOS);

        if (!torneo.getGrupos().isEmpty()) {
            System.out.println("Los grupos ya fueron generados.");
            return;
        }

        int necesarios = CANTIDAD_GRUPOS * 4;
        if (torneo.getEquipos().size() < necesarios) {
            System.out.println("Se necesitan " + necesarios + " equipos. Hay "
                    + torneo.getEquipos().size() + ".");
            return;
        }

        // 1. Genera y registra los grupos
        List<Grupo> grupos = generadorGrupos.generarGrupos(torneo.getEquipos());
        for (Grupo g : grupos) {
            torneo.agregarGrupo(g);
        }

        // 2. Genera el fixture de CADA grupo individualmente — FIX clave
        for (Grupo g : torneo.getGrupos()) {
            generadorFixture.generarPartidos(g, torneo.getEstadios(), torneo);
        }

        // 3. Muestra los grupos generados
        System.out.println("\n========== GRUPOS GENERADOS (" + CANTIDAD_GRUPOS + ") ==========");
        for (Grupo g : torneo.getGrupos()) {
            System.out.println("\n  Grupo " + g.getNombre() + ":");
            for (Equipo e : g.getEquipos()) {
                System.out.println("    - " + e.getNombre());
            }
        }
        System.out.println("==========================================");
    }

    // ---------------------------------------------------------------
    //  PASO 2: simula los partidos de grupos ya generados
    // ---------------------------------------------------------------

    public void simularFaseGrupos() {
        if (torneo.getGrupos().isEmpty()) {
            System.out.println("Primero genere los grupos (opcion 4).");
            return;
        }

        System.out.println("\n========== SIMULANDO FASE DE GRUPOS ==========");
        for (Grupo g : torneo.getGrupos()) {
            System.out.println("\n--- Grupo " + g.getNombre() + " ---");
            for (Partido p : g.getPartidos()) {
                Resultado resultado = simuladorPartido.simular(p);
                actualizarTabla(g, p, resultado);
                System.out.println("  " + p);
            }
            g.getTablaPosiciones().ordenar();
            System.out.println("\n  Tabla Grupo " + g.getNombre() + ":");
            System.out.print(g.getTablaPosiciones());
        }

        mostrarClasificados();
    }

    private void actualizarTabla(Grupo grupo, Partido partido, Resultado resultado) {
        RegistroTabla regLocal = grupo.getTablaPosiciones()
                .getRegistroPorEquipo(partido.getEquipoLocal());
        RegistroTabla regVisitante = grupo.getTablaPosiciones()
                .getRegistroPorEquipo(partido.getEquipoVisitante());

        if (regLocal != null)     regLocal.actualizarConResultado(resultado, true);
        if (regVisitante != null) regVisitante.actualizarConResultado(resultado, false);
    }

    private void mostrarClasificados() {
        List<Equipo> clasificados = obtenerClasificados();
        if (clasificados.size() < 32) return;

        System.out.println("\n========== CLASIFICADOS A DIECISEISAVOS (32) ==========");
        System.out.println("-- Primeros de grupo --");
        for (int i = 0; i < 12; i++)
            System.out.println("  " + clasificados.get(i).getNombre());
        System.out.println("-- Segundos de grupo --");
        for (int i = 12; i < 24; i++)
            System.out.println("  " + clasificados.get(i).getNombre());
        System.out.println("-- Mejores 8 terceros --");
        for (int i = 24; i < 32; i++)
            System.out.println("  " + clasificados.get(i).getNombre());
        System.out.println("========================================================");
    }

    // ---------------------------------------------------------------
    //  CLASIFICACION: 12x(1ro) + 12x(2do) + 8 mejores terceros = 32
    // ---------------------------------------------------------------

    public List<Equipo> obtenerClasificados() {
        ClasificadorGrupos clasificadorGrupos = new ClasificadorGrupos(
                new ClasificacionPorPuntosStrategy());
        ClasificadorMejoresTerceros clasificadorTerceros =
                new ClasificadorMejoresTerceros();

        List<Grupo> grupos = torneo.getGrupos();
        List<Equipo> clasificados = new ArrayList<>();

        int mitad = grupos.size() / 2;

        for (int i = 0; i < mitad; i++) {
            Grupo gPar   = grupos.get(i * 2);
            Grupo gImpar = grupos.get(i * 2 + 1);
            List<Equipo> topPar   = clasificadorGrupos.clasificar(gPar);
            List<Equipo> topImpar = clasificadorGrupos.clasificar(gImpar);
            if (!topPar.isEmpty())   clasificados.add(topPar.get(0));
            if (topImpar.size() > 1) clasificados.add(topImpar.get(1));
        }

        for (int i = 0; i < mitad; i++) {
            Grupo gPar   = grupos.get(i * 2);
            Grupo gImpar = grupos.get(i * 2 + 1);
            List<Equipo> topPar   = clasificadorGrupos.clasificar(gPar);
            List<Equipo> topImpar = clasificadorGrupos.clasificar(gImpar);
            if (!topImpar.isEmpty()) clasificados.add(topImpar.get(0));
            if (topPar.size() > 1)   clasificados.add(topPar.get(1));
        }

        List<Equipo> terceros = clasificadorTerceros
                .obtenerMejoresTerceros(torneo.getGrupos(), MEJORES_TERCEROS);
        clasificados.addAll(terceros);

        return clasificados;
    }

    // ---------------------------------------------------------------
    //  FASES ELIMINATORIAS
    // ---------------------------------------------------------------

    public void simularEliminatorias() {
        FaseTorneo[] fases = {
                FaseTorneo.DIECISEISAVOS, FaseTorneo.OCTAVOS,
                FaseTorneo.CUARTOS, FaseTorneo.SEMIFINAL,
                FaseTorneo.TERCER_PUESTO, FaseTorneo.FINAL
        };
        for (FaseTorneo fase : fases) {
            simularFaseEspecifica(fase);
        }
    }

    public void simularFaseEspecifica(FaseTorneo fase) {
        if (torneo.getGrupos().isEmpty()) {
            System.out.println("Primero simule la fase de grupos.");
            return;
        }

        if (faseJugada(fase)) {
            System.out.println("La fase " + fase + " ya fue simulada.");
            mostrarResultadosFase(fase);
            return;
        }

        List<Equipo> equiposRonda;

        switch (fase) {
            case DIECISEISAVOS:
                equiposRonda = obtenerClasificados();
                if (equiposRonda.size() < 32) {
                    System.out.println("Se necesitan 32 clasificados. Hay "
                            + equiposRonda.size() + ".");
                    return;
                }
                break;

            case TERCER_PUESTO:
                if (!faseJugada(FaseTorneo.SEMIFINAL)) {
                    System.out.println("Primero simula: SEMIFINAL");
                    return;
                }
                equiposRonda = obtenerPerdedoresDeFase(FaseTorneo.SEMIFINAL);
                break;

            case FINAL:
                if (!faseJugada(FaseTorneo.SEMIFINAL)) {
                    System.out.println("Primero simula: SEMIFINAL");
                    return;
                }
                equiposRonda = obtenerGanadoresDeFase(FaseTorneo.SEMIFINAL);
                break;

            default:
                FaseTorneo anterior = faseAnterior(fase);
                if (!faseJugada(anterior)) {
                    System.out.println("Primero simula: " + anterior);
                    return;
                }
                equiposRonda = obtenerGanadoresDeFase(anterior);
        }

        if (!cantidadValidaParaFase(equiposRonda.size(), fase)) {
            System.out.println("Cantidad incorrecta para " + fase
                    + ": " + equiposRonda.size() + " equipos.");
            return;
        }

        torneo.setFaseActual(fase);
        List<Equipo> ganadores = simularRondaEliminatoria(equiposRonda, fase);

        if (fase == FaseTorneo.FINAL && ganadores.size() == 1) {
            torneo.setCampeon(ganadores.get(0));
            System.out.println("\nCAMPEON: " + ganadores.get(0).getNombre().toUpperCase());
        }
    }

    private List<Equipo> simularRondaEliminatoria(List<Equipo> equipos, FaseTorneo fase) {
        System.out.println("\n========== " + fase + " ==========");

        // Pasa torneo para IDs correlativos — FIX clave
        List<Partido> partidos = generadorLlaves.generarLlaves(
                equipos, fase, torneo.getEstadios(), torneo);

        ClasificadorEliminatoria clasificador = new ClasificadorEliminatoria();
        List<Equipo> ganadores = new ArrayList<>();

        for (Partido p : partidos) {
            simuladorPartido.simular(p);
            torneo.agregarPartidoEliminatorio(p);
            System.out.println("  " + p);
            Equipo ganador = clasificador.determinarGanador(p);
            if (ganador != null) {
                ganadores.add(ganador);
                System.out.println("    -> Avanza: " + ganador.getNombre());
            }
        }
        return ganadores;
    }

    // ---------------------------------------------------------------
    //  HELPERS
    // ---------------------------------------------------------------

    private boolean faseJugada(FaseTorneo fase) {
        return torneo.getPartidosEliminatorios().stream()
                .anyMatch(p -> p.getFase() == fase);
    }

    private List<Equipo> obtenerGanadoresDeFase(FaseTorneo fase) {
        ClasificadorEliminatoria clasificador = new ClasificadorEliminatoria();
        List<Equipo> ganadores = new ArrayList<>();
        for (Partido p : torneo.getPartidosEliminatorios()) {
            if (p.getFase() == fase) {
                Equipo g = clasificador.determinarGanador(p);
                if (g != null) ganadores.add(g);
            }
        }
        return ganadores;
    }

    private List<Equipo> obtenerPerdedoresDeFase(FaseTorneo fase) {
        ClasificadorEliminatoria clasificador = new ClasificadorEliminatoria();
        List<Equipo> perdedores = new ArrayList<>();
        for (Partido p : torneo.getPartidosEliminatorios()) {
            if (p.getFase() == fase) {
                Equipo ganador = clasificador.determinarGanador(p);
                if (ganador != null) {
                    Equipo perdedor = ganador.getId() == p.getEquipoLocal().getId()
                            ? p.getEquipoVisitante() : p.getEquipoLocal();
                    perdedores.add(perdedor);
                }
            }
        }
        return perdedores;
    }

    private void mostrarResultadosFase(FaseTorneo fase) {
        System.out.println("\n=== " + fase + " (ya jugada) ===");
        for (Partido p : torneo.getPartidosEliminatorios()) {
            if (p.getFase() == fase) System.out.println("  " + p);
        }
    }

    private FaseTorneo faseAnterior(FaseTorneo fase) {
        switch (fase) {
            case OCTAVOS:   return FaseTorneo.DIECISEISAVOS;
            case CUARTOS:   return FaseTorneo.OCTAVOS;
            case SEMIFINAL: return FaseTorneo.CUARTOS;
            default:        return FaseTorneo.GRUPOS;
        }
    }

    private boolean cantidadValidaParaFase(int cant, FaseTorneo fase) {
        switch (fase) {
            case DIECISEISAVOS:  return cant == 32;
            case OCTAVOS:        return cant == 16;
            case CUARTOS:        return cant == 8;
            case SEMIFINAL:      return cant == 4;
            case TERCER_PUESTO:  return cant == 2;
            case FINAL:          return cant == 2;
            default:             return false;
        }
    }

    public void avanzarFase() {
        FaseTorneo[] fases = FaseTorneo.values();
        FaseTorneo actual  = torneo.getFaseActual();
        for (int i = 0; i < fases.length - 1; i++) {
            if (fases[i] == actual) {
                torneo.setFaseActual(fases[i + 1]);
                System.out.println("Fase avanzada a: " + torneo.getFaseActual());
                return;
            }
        }
        System.out.println("El torneo ya esta en la fase final.");
    }

    public Torneo getTorneo()                     { return torneo; }
    public SimuladorPartido getSimuladorPartido() { return simuladorPartido; }
}