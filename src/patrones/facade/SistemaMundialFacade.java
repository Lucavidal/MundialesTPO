package patrones.facade;


import datos.CargadorJSON;
import datos.DatosDePrueba;
import modelo.Equipo;
import modelo.Estadio;
import modelo.Jugador;
import patrones.factory.IReporte;
import patrones.factory.ReporteFactory;
import servicio.*;
import simulacion.SimuladorMundial;
import util.ReporteHTML;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SistemaMundialFacade {

    private TorneoService      torneoService;
    private EquipoService      equipoService;
    private JugadorService     jugadorService;
    private PartidoService     partidoService;
    private EstadisticaService estadisticaService;
    private SimuladorMundial   simuladorMundial;
    private ReporteFactory     reporteFactory;

    public SistemaMundialFacade() {
        this.torneoService      = new TorneoService();
        this.equipoService      = new EquipoService();
        this.jugadorService     = new JugadorService();
        this.partidoService     = new PartidoService();
        this.estadisticaService = new EstadisticaService();
        this.reporteFactory     = new ReporteFactory();
    }

    public void iniciarTorneo() {
        torneoService.inicializar("Copa Mundial de Futbol", 2026);
        System.out.println("Torneo inicializado: " + torneoService.getTorneo());
    }

    /**
     * Carga equipos desde JSON si existe, sino usa DatosDePrueba.
     * Mezcla aleatoriamente antes de registrar para que cada
     * simulacion tenga grupos distintos.
     */
    public void cargarEquipos() {
        // 1. Intentar cargar desde JSON
        CargadorJSON cargador = new CargadorJSON();
        List<Equipo> equipos = cargador.cargarEquipos();


        if (equipos.isEmpty()) {
            System.out.println("Usando datos de prueba por defecto...");
            DatosDePrueba datos = new DatosDePrueba();
            equipos = datos.cargarEquipos();
            datos.cargarJugadores(equipos);
        }


        Collections.shuffle(equipos, new Random(System.nanoTime()));
        System.out.println("Equipos mezclados aleatoriamente.");


        for (Equipo e : equipos) {
            equipoService.agregarEquipo(e);
            torneoService.getTorneo().agregarEquipo(e);
            for (Jugador j : e.getJugadores()) {
                jugadorService.agregarJugador(j);
            }
        }


        DatosDePrueba datos = new DatosDePrueba();
        List<Estadio> estadios = datos.cargarEstadios();
        for (Estadio est : estadios) {
            torneoService.getTorneo().agregarEstadio(est);
        }

        System.out.println("Equipos cargados: " + equipos.size());
        System.out.println("Estadios cargados: " + estadios.size());
    }

    public void cargarJugadores() {
        List<Equipo> equipos = equipoService.listarEquipos();
        boolean alguno = equipos.stream().anyMatch(e -> e.getJugadores().isEmpty());
        if (alguno) {
            DatosDePrueba datos = new DatosDePrueba();
            datos.cargarJugadores(equipos);
            for (Equipo e : equipos) {
                for (Jugador j : e.getJugadores()) {
                    jugadorService.agregarJugador(j);
                }
            }
        }
        System.out.println("Jugadores listos.");
    }

    public void generarGrupos() {
        if (simuladorMundial == null) {
            simuladorMundial = new SimuladorMundial(torneoService.getTorneo());
        }
        simuladorMundial.generarFaseGrupos();
    }

    public void simularFaseGrupos() {
        if (simuladorMundial == null) {
            System.out.println("Primero genere los grupos (opcion 4).");
            return;
        }
        simuladorMundial.simularFaseGrupos();
        System.out.println("Fase de grupos simulada.");
    }

    public void simularEliminatorias() {
        if (simuladorMundial == null) {
            System.out.println("Primero simule la fase de grupos (opcion 5).");
            return;
        }
        simuladorMundial.simularEliminatorias();
    }

    public Equipo obtenerCampeon() {
        return torneoService.getTorneo().getCampeon();
    }

    public void generarReporte(String tipo) {
        IReporte reporte = reporteFactory.crearReporte(tipo, torneoService.getTorneo());
        reporte.generar();
    }

    public void abrirReporteVisual() {
        new ReporteHTML(torneoService.getTorneo()).generarYAbrir();
    }

    public TorneoService      getTorneoService()        { return torneoService; }
    public EquipoService      getEquipoService()        { return equipoService; }
    public SimuladorMundial   getSimuladorMundial()     { return simuladorMundial; }
    public EstadisticaService getEstadisticaService()   { return estadisticaService; }
    public PartidoService     getPartidoService()       { return partidoService; }
}