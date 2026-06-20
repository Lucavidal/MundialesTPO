package simulacion;

import modelo.Equipo;
import modelo.Estadio;
import modelo.Grupo;
import modelo.Partido;
import modelo.Torneo;
import modelo.enums.EstadoPartido;
import modelo.enums.FaseTorneo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GeneradorFixture {

    /**
     * Genera todos los partidos del grupo (todos contra todos).
     * Usa el contador centralizado del torneo para IDs correlativos.
     */
    public List<Partido> generarPartidos(Grupo grupo, List<Estadio> estadios, Torneo torneo) {
        List<Partido> partidos = new ArrayList<>();
        List<Equipo> equipos = grupo.getEquipos();
        int estadioIndex = 0;

        for (int i = 0; i < equipos.size() - 1; i++) {
            for (int j = i + 1; j < equipos.size(); j++) {
                Estadio estadio = estadios.isEmpty() ? null
                        : estadios.get(estadioIndex++ % estadios.size());

                Partido partido = new Partido(
                        torneo.siguienteIdPartido(), // CENTRALIZADO
                        equipos.get(i),
                        equipos.get(j),
                        estadio,
                        FaseTorneo.GRUPOS,
                        LocalDate.now()
                );
                partidos.add(partido);
                grupo.agregarPartido(partido);
            }
        }
        return partidos;
    }
}