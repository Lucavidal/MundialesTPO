package modelo;

public class Estadio {

    private int id;
    private String nombre;
    private String ciudad;
    private String pais;
    private int capacidad;

    public Estadio(int id, String nombre, String ciudad, String pais, int capacidad) {
        this.id        = id;
        this.nombre    = nombre;
        this.ciudad    = ciudad;
        this.pais      = pais;
        this.capacidad = capacidad;
    }

    public int    getId()        { return id;}
    public String getNombre()    { return nombre; }
    public String getCiudad()    { return ciudad; }
    public String getPais()      { return pais; }
    public int    getCapacidad() { return capacidad; }

    @Override
    public String toString() {
        return String.format("Estadio[%d] %s - %s, %s (Cap: %d)",
                id, nombre, ciudad, pais, capacidad);
    }
}