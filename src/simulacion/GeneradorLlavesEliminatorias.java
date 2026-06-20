package simulacion;

import modelo.Equipo;
import modelo.Estadio;
import modelo.Partido;
import modelo.Torneo;
import modelo.enums.FaseTorneo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GeneradorLlavesEliminatorias {

    /**
     * Genera llaves eliminatorias con IDs correlativos usando el contador del torneo.
     * Empareja pares consecutivos: [0 vs 1], [2 vs 3], [4 vs 5]...
     */
    public List<Partido> generarLlaves(List<Equipo> clasificados,
                                       FaseTorneo fase,
                                       List<Estadio> estadios,
                                       Torneo torneo) { // NUEVO parámetro
        List<Partido> partidos = new ArrayList<>();

        if (clasificados == null || clasificados.size() < 2) {
            System.out.println("No hay suficientes clasificados para " + fase);
            return partidos;
        }

        int estadioIdx = 0;

        for (int i = 0; i + 1 < clasificados.size(); i += 2) {
            Estadio estadio = estadios.isEmpty() ? null
                    : estadios.get(estadioIdx++ % estadios.size());

            Partido partido = new Partido(
                    torneo.siguienteIdPartido(), // CENTRALIZADO
                    clasificados.get(i),
                    clasificados.get(i + 1),
                    estadio,
                    fase,
                    LocalDate.now()
            );
            partidos.add(partido);
        }

        return partidos;
    }
}