package simulacion;



import clasificacion.CriterioDesempate;
import modelo.Equipo;
import modelo.Grupo;
import modelo.RegistroTabla;

import java.util.ArrayList;
import java.util.List;

/**
 * Selecciona los mejores equipos que terminaron en el tercer puesto
 * de su grupo, para completar los 32 clasificados a Dieciseisavos
 * (formato real Mundial 2026: 24 primeros/segundos + 8 mejores terceros).
 *
 * Reutiliza CriterioDesempate para ordenar los terceros entre si
 * con el mismo criterio FIFA (puntos, diferencia de gol, goles a favor).
 */
public class ClasificadorMejoresTerceros {

    private CriterioDesempate criterioDesempate;

    public ClasificadorMejoresTerceros() {
        this.criterioDesempate = new CriterioDesempate();
    }

    /**
     * @param grupos   todos los grupos del torneo, ya simulados
     * @param cantidad cuantos terceros deben clasificar (8 en formato 2026)
     * @return los equipos correspondientes a los mejores terceros
     */
    public List<Equipo> obtenerMejoresTerceros(List<Grupo> grupos, int cantidad) {
        List<RegistroTabla> terceros = new ArrayList<>();

        for (Grupo g : grupos) {
            g.getTablaPosiciones().ordenar();
            List<RegistroTabla> registros = g.getTablaPosiciones().getRegistros();
            if (registros.size() >= 3) {
                terceros.add(registros.get(2)); // posicion 3 = indice 2
            }
        }

        // Ordena los 12 terceros entre si con el criterio de desempate FIFA
        criterioDesempate.aplicar(terceros);

        List<Equipo> mejores = new ArrayList<>();
        int limite = Math.min(cantidad, terceros.size());
        for (int i = 0; i < limite; i++) {
            mejores.add(terceros.get(i).getEquipo());
        }
        return mejores;
    }
}