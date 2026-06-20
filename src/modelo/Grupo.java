package modelo;



import java.util.ArrayList;
import java.util.List;

public class Grupo {

    private String nombre;
    private List<Equipo> equipos;
    private List<Partido> partidos;
    private TablaPosiciones tablaPosiciones;

    public Grupo(String nombre) {
        this.nombre = nombre;
        this.equipos = new ArrayList<>();
        this.partidos = new ArrayList<>();
        this.tablaPosiciones = new TablaPosiciones();
    }

    public String getNombre() {
        return nombre;
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public List<Partido> getPartidos() {
        return partidos;
    }

    public TablaPosiciones getTablaPosiciones() {
        return tablaPosiciones;
    }

    /** Agrega un equipo al grupo y crea su registro en la tabla de posiciones */
    public void agregarEquipo(Equipo e) {
        this.equipos.add(e);
        this.tablaPosiciones.agregarRegistro(new RegistroTabla(e));
    }

    public void agregarPartido(Partido p) {
        this.partidos.add(p);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== GRUPO ").append(nombre).append(" ===\n");
        for (Equipo e : equipos) {
            sb.append("  - ").append(e.getNombre()).append("\n");
        }
        return sb.toString();
    }
}