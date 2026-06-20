package patrones.factory;



import modelo.Equipo;
import modelo.Jugador;


public class ReporteCampeon implements IReporte {

    private Equipo campeon;

    public ReporteCampeon(Equipo campeon) {
        this.campeon = campeon;
    }

    @Override
    public void generar() {
        System.out.println("\n========================================");
        System.out.println("  " + getTitulo());
        System.out.println("========================================");

        if (campeon == null) {
            System.out.println("  El torneo aún no tiene campeón.");
        } else {
            System.out.println("  🏆 CAMPEÓN DEL MUNDO: " + campeon.getNombre().toUpperCase());
            System.out.println("  País: " + campeon.getPais());
            System.out.println("  Jugadores:");


            Jugador maxGoleador = null;
            for (Jugador j : campeon.getJugadores()) {
                if (maxGoleador == null || j.getGoles() > maxGoleador.getGoles()) {
                    maxGoleador = j;
                }
            }

            if (maxGoleador != null) {
                System.out.println("  Máximo goleador del equipo: "
                        + maxGoleador.getNombre() + " " + maxGoleador.getApellido()
                        + " (" + maxGoleador.getGoles() + " goles)");
            }
        }
        System.out.println("========================================\n");
    }

    @Override
    public String getTitulo() {
        return "CAMPEÓN DEL TORNEO";
    }
}