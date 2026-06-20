package modelo;


import java.util.ArrayList;
import java.util.List;

public class Equipo {

    private int id;
    private String nombre;
    private String pais;
    private String grupo;
    private List<Jugador> jugadores;

    public Equipo(int id, String nombre, String pais) {
        this.id = id;
        this.nombre = nombre;
        this.pais = pais;
        this.grupo = "";
        this.jugadores = new ArrayList<>();
    }

    public Equipo(int id, String nombre, String pais, String grupo) {
        this.id = id;
        this.nombre = nombre;
        this.pais = pais;
        this.grupo = grupo;
        this.jugadores = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPais() {
        return pais;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }


    public void agregarJugador(Jugador j) {
        this.jugadores.add(j);
    }

    @Override
    public String toString() {
        return String.format("Equipo[%d] %s (%s) - Grupo: %s",
                id, nombre, pais, grupo.isEmpty() ? "Sin asignar" : grupo);
    }
}