package servicio;

import modelo.Equipo;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio CRUD para la gestión de equipos en memoria.
 * Responsabilidad única: administrar el ciclo de vida de los equipos.
 */
public class EquipoService {

    private List<Equipo> equipos;

    public EquipoService() {
        this.equipos = new ArrayList<>();
    }

    public void agregarEquipo(Equipo e) {
        equipos.add(e);
    }

    /**
     * Busca un equipo por su ID.
     * @return el equipo encontrado, o null si no existe
     */
    public Equipo buscarPorId(int id) {
        for (Equipo e : equipos) {
            if (e.getId() == id) return e;
        }
        return null;
    }

    public List<Equipo> listarEquipos() {
        return new ArrayList<>(equipos);
    }

    /** Elimina el equipo con el ID indicado. No hace nada si no existe. */
    public void eliminarEquipo(int id) {
        equipos.removeIf(e -> e.getId() == id);
    }
}