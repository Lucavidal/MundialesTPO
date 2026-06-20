package datos;

import modelo.Equipo;
import modelo.Jugador;
import modelo.enums.PosicionJugador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CargadorJSON {

    private String rutaArchivo;

    public CargadorJSON() {

        this.rutaArchivo = "equipos.json";
    }


    public List<Equipo> cargarEquipos() {
        List<Equipo> equipos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {


            StringBuilder sb = new StringBuilder();
            String linea;
            while ((linea = reader.readLine()) != null) {
                sb.append(linea.trim());
            }
            String json = sb.toString().trim();

            if (json.isEmpty() || json.equals("[]")) {
                System.out.println("El archivo JSON esta vacio.");
                return equipos;
            }


            json = json.substring(1, json.length() - 1);

            // Divide en bloques de objetos equipo { ... }
            List<String> bloques = dividirBloques(json);

            for (String bloque : bloques) {
                Equipo equipo = parsearEquipo(bloque.trim());
                if (equipo != null) {
                    equipos.add(equipo);
                }
            }

            System.out.println("JSON cargado: " + equipos.size() + " equipos.");

        } catch (IOException e) {
            System.out.println("No se encontro '" + rutaArchivo
                    + "'. Se usaran datos de prueba.");
        }

        return equipos;
    }


    private Equipo parsearEquipo(String bloque) {
        try {
            int id        = Integer.parseInt(extraerCampo(bloque, "id"));
            String nombre = extraerCampo(bloque, "nombre");
            String pais   = extraerCampo(bloque, "pais");

            if (nombre.isEmpty() || pais.isEmpty()) return null;

            Equipo equipo = new Equipo(id, nombre, pais);

            // Extrae el array de jugadores si existe
            String arrayJugadores = extraerArray(bloque, "jugadores");
            if (!arrayJugadores.isEmpty()) {
                for (String jBloque : dividirBloques(arrayJugadores)) {
                    Jugador j = parsearJugador(jBloque.trim());
                    if (j != null) equipo.agregarJugador(j);
                }
            }

            return equipo;

        } catch (Exception e) {
            System.out.println("Error al parsear equipo: " + e.getMessage());
            return null;
        }
    }

    private Jugador parsearJugador(String bloque) {
        try {
            int id          = Integer.parseInt(extraerCampo(bloque, "id"));
            String nombre   = extraerCampo(bloque, "nombre");
            String apellido = extraerCampo(bloque, "apellido");
            int dorsal      = Integer.parseInt(extraerCampo(bloque, "dorsal"));
            String posStr   = extraerCampo(bloque, "posicion").toUpperCase().trim();

            PosicionJugador posicion;
            try {
                posicion = PosicionJugador.valueOf(posStr);
            } catch (IllegalArgumentException e) {
                posicion = PosicionJugador.MEDIOCAMPISTA; // fallback
            }

            return new Jugador(id, nombre, apellido, dorsal, posicion);

        } catch (Exception e) {
            System.out.println("Error al parsear jugador: " + e.getMessage());
            return null;
        }
    }


    private List<String> dividirBloques(String json) {
        List<String> bloques = new ArrayList<>();
        int nivel = 0;
        int inicio = -1;

        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '{') {
                if (nivel == 0) inicio = i;
                nivel++;
            } else if (c == '}') {
                nivel--;
                if (nivel == 0 && inicio != -1) {
                    bloques.add(json.substring(inicio, i + 1));
                    inicio = -1;
                }
            }
        }
        return bloques;
    }


    private String extraerCampo(String json, String campo) {
        String clave = "\"" + campo + "\"";
        int idx = json.indexOf(clave);
        if (idx == -1) return "";

        int dospuntos = json.indexOf(":", idx + clave.length());
        if (dospuntos == -1) return "";

        // Avanza hasta el primer caracter no-espacio
        int inicio = dospuntos + 1;
        while (inicio < json.length() && json.charAt(inicio) == ' ') inicio++;

        if (inicio >= json.length()) return "";

        if (json.charAt(inicio) == '"') {
            // Valor string: busca el cierre de comilla
            int fin = json.indexOf("\"", inicio + 1);
            if (fin == -1) return "";
            return json.substring(inicio + 1, fin);
        } else {
            // Valor numerico u otro: lee hasta coma, llave o salto de linea
            int fin = inicio;
            while (fin < json.length()
                    && json.charAt(fin) != ','
                    && json.charAt(fin) != '}'
                    && json.charAt(fin) != '\n'
                    && json.charAt(fin) != '\r') {
                fin++;
            }
            return json.substring(inicio, fin).trim();
        }
    }


    private String extraerArray(String json, String campo) {
        String clave = "\"" + campo + "\"";
        int idx = json.indexOf(clave);
        if (idx == -1) return "";

        int abre = json.indexOf("[", idx);
        if (abre == -1) return "";

        int nivel = 0;
        int cierra = -1;

        for (
                int i = abre; i < json.length(); i++) {
            if (json.charAt(i) == '[') nivel++;
            else if (json.charAt(i) == ']') {
                nivel--;
                if (nivel == 0) {
                    cierra = i;
                    break;
                }
            }
        }

        if (cierra == -1) return "";
        return json.substring(abre + 1, cierra);
    }
}