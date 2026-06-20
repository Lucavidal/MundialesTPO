package modelo;

import modelo.enums.EstadoPartido;
import modelo.enums.FaseTorneo;

import java.time.LocalDate;

public class Partido {

    private int id;
    private Equipo equipoLocal;
    private Equipo equipoVisitante;
    private Estadio estadio;
    private Resultado resultado;
    private FaseTorneo fase;
    private EstadoPartido estado;
    private LocalDate fecha;
    private String hora; // ej: "15:00"

    public Partido(int id, Equipo equipoLocal, Equipo equipoVisitante,
                   Estadio estadio, FaseTorneo fase, LocalDate fecha) {
        this.id              = id;
        this.equipoLocal     = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.estadio         = estadio;
        this.fase            = fase;
        this.fecha           = fecha;
        this.estado          = EstadoPartido.PENDIENTE;
        this.resultado       = null;
        this.hora            = "";
    }

    public int           getId()              { return id; }
    public Equipo        getEquipoLocal()     { return equipoLocal; }
    public Equipo        getEquipoVisitante() { return equipoVisitante; }
    public Estadio       getEstadio()         { return estadio; }
    public Resultado     getResultado()       { return resultado; }
    public FaseTorneo    getFase()            { return fase; }
    public EstadoPartido getEstado()          { return estado; }
    public LocalDate     getFecha()           { return fecha; }
    public String        getHora()            { return hora; }

    public void setResultado(Resultado r)     { this.resultado = r; }
    public void setEstado(EstadoPartido e)    { this.estado = e; }
    public void setHora(String hora)          { this.hora = hora; }

    @Override
    public String toString() {
        String res = (resultado != null) ? resultado.toString() : "Sin resultado";
        String est = (estadio  != null) ? estadio.getNombre()  : "N/A";
        String h   = (hora != null && !hora.isEmpty()) ? " " + hora : "";
        return String.format("Partido[%d] %s vs %s | %s | %s%s | %s | %s",
                id,
                equipoLocal.getNombre(),
                equipoVisitante.getNombre(),
                res, est, h, fase, estado);
    }
}