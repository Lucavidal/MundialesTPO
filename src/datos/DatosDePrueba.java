package datos;

import modelo.Equipo;
import modelo.enums.PosicionJugador;
import modelo.Jugador;
import modelo.Estadio;

import java.util.ArrayList;
import java.util.List;

public class DatosDePrueba {

    public List<Equipo> cargarEquipos() {
        List<Equipo> equipos = new ArrayList<>();

        equipos.add(new Equipo(1,  "Mexico",          "Mexico"));
        equipos.add(new Equipo(2,  "Sudafrica",       "Sudafrica"));
        equipos.add(new Equipo(3,  "Corea del Sur",   "Corea del Sur"));
        equipos.add(new Equipo(4,  "Gales",           "Gales"));
        equipos.add(new Equipo(5,  "Canada",          "Canada"));
        equipos.add(new Equipo(6,  "Bosnia",          "Bosnia"));
        equipos.add(new Equipo(7,  "Catar",           "Catar"));
        equipos.add(new Equipo(8,  "Suiza",           "Suiza"));
        equipos.add(new Equipo(9,  "Brasil",          "Brasil"));
        equipos.add(new Equipo(10, "Marruecos",       "Marruecos"));
        equipos.add(new Equipo(11, "Haiti",           "Haiti"));
        equipos.add(new Equipo(12, "Escocia",         "Escocia"));
        equipos.add(new Equipo(13, "Estados Unidos",  "Estados Unidos"));
        equipos.add(new Equipo(14, "Paraguay",        "Paraguay"));
        equipos.add(new Equipo(15, "Australia",       "Australia"));
        equipos.add(new Equipo(16, "Polonia",         "Polonia"));
        equipos.add(new Equipo(17, "Alemania",        "Alemania"));
        equipos.add(new Equipo(18, "Curazao",         "Curazao"));
        equipos.add(new Equipo(19, "Costa de Marfil", "Costa de Marfil"));
        equipos.add(new Equipo(20, "Ecuador",         "Ecuador"));
        equipos.add(new Equipo(21, "Paises Bajos",    "Paises Bajos"));
        equipos.add(new Equipo(22, "Japon",           "Japon"));
        equipos.add(new Equipo(23, "Turquia",         "Turquia"));
        equipos.add(new Equipo(24, "Tunez",           "Tunez"));
        equipos.add(new Equipo(25, "Belgica",         "Belgica"));
        equipos.add(new Equipo(26, "Egipto",          "Egipto"));
        equipos.add(new Equipo(27, "Iran",            "Iran"));
        equipos.add(new Equipo(28, "Nueva Zelanda",   "Nueva Zelanda"));
        equipos.add(new Equipo(29, "Espana",          "Espana"));
        equipos.add(new Equipo(30, "Cabo Verde",      "Cabo Verde"));
        equipos.add(new Equipo(31, "Arabia Saudita",  "Arabia Saudita"));
        equipos.add(new Equipo(32, "Uruguay",         "Uruguay"));
        equipos.add(new Equipo(33, "Francia",         "Francia"));
        equipos.add(new Equipo(34, "Senegal",         "Senegal"));
        equipos.add(new Equipo(35, "Irak",            "Irak"));
        equipos.add(new Equipo(36, "Noruega",         "Noruega"));
        equipos.add(new Equipo(37, "Argentina",       "Argentina"));
        equipos.add(new Equipo(38, "Argelia",         "Argelia"));
        equipos.add(new Equipo(39, "Austria",         "Austria"));
        equipos.add(new Equipo(40, "Jordania",        "Jordania"));
        equipos.add(new Equipo(41, "Portugal",        "Portugal"));
        equipos.add(new Equipo(42, "Jamaica",         "Jamaica"));
        equipos.add(new Equipo(43, "Uzbekistan",      "Uzbekistan"));
        equipos.add(new Equipo(44, "Colombia",        "Colombia"));
        equipos.add(new Equipo(45, "Inglaterra",      "Inglaterra"));
        equipos.add(new Equipo(46, "Croacia",         "Croacia"));
        equipos.add(new Equipo(47, "Ghana",           "Ghana"));
        equipos.add(new Equipo(48, "Panama",          "Panama"));

        return equipos;
    }

