package servicio;



import modelo.Partido;
import modelo.enums.FaseTorneo;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para la gestión de partidos en memoria.
 */
public class PartidoService {

    private List<Partido> partidos;

    public PartidoService() {
        this.partidos = new ArrayList<>();
    }

    public void agregarPartido(Partido p) {
        partidos.add(p);
    }

    /**
     * Busca un partido por su ID.
     * @return el partido encontrado, o null si no existe
     */
    public Partido buscarPorId(int id) {
        for (Partido p : partidos) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    /**
     * Filtra los partidos por fase del torneo.
     * @return lista de partidos de la fase indicada
     */
    public List<Partido> listarPorFase(FaseTorneo fase) {
        List<Partido> resultado = new ArrayList<>();
        for (Partido p : partidos) {
            if (p.getFase() == fase) {
                resultado.add(p);
            }
        }
        return resultado;
    }
}