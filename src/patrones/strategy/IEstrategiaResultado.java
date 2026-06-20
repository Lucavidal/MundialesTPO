package patrones.strategy;



import modelo.Partido;
import modelo.Resultado;

/**
 * Interfaz Strategy para simular el resultado de un partido.
 * Permite intercambiar entre simulación aleatoria y manual.
 */
public interface IEstrategiaResultado {
    Resultado simularResultado(Partido p);
}