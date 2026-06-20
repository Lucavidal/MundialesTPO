package patrones.strategy;


import modelo.Equipo;
import modelo.Grupo;
import modelo.RegistroTabla;

import java.util.ArrayList;
import java.util.List;

/**
 * Clasifica los equipos de un grupo ordenando por puntaje,
 * diferencia de goles y goles a favor (criterio FIFA).
 * Retorna los 2 primeros clasificados.
 */
public class ClasificacionPorPuntosStrategy implements IEstrategiaClasificacion {

    @Override
    public List<Equipo> clasificar(Grupo g) {
        // Ordena la tabla y toma los dos primeros
        g.getTablaPosiciones().ordenar();

        List<RegistroTabla> registros = g.getTablaPosiciones().getRegistros();
        List<Equipo> clasificados = new ArrayList<>();

        int cantClasificados = Math.min(2, registros.size());
        for (int i = 0; i < cantClasificados; i++) {
            clasificados.add(registros.get(i).getEquipo());
        }

        return clasificados;
    }
}