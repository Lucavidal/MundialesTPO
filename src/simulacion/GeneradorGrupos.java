package simulacion;

import modelo.Equipo;
import modelo.Grupo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GeneradorGrupos {

    private int cantidadGrupos;

    public GeneradorGrupos(int cantidadGrupos) {
        this.cantidadGrupos = cantidadGrupos;
    }

    /**
     * Mezcla aleatoriamente los equipos con seed basada en tiempo
     * para garantizar resultados distintos en cada ejecucion,
     * luego los distribuye en grupos de forma ciclica.
     */
    public List<Grupo> generarGrupos(List<Equipo> equipos) {
        List<Grupo> grupos = new ArrayList<>();

        // Crea los grupos A, B, C, ...
        for (int i = 0; i < cantidadGrupos; i++) {
            String nombreGrupo = String.valueOf((char) ('A' + i));
            grupos.add(new Grupo(nombreGrupo));
        }

        // Copia la lista y mezcla con seed aleatoria real
        List<Equipo> equiposMezclados = new ArrayList<>(equipos);
        Random random = new Random(System.nanoTime()); // seed distinta cada vez
        Collections.shuffle(equiposMezclados, random);

        // Distribuye ciclicamente: equipo 0->A, 1->B, ..., 8->A, 9->B...
        for (int i = 0; i < equiposMezclados.size(); i++) {
            Equipo equipo = equiposMezclados.get(i);
            Grupo grupo = grupos.get(i % cantidadGrupos);
            equipo.setGrupo(grupo.getNombre());
            grupo.agregarEquipo(equipo);
        }

        return grupos;
    }

    public int getCantidadGrupos() {
        return cantidadGrupos;
    }
}