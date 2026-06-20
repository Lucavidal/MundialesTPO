package modelo;

import modelo.enums.FaseTorneo;

import java.util.ArrayList;
import java.util.List;

public class Torneo {

    private String nombre;
    private int anio;
    private String paisSede;
    private List<Grupo> grupos;
    private List<Equipo> equipos;
    private List<Estadio> estadios;
    private FaseTorneo faseActual;
    private Equipo campeon;
    private List<Partido> partidosEliminatorios;
    private int contadorPartidos; // NUEVO — contador global de IDs

    public Torneo(String nombre, int anio, String paisSede) {
        this.nombre = nombre;
        this.anio = anio;
        this.paisSede = paisSede;
        this.grupos = new ArrayList<>();
        this.equipos = new ArrayList<>();
        this.estadios = new ArrayList<>();
        this.faseActual = FaseTorneo.GRUPOS;
        this.campeon = null;
        this.partidosEliminatorios = new ArrayList<>();
        this.contadorPartidos = 1; // NUEVO — empieza en 1
    }

    // NUEVO — retorna el siguiente ID y lo incrementa
    public int siguienteIdPartido() {
        return contadorPartidos++;
    }

    public String getNombre()          { return nombre; }
    public int    getAnio()            { return anio; }
    public String getPaisSede()        { return paisSede; }
    public List<Grupo>   getGrupos()   { return grupos; }
    public List<Equipo>  getEquipos()  { return equipos; }
    public List<Estadio> getEstadios() { return estadios; }
    public FaseTorneo    getFaseActual(){ return faseActual; }
    public Equipo        getCampeon()  { return campeon; }

    public List<Partido> getPartidosEliminatorios() {
        return partidosEliminatorios;
    }

    public void agregarPartidoEliminatorio(Partido p) {
        this.partidosEliminatorios.add(p);
    }

    public void agregarEquipo(Equipo e)    { this.equipos.add(e); }
    public void agregarEstadio(Estadio est){ this.estadios.add(est); }
    public void agregarGrupo(Grupo g)      { this.grupos.add(g); }
    public void setCampeon(Equipo e)       { this.campeon = e; }
    public void setFaseActual(FaseTorneo f){ this.faseActual = f; }

    @Override
    public String toString() {
        return String.format("Torneo: %s %d | Sede: %s | Fase actual: %s | Equipos: %d",
                nombre, anio, paisSede, faseActual, equipos.size());
    }
}