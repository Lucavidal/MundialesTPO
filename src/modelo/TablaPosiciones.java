package modelo;



import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TablaPosiciones {

    private List<RegistroTabla> registros;

    public TablaPosiciones() {
        this.registros = new ArrayList<>();
    }

    public List<RegistroTabla> getRegistros() {
        return registros;
    }

    public void agregarRegistro(RegistroTabla r) {
        this.registros.add(r);
    }


    public void ordenar() {
        registros.sort(
                Comparator.comparingInt(RegistroTabla::getPuntaje).reversed()
                        .thenComparingInt(RegistroTabla::getDiferenciaGoles).reversed()
                        .thenComparingInt(RegistroTabla::getGolesFavor).reversed()
        );
    }

    /** Busca el registro de un equipo específico. Retorna null si no existe. */
    public RegistroTabla getRegistroPorEquipo(Equipo e) {
        for (RegistroTabla r : registros) {
            if (r.getEquipo().getId() == e.getId()) {
                return r;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-20s | PJ | PG | PE | PP |  GF |  GC |  DG | Pts%n",
                "Equipo"));
        sb.append("-".repeat(70)).append("\n");
        for (RegistroTabla r : registros) {
            sb.append(r.toString()).append("\n");
        }
        return sb.toString();
    }
}