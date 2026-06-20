package modelo;



public class RegistroTabla {

    private Equipo equipo;
    private int puntaje;
    private int partidosJugados;
    private int partidosGanados;
    private int partidosEmpatados;
    private int partidosPerdidos;
    private int golesFavor;
    private int golesContra;

    public RegistroTabla(Equipo equipo) {
        this.equipo = equipo;
        this.puntaje = 0;
        this.partidosJugados = 0;
        this.partidosGanados = 0;
        this.partidosEmpatados = 0;
        this.partidosPerdidos = 0;
        this.golesFavor = 0;
        this.golesContra = 0;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public int getPartidosJugados() {
        return partidosJugados;
    }

    public int getPartidosGanados() {
        return partidosGanados;
    }

    public int getPartidosEmpatados() {
        return partidosEmpatados;
    }

    public int getPartidosPerdidos() {
        return partidosPerdidos;
    }

    public int getGolesFavor() {
        return golesFavor;
    }

    public int getGolesContra() {
        return golesContra;
    }

    public int getDiferenciaGoles() {
        return golesFavor - golesContra;
    }


    public void actualizarConResultado(Resultado r, boolean esLocal) {
        int golesPropios    = esLocal ? r.getGolesLocal()     : r.getGolesVisitante();
        int golesRivales    = esLocal ? r.getGolesVisitante() : r.getGolesLocal();

        this.golesFavor  += golesPropios;
        this.golesContra += golesRivales;
        this.partidosJugados++;

        if (golesPropios > golesRivales) {
            // Victoria
            this.partidosGanados++;
            this.puntaje += 3;
        } else if (golesPropios == golesRivales) {
            // Empate
            this.partidosEmpatados++;
            this.puntaje += 1;
        } else {
            // Derrota
            this.partidosPerdidos++;
        }
    }

    @Override
    public String toString() {
        return String.format("%-20s | PJ:%2d | PG:%2d | PE:%2d | PP:%2d | GF:%3d | GC:%3d | DG:%+3d | Pts:%3d",
                equipo.getNombre(),
                partidosJugados, partidosGanados, partidosEmpatados, partidosPerdidos,
                golesFavor, golesContra, getDiferenciaGoles(), puntaje);
    }
}