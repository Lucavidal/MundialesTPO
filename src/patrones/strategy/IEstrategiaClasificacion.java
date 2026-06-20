package patrones.strategy;



import modelo.Equipo;
import modelo.Grupo;

import java.util.List;

/**
 * Interfaz Strategy para el criterio de clasificación dentro de un grupo.
 */
public interface IEstrategiaClasificacion {
    List<Equipo> clasificar(Grupo g);
}