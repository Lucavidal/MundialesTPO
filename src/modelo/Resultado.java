package modelo;


public class Resultado {

    private int golesLocal;
    private int golesVisitante;
    private int penalesLocal;
    private int penalesVisitante;
    private boolean seDefinioEnPenales;

    /** Constructor para resultado normal (sin penales) */
    public Resultado(int golesLocal, int golesVisitante) {
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
        this.penalesLocal = 0;
        this.penalesVisitante = 0;
        this.seDefinioEnPenales = false;
    }

    /** Constructor para resultado que se definió en penales */
    public Resultado(int golesLocal, int golesVisitante,
                     int penalesLocal, int penalesVisitante) {
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
        this.penalesLocal = penalesLocal;
        this.penalesVisitante = penalesVisitante;
        this.seDefinioEnPenales = true;
    }

    public int getGolesLocal() {
        return golesLocal;
    }

    public int getGolesVisitante() {
        return golesVisitante;
    }

    public int getPenalesLocal() {
        return penalesLocal;
    }

    public int getPenalesVisitante() {
        return penalesVisitante;
    }

    public boolean isSeDefinioEnPenales() {
        return seDefinioEnPenales;
    }


    public Equipo getGanador(Equipo local, Equipo visitante) {
        if (seDefinioEnPenales) {
            return penalesLocal > penalesVisitante ? local : visitante;
        }
        if (golesLocal > golesVisitante) return local;
        if (golesVisitante > golesLocal) return visitante;
        return null;
    }

    @Override
    public String toString() {
        String base = golesLocal + " - " + golesVisitante;
        if (seDefinioEnPenales) {
            base += " (Penales: " + penalesLocal + " - " + penalesVisitante + ")";
        }
        return base;
    }
}