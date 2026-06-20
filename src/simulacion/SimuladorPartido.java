package simulacion;



import modelo.Partido;
import modelo.Resultado;
import modelo.enums.EstadoPartido;
import patrones.strategy.IEstrategiaResultado;
import patrones.strategy.ResultadoAleatorioStrategy;

/**
 * Responsable de simular un partido individual.
 * Delega la generación del resultado a una EstrategiaResultado (patrón Strategy).
 */
public class SimuladorPartido {

    private IEstrategiaResultado estrategiaResultado;

    public SimuladorPartido() {
        // Por defecto usa la estrategia aleatoria
        this.estrategiaResultado = new ResultadoAleatorioStrategy();
    }

    public SimuladorPartido(IEstrategiaResultado estrategia) {
        this.estrategiaResultado = estrategia;
    }

    /**
     * Simula el partido: cambia su estado, genera el resultado
     * y lo asigna al partido.
     */
    public Resultado simular(Partido p) {
        p.setEstado(EstadoPartido.EN_CURSO);

        Resultado resultado = estrategiaResultado.simularResultado(p);
        p.setResultado(resultado);

        p.setEstado(EstadoPartido.FINALIZADO);
        return resultado;
    }

    /** Permite cambiar la estrategia de simulación en tiempo de ejecución */
    public void setEstrategia(IEstrategiaResultado e) {
        this.estrategiaResultado = e;
    }

    public IEstrategiaResultado getEstrategia() {
        return estrategiaResultado;
    }
}