package servicio;



import modelo.Torneo;
import modelo.enums.FaseTorneo;

/**
 * Servicio que gestiona el ciclo de vida del torneo.
 * Responsabilidad: inicializar, consultar y avanzar de fase.
 */
public class TorneoService {

    private Torneo torneo;

    public TorneoService() {
        this.torneo = null;
    }

    /**
     * Inicializa un nuevo torneo con nombre y año.
     * La sede por defecto es "Mundial".
     */
    public void inicializar(String nombre, int anio) {
        this.torneo = new Torneo(nombre, anio, "USACANMEX");
    }

    /**
     * @return el torneo activo
     * @throws IllegalStateException si el torneo no fue inicializado
     */
    public Torneo getTorneo() {
        if (torneo == null) {
            throw new IllegalStateException(
                    "El torneo no fue inicializado. Llame a inicializar() primero.");
        }
        return torneo;
    }

    /**
     * Avanza el torneo a la siguiente fase en el orden del enum FaseTorneo.
     * Si ya está en FINAL, informa que el torneo terminó.
     */
    public void avanzarFase() {
        FaseTorneo[] fases  = FaseTorneo.values();
        FaseTorneo   actual = torneo.getFaseActual();

        for (int i = 0; i < fases.length - 1; i++) {
            if (fases[i] == actual) {
                torneo.setFaseActual(fases[i + 1]);
                System.out.println("✔ Fase avanzada a: " + torneo.getFaseActual());
                return;
            }
        }
        System.out.println("ℹ El torneo ya está en la FINAL.");
    }
}