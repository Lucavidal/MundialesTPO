package clasificacion;



import modelo.Equipo;
import modelo.Partido;
import modelo.Resultado;
import modelo.enums.EstadoPartido;


public class ClasificadorEliminatoria {


    public Equipo determinarGanador(Partido partido) {
        if (partido.getEstado() != EstadoPartido.FINALIZADO) {
            System.out.println("⚠ El partido " + partido.getId() + " no está finalizado.");
            return null;
        }

        Resultado resultado = partido.getResultado();
        if (resultado == null) {
            System.out.println("⚠ El partido " + partido.getId() + " no tiene resultado.");
            return null;
        }

        Equipo ganador = resultado.getGanador(
                partido.getEquipoLocal(),
                partido.getEquipoVisitante()
        );


        if (ganador == null) {
            System.out.println("⚠ Empate sin resolver en partido eliminatorio "
                    + partido.getId() + ". Verificar estrategia.");
        }

        return ganador;
    }
}