    /**
     * Carga 3 jugadores por equipo.
     * Los jugadores quedan asociados directamente al equipo.
     */
    public void cargarJugadores(List<Equipo> equipos) {
        int idJugador = 1;

        String[][] plantillaArgentina = {
                {"Emiliano", "Martínez"},
                {"Lionel",   "Messi"},
                {"Julián",   "Álvarez"}
        };

        PosicionJugador[] posiciones = {
                PosicionJugador.PORTERO,
                PosicionJugador.MEDIOCAMPISTA,
                PosicionJugador.DELANTERO
        };

        for (Equipo equipo : equipos) {

            /*
             * Primero cargamos jugadores genéricos para todos los equipos.
             */
            for (int i = 0; i < 3; i++) {
                String nombre = "Jugador" + (i + 1);
                String apellido = equipo.getNombre().replaceAll("\\s+", "");
                int dorsal = (i == 0) ? 1 : (i == 1 ? 10 : 9);

                Jugador jugador = new Jugador(
                        idJugador++,
                        nombre,
                        apellido,
                        dorsal,
                        posiciones[i]
                );

                equipo.agregarJugador(jugador);
            }

            /*
             * Si el equipo es Argentina, reemplazamos los jugadores genéricos
             * por jugadores reales.
             */
            if (equipo.getNombre().equalsIgnoreCase("Argentina")) {
                equipo.getJugadores().clear();

                for (int i = 0; i < plantillaArgentina.length; i++) {
                    int dorsal = (i == 0) ? 23 : (i == 1 ? 10 : 9);

                    Jugador jugador = new Jugador(
                            idJugador++,
                            plantillaArgentina[i][0],
                            plantillaArgentina[i][1],
                            dorsal,
                            posiciones[i]
                    );

                    equipo.agregarJugador(jugador);
                }
            }
        }
    }

    /**
     * Crea y retorna estadios de ejemplo.
     */
    public List<Estadio> cargarEstadios() {
        List<Estadio> estadios = new ArrayList<>();

        estadios.add(new Estadio(1,  "Mercedes-Benz Stadium",   "Atlanta",             "USA",    71000));
        estadios.add(new Estadio(2,  "Gillette Stadium",        "Boston",              "USA",    65878));
        estadios.add(new Estadio(3,  "AT&T Stadium",            "Dallas",              "USA",    80000));
        estadios.add(new Estadio(4,  "NRG Stadium",             "Houston",             "USA",    72220));
        estadios.add(new Estadio(5,  "Arrowhead Stadium",       "Kansas City",         "USA",    76416));
        estadios.add(new Estadio(6,  "SoFi Stadium",            "Los Angeles",         "USA",    70240));
        estadios.add(new Estadio(7,  "Hard Rock Stadium",       "Miami",               "USA",    65326));
        estadios.add(new Estadio(8,  "MetLife Stadium",         "New York/New Jersey", "USA",    82500));
        estadios.add(new Estadio(9,  "Lincoln Financial Field", "Philadelphia",        "USA",    69176));
        estadios.add(new Estadio(10, "Levi's Stadium",          "San Francisco",       "USA",    68500));
        estadios.add(new Estadio(11, "Lumen Field",             "Seattle",             "USA",    68740));
        estadios.add(new Estadio(12, "BMO Field",               "Toronto",             "Canada", 30000));
        estadios.add(new Estadio(13, "BC Place",                "Vancouver",           "Canada", 54500));
        estadios.add(new Estadio(14, "Estadio Akron",           "Guadalajara",         "Mexico", 49850));
        estadios.add(new Estadio(15, "Estadio Azteca",          "Ciudad de Mexico",    "Mexico", 87523));
        estadios.add(new Estadio(16, "Estadio BBVA",            "Monterrey",           "Mexico", 53500));

        return estadios;
    }
}