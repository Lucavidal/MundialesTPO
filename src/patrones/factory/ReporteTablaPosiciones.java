package patrones.factory;



import modelo.Grupo;

import java.util.List;

/**
 * Reporte que muestra la tabla de posiciones de todos los grupos.
 */
public class ReporteTablaPosiciones implements IReporte {

    private List<Grupo> grupos;

    public ReporteTablaPosiciones(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    @Override
    public void generar() {
        System.out.println("\n========================================");
        System.out.println("  " + getTitulo());
        System.out.println("========================================");

        if (grupos.isEmpty()) {
            System.out.println("No hay grupos generados aún.");
            return;
        }

        for (Grupo g : grupos) {
            System.out.println("\n  --- GRUPO " + g.getNombre() + " ---");
            g.getTablaPosiciones().ordenar();
            System.out.print(g.getTablaPosiciones().toString());
        }
        System.out.println("========================================\n");
    }

    @Override
    public String getTitulo() {
        return "TABLA DE POSICIONES POR GRUPO";
    }
}