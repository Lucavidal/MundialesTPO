package patrones.factory;

import modelo.Partido;

import java.util.List;

/**
 * Reporte que muestra todos los partidos con su resultado y estado.
 */
public class ReportePartidos implements IReporte {

    private List<Partido> partidos;

    public ReportePartidos(List<Partido> partidos) {
        this.partidos = partidos;
    }

    @Override
    public void generar() {
        System.out.println("\n========================================");
        System.out.println("  " + getTitulo());
        System.out.println("========================================");

        if (partidos.isEmpty()) {
            System.out.println("No hay partidos registrados.");
            return;
        }

        for (Partido p : partidos) {
            String resultado = (p.getResultado() != null)
                    ? p.getResultado().toString()
                    : "Pendiente";

            System.out.printf("  [%s] %s vs %s  ->  %s  | Estadio: %s%n",
                    p.getFase(),
                    p.getEquipoLocal().getNombre(),
                    p.getEquipoVisitante().getNombre(),
                    resultado,
                    p.getEstadio() != null ? p.getEstadio().getNombre() : "N/A");
        }
        System.out.println("========================================\n");
    }

    @Override
    public String getTitulo() {
        return "REPORTE DE PARTIDOS";
    }
}