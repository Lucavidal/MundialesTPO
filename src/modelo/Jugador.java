package modelo;

import modelo.enums.PosicionJugador;

public class Jugador {

    private int id;
    private String nombre;
    private String apellido;
    private int dorsal;
    private PosicionJugador posicion;
    private int goles;
    private int asistencias;
    private int tarjetasAmarillas;
    private int tarjetasRojas;
    private String pais;

    public Jugador(int id, String nombre, String apellido,
                   int dorsal, PosicionJugador posicion) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dorsal = dorsal;
        this.posicion = posicion;
        this.goles = 0;
        this.asistencias = 0;
        this.tarjetasAmarillas = 0;
        this.tarjetasRojas = 0;
    }

    public int            getId()              { return id;
    }
    public String         getNombre()          { return nombre; }
    public String         getApellido()        { return apellido; }
    public int            getDorsal()          { return dorsal; }
    public PosicionJugador getPosicion()       { return posicion; }
    public int            getGoles()           { return goles; }
    public int            getAsistencias()     { return asistencias; }
    public int            getTarjetasAmarillas(){ return tarjetasAmarillas; }
    public int            getTarjetasRojas()   { return tarjetasRojas; }


    public void incrementarGoles()      { this.goles++; }
    public void incrementarAsistencias(){ this.asistencias++; }

    @Override
    public String toString() {
        return String.format("#%d %s %s [%s] - Goles: %d  Asistencias: %d",
                dorsal, nombre, apellido, posicion, goles, asistencias);
    }
}