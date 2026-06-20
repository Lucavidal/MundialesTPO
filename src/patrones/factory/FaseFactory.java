package patrones.factory;



import modelo.Equipo;
import modelo.Estadio;
import modelo.Partido;
import modelo.enums.FaseTorneo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class FaseFactory {

    private static int contadorId = 1000;


    public List<Partido> crearPartidosEliminatorios(List<Equipo> clasificados,
                                                    FaseTorneo fase) {
        List<Partido> partidos = new ArrayList<>();

        if (clasificados == null || clasificados.size() < 2) {
            System.out.println("No hay suficientes equipos para generar " + fase);
            return partidos;
        }

        int total = clasificados.size();

        for (int i = 0; i < total / 2; i++) {
            Equipo local     = clasificados.get(i);
            Equipo visitante = clasificados.get(total - 1 - i);

            Partido partido = new Partido(
                    contadorId++,
                    local,
                    visitante,
                    null,
                    fase,
                    LocalDate.now()
            );
            partidos.add(partido);
        }

        return partidos;
    }
}
