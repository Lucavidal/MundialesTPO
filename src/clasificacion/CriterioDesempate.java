package clasificacion;



import modelo.RegistroTabla;

import java.util.Comparator;
import java.util.List;


public class CriterioDesempate {


    public List<RegistroTabla> aplicar(List<RegistroTabla> registros) {
        registros.sort(
                Comparator

                        .comparingInt(RegistroTabla::getPuntaje).reversed()

                        .thenComparingInt(RegistroTabla::getDiferenciaGoles).reversed()

                        .thenComparingInt(RegistroTabla::getGolesFavor).reversed()

                        .thenComparing(r -> r.getEquipo().getNombre())
        );
        return registros;
    }
}