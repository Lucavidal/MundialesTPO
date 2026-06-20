package clasificacion;



import modelo.Equipo;
import modelo.Grupo;
import patrones.strategy.IEstrategiaClasificacion;

import java.util.List;


public class ClasificadorGrupos {

    private IEstrategiaClasificacion estrategiaClasificacion;

    public ClasificadorGrupos(IEstrategiaClasificacion estrategia) {

        this.estrategiaClasificacion = estrategia;
    }


    public List<Equipo> clasificar(Grupo grupo) {

        return estrategiaClasificacion.clasificar(grupo);
    }


    public void setEstrategia(IEstrategiaClasificacion e) {
        this.estrategiaClasificacion = e;
    }

    public IEstrategiaClasificacion getEstrategia() {
        return estrategiaClasificacion;
    }
}