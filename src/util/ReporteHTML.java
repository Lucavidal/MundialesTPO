package util;

import modelo.*;
import modelo.enums.EstadoPartido;
import modelo.enums.FaseTorneo;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReporteHTML {

    private Torneo torneo;

    private static final Map<String, String> CODIGOS_PAIS = new HashMap<>();

    static {
        CODIGOS_PAIS.put("Mexico",          "mx");
        CODIGOS_PAIS.put("Sudafrica",       "za");
        CODIGOS_PAIS.put("Corea del Sur",   "kr");
        CODIGOS_PAIS.put("Gales",           "gb-wls");
        CODIGOS_PAIS.put("Canada",          "ca");
        CODIGOS_PAIS.put("Italia",          "it");
        CODIGOS_PAIS.put("Catar",           "qa");
        CODIGOS_PAIS.put("Suiza",           "ch");
        CODIGOS_PAIS.put("Brasil",          "br");
        CODIGOS_PAIS.put("Marruecos",       "ma");
        CODIGOS_PAIS.put("Haiti",           "ht");
        CODIGOS_PAIS.put("Escocia",         "gb-sct");
        CODIGOS_PAIS.put("Estados Unidos",  "us");
        CODIGOS_PAIS.put("Paraguay",        "py");
        CODIGOS_PAIS.put("Australia",       "au");
        CODIGOS_PAIS.put("Polonia",         "pl");
        CODIGOS_PAIS.put("Alemania",        "de");
        CODIGOS_PAIS.put("Curazao",         "cw");
        CODIGOS_PAIS.put("Costa de Marfil", "ci");
        CODIGOS_PAIS.put("Ecuador",         "ec");
        CODIGOS_PAIS.put("Paises Bajos",    "nl");
        CODIGOS_PAIS.put("Japon",           "jp");
        CODIGOS_PAIS.put("Turquia",         "tr");
        CODIGOS_PAIS.put("Tunez",           "tn");
        CODIGOS_PAIS.put("Belgica",         "be");
        CODIGOS_PAIS.put("Egipto",          "eg");
        CODIGOS_PAIS.put("Iran",            "ir");
        CODIGOS_PAIS.put("Nueva Zelanda",   "nz");
        CODIGOS_PAIS.put("Espana",          "es");
        CODIGOS_PAIS.put("Cabo Verde",      "cv");
        CODIGOS_PAIS.put("Arabia Saudita",  "sa");
        CODIGOS_PAIS.put("Uruguay",         "uy");
        CODIGOS_PAIS.put("Francia",         "fr");
        CODIGOS_PAIS.put("Senegal",         "sn");
        CODIGOS_PAIS.put("Bolivia",         "bo");
        CODIGOS_PAIS.put("Noruega",         "no");
        CODIGOS_PAIS.put("Argentina",       "ar");
        CODIGOS_PAIS.put("Argelia",         "dz");
        CODIGOS_PAIS.put("Austria",         "at");
        CODIGOS_PAIS.put("Jordania",        "jo");
        CODIGOS_PAIS.put("Portugal",        "pt");
        CODIGOS_PAIS.put("Jamaica",         "jm");
        CODIGOS_PAIS.put("Uzbekistan",      "uz");
        CODIGOS_PAIS.put("Colombia",        "co");
        CODIGOS_PAIS.put("Inglaterra",      "gb-eng");
        CODIGOS_PAIS.put("Croacia",         "hr");
        CODIGOS_PAIS.put("Ghana",           "gh");
        CODIGOS_PAIS.put("Panama",          "pa");
        CODIGOS_PAIS.put("Irak",            "iq");
    }

    public ReporteHTML(Torneo torneo) {
        this.torneo = torneo;
    }

    public void generarYAbrir() {
        String html = construirHTML();
        File archivo = new File("reporte_mundial.html");

        try (FileWriter writer = new FileWriter(archivo)) {
            writer.write(html);
            System.out.println("Reporte generado: " + archivo.getAbsolutePath());

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(archivo.toURI());
            }

        } catch (IOException e) {
            System.out.println("Error al generar el reporte: " + e.getMessage());
        }
    }

    private String construirHTML() {
        StringBuilder sb = new StringBuilder();

        sb.append(htmlHead());
        sb.append("<body>\n");
        sb.append(htmlHeader());
        sb.append("<div class='container'>\n");

        if (torneo.getCampeon() != null) {
            sb.append(htmlCampeon());
        }

        sb.append("""
            <div class='tabs'>
              <button class='tab active' onclick='showTab("grupos",this)'>GRUPOS</button>
              <button class='tab' onclick='showTab("bracket",this)'>ELIMINATORIAS</button>
            </div>
            """);

        sb.append("<div id='grupos' class='tab-content'>\n");

        if (!torneo.getGrupos().isEmpty()) {
            sb.append("<div class='groups-grid'>\n");

            for (Grupo grupo : torneo.getGrupos()) {
                sb.append(htmlGrupo(grupo));
            }

            sb.append("</div>\n");
        } else {
            sb.append("<p class='empty'>No hay grupos generados aun.</p>\n");
        }

        sb.append("</div>\n");

        sb.append("<div id='bracket' class='tab-content' style='display:none'>\n");
        sb.append(htmlBracket());
        sb.append("</div>\n");

        sb.append("</div>\n");
        sb.append(htmlFooter());
        sb.append(htmlScript());
        sb.append("</body></html>");

        return sb.toString();
    }

    private String htmlBracket() {
        List<Partido> eliminatorios = torneo.getPartidosEliminatorios();

        List<Partido> dieciseisavos = filtrar(eliminatorios, FaseTorneo.DIECISEISAVOS);
        List<Partido> octavos = filtrar(eliminatorios, FaseTorneo.OCTAVOS);
        List<Partido> cuartos = filtrar(eliminatorios, FaseTorneo.CUARTOS);
        List<Partido> semifinal = filtrar(eliminatorios, FaseTorneo.SEMIFINAL);
        List<Partido> finalPartidos = filtrar(eliminatorios, FaseTorneo.FINAL);
        List<Partido> tercerPuesto = filtrar(eliminatorios, FaseTorneo.TERCER_PUESTO);

        if (eliminatorios.isEmpty() && torneo.getGrupos().isEmpty()) {
            return "<div class='empty-bracket'><p>Genera y simula la fase de grupos primero.</p></div>";
        }

        StringBuilder sb = new StringBuilder();

        sb.append("""
            <div class='leyenda'>
              <span class='ley-item'><span class='dot dot-verde'></span> Clasificado directo</span>
              <span class='ley-item'><span class='dot dot-ambar'></span> Posible mejor 3ro</span>
              <span class='ley-item'><span class='dot dot-gris'></span> Eliminado</span>
            </div>
            """);

        sb.append("<div class='bracket-wrapper'>\n");
        sb.append("<div class='bracket'>\n");

        sb.append(col("col-left", "DIECISEISAVOS", "col-azul", sublist(dieciseisavos, 0, 8)));
        sb.append(col("col-left", "OCTAVOS", "col-verde", sublist(octavos, 0, 4)));
        sb.append(col("col-left", "CUARTOS", "col-naranja", sublist(cuartos, 0, 2)));
        sb.append(col("col-left", "SEMIFINAL", "col-rojo", sublist(semifinal, 0, 1)));

        sb.append("<div class='col col-center'>\n");
        sb.append(rondaLabel("FINAL", "col-dorado"));

        if (!finalPartidos.isEmpty()) {
            sb.append(matchCard(finalPartidos.get(0)));
        } else {
            sb.append(matchCardVacio());
        }

        sb.append(rondaLabel("TERCER PUESTO", "col-bronce"));

        if (!tercerPuesto.isEmpty()) {
            sb.append(matchCard(tercerPuesto.get(0)));
        } else {
            sb.append(matchCardVacio());
        }

        if (torneo.getCampeon() != null) {
            sb.append(String.format("""
                <div class='champ-center'>
                  <div class='champ-trophy'>&#127942;</div>
                  <div class='champ-flag'>%s</div>
                  <div class='champ-name'>%s</div>
                </div>
                """,
                    bandera(torneo.getCampeon().getNombre()),
                    escapeHtml(torneo.getCampeon().getNombre())));
        }

        sb.append("</div>\n");

        sb.append(col("col-right", "SEMIFINAL", "col-rojo", sublist(semifinal, 1, 2)));
        sb.append(col("col-right", "CUARTOS", "col-naranja", sublist(cuartos, 2, 4)));
        sb.append(col("col-right", "OCTAVOS", "col-verde", sublist(octavos, 4, 8)));
        sb.append(col("col-right", "DIECISEISAVOS", "col-azul", sublist(dieciseisavos, 8, 16)));

        sb.append("</div>\n");
        sb.append("</div>\n");

        return sb.toString();
    }

    private List<Partido> sublist(List<Partido> lista, int desde, int hasta) {
        List<Partido> resultado = new ArrayList<>();

        for (int i = desde; i < hasta && i < lista.size(); i++) {
            resultado.add(lista.get(i));
        }

        return resultado;
    }

    private String col(String lado, String titulo, String colorClass, List<Partido> partidos) {
        int necesarios = calcNecesarios(titulo);
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("<div class='col %s'>\n", lado));
        sb.append(rondaLabel(titulo, colorClass));

        for (int i = 0; i < necesarios; i++) {
            if (i < partidos.size()) {
                sb.append(matchCard(partidos.get(i)));
            } else {
                sb.append(matchCardVacio());
            }
        }

        sb.append("</div>\n");

        return sb.toString();
    }

    private int calcNecesarios(String titulo) {
        switch (titulo) {
            case "DIECISEISAVOS":
                return 8;
            case "OCTAVOS":
                return 4;
            case "CUARTOS":
                return 2;
            default:
                return 1;
        }
    }

    private String rondaLabel(String titulo, String css) {
        return String.format("<div class='round-label %s'>%s</div>\n", css, titulo);
    }

    private String matchCard(Partido partido) {
        String equipo1 = partido.getEquipoLocal().getNombre();
        String equipo2 = partido.getEquipoVisitante().getNombre();

        boolean finalizado = partido.getEstado() == EstadoPartido.FINALIZADO;

        Integer goles1 = null;
        Integer goles2 = null;
        String penales = "";

        if (finalizado && partido.getResultado() != null) {
            goles1 = partido.getResultado().getGolesLocal();
            goles2 = partido.getResultado().getGolesVisitante();

            if (partido.getResultado().isSeDefinioEnPenales()) {
                penales = "(" + partido.getResultado().getPenalesLocal()
                        + "-" + partido.getResultado().getPenalesVisitante() + "p)";
            }
        }

        Equipo ganador = null;

        if (finalizado && partido.getResultado() != null) {
            ganador = partido.getResultado().getGanador(
                    partido.getEquipoLocal(),
                    partido.getEquipoVisitante()
            );
        }

        boolean gana1 = ganador != null && ganador.getId() == partido.getEquipoLocal().getId();
        boolean gana2 = ganador != null && ganador.getId() == partido.getEquipoVisitante().getId();

        String infoEstadio = partido.getEstadio() != null
                ? escapeHtml(partido.getEstadio().getNombre()) + " &middot; " + escapeHtml(partido.getEstadio().getCiudad())
                : "";

        String infoHora = partido.getHora() != null && !partido.getHora().isEmpty()
                ? partido.getHora()
                : "";

        String penales1 = gana1 && !penales.isEmpty() ? penales : "";
        String penales2 = gana2 && !penales.isEmpty() ? penales : "";

        String metaBlock = "";

        if (!infoEstadio.isEmpty() || !infoHora.isEmpty()) {
            metaBlock = "<div class='match-meta'>"
                    + (!infoHora.isEmpty() ? "<span class='meta-hora'>&#128336; " + infoHora + "</span>" : "")
                    + (!infoEstadio.isEmpty() ? "<span class='meta-est'>&#127974; " + infoEstadio + "</span>" : "")
                    + "</div>";
        }

        return String.format("""
            <div class='match'>
              <div class='match-team %s'>
                %s
                <span class='tname'>%s</span>
                <div class='score-block'>
                  %s
                  <span class='tscore %s'>%s</span>
                </div>
              </div>
              <div class='match-divider'></div>
              <div class='match-team %s'>
                %s
                <span class='tname'>%s</span>
                <div class='score-block'>
                  %s
                  <span class='tscore %s'>%s</span>
                </div>
              </div>
              %s
            </div>
            """,
                gana1 ? "win" : "",
                bandera(equipo1),
                escapeHtml(equipo1),
                !penales1.isEmpty() ? "<span class='pen'>" + penales1 + "</span>" : "",
                gana1 ? "score-win" : "",
                goles1 != null ? goles1 : "-",

                gana2 ? "win" : "",
                bandera(equipo2),
                escapeHtml(equipo2),
                !penales2.isEmpty() ? "<span class='pen'>" + penales2 + "</span>" : "",
                gana2 ? "score-win" : "",
                goles2 != null ? goles2 : "-",

                metaBlock
        );
    }

    private String matchCardVacio() {
        return """
            <div class='match empty-match'>
              <div class='match-team tbd'>
                <span class='flag-ph'>?</span>
                <span class='tname'>Por definir</span>
                <span class='tscore'>-</span>
              </div>
              <div class='match-divider'></div>
              <div class='match-team tbd'>
                <span class='flag-ph'>?</span>
                <span class='tname'>Por definir</span>
                <span class='tscore'>-</span>
              </div>
            </div>
            """;
    }

    private String htmlGrupo(Grupo grupo) {
        String color = colorParaGrupo(grupo.getNombre());
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("<div class='group-card' style='--color:%s'>\n", color));

        sb.append(String.format("""
            <div class='group-header'>
              <span class='group-letter'>%s</span>
              <span class='group-label'>GRUPO</span>
            </div>
            """, grupo.getNombre()));

        sb.append("<div class='group-body'>\n");

        grupo.getTablaPosiciones().ordenar();
        List<RegistroTabla> registros = grupo.getTablaPosiciones().getRegistros();

        if (!registros.isEmpty()) {
            sb.append("""
                <div class='table-header'>
                  <span>Equipo</span>
                  <span>PJ</span><span>GF</span><span>GC</span>
                  <span>DG</span><span>Pts</span>
                </div>
                """);

            for (int i = 0; i < registros.size(); i++) {
                RegistroTabla registro = registros.get(i);

                String rowClass = i < 2
                        ? "table-row top2"
                        : i == 2 ? "table-row top3" : "table-row";

                String dot = i < 2
                        ? "<span class='dot dot-verde'></span>"
                        : i == 2
                        ? "<span class='dot dot-ambar'></span>"
                        : "<span class='dot dot-gris'></span>";

                int diferenciaGoles = registro.getDiferenciaGoles();
                String diferenciaTexto = diferenciaGoles >= 0
                        ? "+" + diferenciaGoles
                        : String.valueOf(diferenciaGoles);

                sb.append(String.format("""
                    <div class='%s'>
                      <div class='team-cell'>%s%s<span class='tname-g'>%s</span></div>
                      <span class='cell'>%d</span>
                      <span class='cell'>%d</span>
                      <span class='cell'>%d</span>
                      <span class='cell'>%s</span>
                      <span class='cell pts'>%d</span>
                    </div>
                    """,
                        rowClass,
                        dot,
                        bandera(registro.getEquipo().getNombre()),
                        escapeHtml(registro.getEquipo().getNombre()),
                        registro.getPartidosJugados(),
                        registro.getGolesFavor(),
                        registro.getGolesContra(),
                        diferenciaTexto,
                        registro.getPuntaje()));
            }

        } else {
            for (Equipo equipo : grupo.getEquipos()) {
                sb.append(String.format("""
                    <div class='table-row'>
                      <div class='team-cell'>
                        <span class='dot dot-gris'></span>
                        %s
                        <span class='tname-g'>%s</span>
                      </div>
                      <span class='cell'>-</span>
                      <span class='cell'>-</span>
                      <span class='cell'>-</span>
                      <span class='cell'>-</span>
                      <span class='cell pts'>-</span>
                    </div>
                    """,
                        bandera(equipo.getNombre()),
                        escapeHtml(equipo.getNombre())));
            }
        }

        List<Partido> partidos = grupo.getPartidos();

        boolean hayResultados = partidos.stream()
                .anyMatch(p -> p.getEstado() == EstadoPartido.FINALIZADO);

        if (hayResultados) {
            sb.append("<div class='results-section'><div class='results-title'>RESULTADOS</div>\n");

            for (Partido partido : partidos) {
                if (partido.getEstado() == EstadoPartido.FINALIZADO && partido.getResultado() != null) {
                    Resultado resultado = partido.getResultado();

                    String penalesTexto = resultado.isSeDefinioEnPenales()
                            ? " (" + resultado.getPenalesLocal() + "-" + resultado.getPenalesVisitante() + "p)"
                            : "";

                    String estadioTexto = partido.getEstadio() != null
                            ? " &middot; " + partido.getEstadio().getCiudad()
                            : "";

                    String horaTexto = partido.getHora() != null && !partido.getHora().isEmpty()
                            ? " " + partido.getHora()
                            : "";

                    sb.append(String.format("""
                        <div class='result-row'>
                          <span class='result-team'>%s %s</span>
                          <span class='result-score'>%d-%d%s</span>
                          <span class='result-team right'>%s %s</span>
                        </div>
                        <div class='result-meta'>%s%s</div>
                        """,
                            bandera(partido.getEquipoLocal().getNombre()),
                            escapeHtml(partido.getEquipoLocal().getNombre()),
                            resultado.getGolesLocal(),
                            resultado.getGolesVisitante(),
                            penalesTexto,
                            escapeHtml(partido.getEquipoVisitante().getNombre()),
                            bandera(partido.getEquipoVisitante().getNombre()),
                            estadioTexto,
                            horaTexto));
                }
            }

            sb.append("</div>\n");
        }

        sb.append("</div>\n");
        sb.append("</div>\n");

        return sb.toString();
    }

    private String htmlHead() {
        return """
            <!DOCTYPE html>
            <html lang="es">
            <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>FIFA World Cup 2026</title>
            <style>
              * { box-sizing: border-box; margin: 0; padding: 0; }

              :root {
                --azul:    #1B3F8B;
                --rojo:    #C62828;
                --verde:   #2E7D32;
                --naranja: #EF6C00;
                --dorado:  #F9A825;
                --bronce:  #B5703F;
                --blanco:  #FFFFFF;
                --fondo:   #0D1117;
                --card:    #161B22;
                --borde:   #30363D;
              }

              body {
                background: var(--fondo);
                color: var(--blanco);
                font-family: 'Segoe UI', system-ui, sans-serif;
                min-height: 100vh;
              }

              .header {
                background: linear-gradient(135deg, var(--azul), var(--verde) 50%, var(--rojo));
                padding: 18px 40px;
                display: flex;
                align-items: center;
                gap: 16px;
                border-bottom: 3px solid var(--dorado);
              }

              .header-badge {
                width: 52px;
                height: 52px;
                border-radius: 50%;
                background: var(--dorado);
                display: flex;
                align-items: center;
                justify-content: center;
                font-size: 28px;
                box-shadow: 0 0 20px rgba(249, 168, 37, .6);
              }

              .header-title {
                font-size: 24px;
                font-weight: 900;
                letter-spacing: 3px;
              }

              .header-sub {
                font-size: 11px;
                font-weight: 700;
                color: var(--dorado);
                letter-spacing: 4px;
                margin-top: 2px;
              }

              .header-hosts {
                font-size: 10px;
                color: rgba(255, 255, 255, .7);
                margin-top: 2px;
              }

              .container {
                padding: 22px 28px;
              }

              .champion-banner {
                text-align: center;
                padding: 20px 40px;
                margin-bottom: 24px;
                background: linear-gradient(90deg, rgba(249,168,37,.2), rgba(249,168,37,.05), rgba(249,168,37,.2));
                border: 3px solid var(--dorado);
                border-radius: 16px;
              }

              .champion-banner .trophy-big {
                font-size: 48px;
              }

              .champion-banner .ctitle {
                font-size: 11px;
                font-weight: 800;
                color: var(--dorado);
                letter-spacing: 5px;
                margin-top: 6px;
              }

              .champion-banner .cname {
                font-size: 32px;
                font-weight: 900;
                text-transform: uppercase;
                letter-spacing: 3px;
                margin-top: 4px;
              }

              .tabs {
                display: flex;
                gap: 10px;
                margin-bottom: 22px;
              }

              .tab {
                padding: 9px 28px;
                border-radius: 8px;
                cursor: pointer;
                font-weight: 800;
                font-size: 11px;
                letter-spacing: 3px;
                border: 2px solid var(--borde);
                background: transparent;
                color: #8B949E;
                transition: all .2s;
              }

              .tab.active {
                border-color: var(--dorado);
                color: var(--dorado);
                background: rgba(249, 168, 37, .1);
              }

              .leyenda {
                display: flex;
                gap: 16px;
                margin-bottom: 16px;
                flex-wrap: wrap;
              }

              .ley-item {
                display: flex;
                align-items: center;
                gap: 5px;
                font-size: 11px;
                color: #8B949E;
              }

              .groups-grid {
                display: grid;
                grid-template-columns: repeat(auto-fill, minmax(240px, 1fr));
                gap: 14px;
              }

              .group-card {
                background: var(--card);
                border-radius: 12px;
                overflow: hidden;
                border: 2px solid var(--color);
              }

              .group-header {
                background: var(--color);
                padding: 7px 13px;
                display: flex;
                align-items: center;
                justify-content: space-between;
              }

              .group-letter {
                font-size: 22px;
                font-weight: 900;
                color: #000;
              }

              .group-label {
                font-size: 10px;
                font-weight: 700;
                color: #000;
                opacity: .65;
              }

              .group-body {
                padding: 11px 13px;
              }

              .table-header {
                display: grid;
                grid-template-columns: 1fr 26px 26px 26px 26px 30px;
                font-size: 10px;
                color: #6B7280;
                margin-bottom: 3px;
                text-align: center;
              }

              .table-header span:first-child {
                text-align: left;
              }

              .table-row {
                display: grid;
                grid-template-columns: 1fr 26px 26px 26px 26px 30px;
                align-items: center;
                padding: 3px 0;
                border-bottom: 1px solid var(--borde);
                font-size: 10px;
              }

              .table-row.top2 {
                background: rgba(46, 125, 50, .12);
                border-radius: 3px;
              }

              .table-row.top3 {
                background: rgba(249, 168, 37, .10);
                border-radius: 3px;
              }

              .team-cell {
                display: flex;
                align-items: center;
                gap: 4px;
              }

              .dot {
                width: 6px;
                height: 6px;
                border-radius: 50%;
                display: inline-block;
                flex-shrink: 0;
              }

              .dot-verde {
                background: var(--verde);
              }

              .dot-ambar {
                background: var(--dorado);
              }

              .dot-gris {
                background: #484F58;
              }

              .flag {
                width: 20px;
                height: 14px;
                object-fit: cover;
                border-radius: 2px;
                flex-shrink: 0;
                box-shadow: 0 0 2px rgba(0,0,0,.4);
                vertical-align: middle;
              }

              .flag-ph {
                width: 20px;
                height: 14px;
                display: inline-flex;
                align-items: center;
                justify-content: center;
                font-size: 9px;
                color: #484F58;
                flex-shrink: 0;
              }

              .tname-g {
                color: #E6EDF3;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
                max-width: 85px;
                font-size: 10px;
              }

              .cell {
                text-align: center;
                color: #8B949E;
              }

              .cell.pts {
                color: var(--color);
                font-weight: 800;
                font-size: 11px;
              }

              .results-section {
                margin-top: 9px;
                border-top: 1px solid var(--borde);
                padding-top: 7px;
              }

              .results-title {
                font-size: 9px;
                color: #484F58;
                margin-bottom: 4px;
                letter-spacing: 2px;
              }

              .result-row {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 2px 0;
                font-size: 10px;
              }

              .result-team {
                color: #8B949E;
                max-width: 90px;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
              }

              .result-team.right {
                text-align: right;
              }

              .result-score {
                color: #E6EDF3;
                font-weight: 800;
                font-size: 11px;
                padding: 0 5px;
                white-space: nowrap;
              }

              .result-meta {
                font-size: 9px;
                color: #484F58;
                padding: 1px 0 3px 0;
                border-bottom: 1px solid var(--borde);
              }

              .bracket-wrapper {
                overflow-x: auto;
                padding-bottom: 20px;
              }

              .bracket {
                display: flex;
                align-items: stretch;
                justify-content: center;
                gap: 4px;
                min-width: 1400px;
                padding: 16px 8px;
              }

              .col {
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: space-around;
                flex: 1;
                min-width: 155px;
                gap: 5px;
              }

              .col-center {
                flex: 0 0 185px;
                justify-content: center;
                gap: 12px;
              }

              .round-label {
                font-size: 9px;
                font-weight: 800;
                letter-spacing: 2px;
                text-align: center;
                margin-bottom: 3px;
                width: 100%;
                padding: 5px 0;
                border-radius: 4px;
              }

              .col-azul {
                background: var(--azul);
                color: #fff;
              }

              .col-verde {
                background: var(--verde);
                color: #fff;
              }

              .col-naranja {
                background: var(--naranja);
                color: #fff;
              }

              .col-rojo {
                background: var(--rojo);
                color: #fff;
              }

              .col-dorado {
                background: var(--dorado);
                color: #000;
              }

              .col-bronce {
                background: var(--bronce);
                color: #fff;
              }

              .match {
                background: var(--card);
                border: 1px solid var(--borde);
                border-radius: 8px;
                overflow: hidden;
                width: 148px;
                flex-shrink: 0;
              }

              .empty-match {
                opacity: .4;
              }

              .match-team {
                display: flex;
                align-items: center;
                gap: 5px;
                padding: 5px 8px;
                flex-wrap: nowrap;
                min-width: 0;
              }

              .match-divider {
                height: 1px;
                background: var(--borde);
              }

              .match-team.win {
                background: rgba(249, 168, 37, .18);
              }

              .match-team.tbd {
                opacity: .5;
              }

              .tname {
                font-size: 10px;
                font-weight: 500;
                color: #E6EDF3;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
                flex: 1;
                min-width: 0;
              }

              .match-team.win .tname {
                color: var(--dorado);
                font-weight: 700;
              }

              .score-block {
                display: flex;
                align-items: center;
                gap: 3px;
                flex-shrink: 0;
              }

              .pen {
                font-size: 8px;
                color: #8B949E;
                white-space: nowrap;
              }

              .tscore {
                font-size: 12px;
                font-weight: 800;
                color: #484F58;
                min-width: 13px;
                text-align: right;
              }

              .tscore.score-win {
                color: var(--dorado);
              }

              .match-meta {
                padding: 3px 8px;
                background: rgba(255,255,255,.03);
                border-top: 1px solid var(--borde);
              }

              .meta-hora {
                font-size: 9px;
                color: var(--dorado);
                display: block;
                margin-bottom: 1px;
              }

              .meta-est {
                font-size: 8px;
                color: #6B7280;
                display: block;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
              }

              .champ-center {
                text-align: center;
                padding: 12px 8px;
                background: linear-gradient(135deg, rgba(249,168,37,.25), rgba(249,168,37,.08));
                border: 2px solid var(--dorado);
                border-radius: 12px;
                width: 160px;
              }

              .champ-trophy {
                font-size: 32px;
              }

              .champ-flag {
                margin-top: 6px;
              }

              .champ-flag img {
                width: 40px;
                height: 28px;
                object-fit: cover;
                border-radius: 3px;
                box-shadow: 0 0 6px rgba(249,168,37,.4);
              }

              .champ-name {
                font-size: 12px;
                font-weight: 900;
                color: var(--dorado);
                text-transform: uppercase;
                letter-spacing: 1px;
                margin-top: 6px;
              }

              .empty-bracket {
                text-align: center;
                padding: 60px;
                color: #484F58;
                font-size: 14px;
              }

              .empty {
                color: #484F58;
                padding: 20px;
                text-align: center;
              }

              .footer {
                text-align: center;
                padding: 18px;
                color: #484F58;
                font-size: 10px;
                letter-spacing: 2px;
                border-top: 1px solid var(--borde);
                margin-top: 28px;
              }
            </style>
            </head>
            """;
    }

    private String htmlHeader() {
        return String.format("""
            <div class='header'>
              <div class='header-badge'>&#127942;</div>
              <div>
                <div class='header-title'>FIFA WORLD CUP 2026</div>
                <div class='header-sub'>%s &mdash; 48 EQUIPOS / 12 GRUPOS</div>
                <div class='header-hosts'>CANADA &bull; MEXICO &bull; USA</div>
              </div>
            </div>
            """,
                escapeHtml(torneo.getNombre().toUpperCase()));
    }

    private String htmlCampeon() {
        Equipo campeon = torneo.getCampeon();

        return String.format("""
            <div class='champion-banner'>
              <div class='trophy-big'>&#127942;</div>
              <div class='ctitle'>CAMPEON DEL MUNDO</div>
              <div class='cname'>%s %s</div>
            </div>
            """,
                bandera(campeon.getNombre()),
                escapeHtml(campeon.getNombre()));
    }

    private String htmlFooter() {
        return String.format("""
            <div class='footer'>
              SISTEMA MUNDIAL TPO &middot; FASE ACTUAL: %s
            </div>
            """,
                torneo.getFaseActual());
    }

    private String htmlScript() {
        return """
            <script>
            function showTab(id, btn) {
              document.querySelectorAll('.tab-content').forEach(el => el.style.display = 'none');
              document.querySelectorAll('.tab').forEach(el => el.classList.remove('active'));
              document.getElementById(id).style.display = 'block';
              btn.classList.add('active');
            }
            </script>
            """;
    }

    private List<Partido> filtrar(List<Partido> lista, FaseTorneo fase) {
        List<Partido> resultado = new ArrayList<>();

        for (Partido partido : lista) {
            if (partido.getFase() == fase) {
                resultado.add(partido);
            }
        }

        return resultado;
    }

    private String bandera(String nombre) {
        String codigo = CODIGOS_PAIS.get(nombre);

        if (codigo == null || codigo.isEmpty()) {
            return "<span class='flag-ph'>?</span>";
        }

        return String.format(
                "<img class='flag' src='https://flagcdn.com/w40/%s.png' "
                        + "srcset='https://flagcdn.com/w80/%s.png 2x' "
                        + "alt='%s' loading='lazy' onerror=\"this.style.display='none'\">",
                codigo,
                codigo,
                escapeHtml(nombre)
        );
    }

    private String colorParaGrupo(String nombreGrupo) {
        switch (nombreGrupo) {
            case "A":
                return "#1B3F8B";
            case "B":
                return "#C62828";
            case "C":
                return "#2E7D32";
            case "D":
                return "#5E35B1";
            case "E":
                return "#F9A825";
            case "F":
                return "#00897B";
            case "G":
                return "#D81B60";
            case "H":
                return "#1565C0";
            case "I":
                return "#EF6C00";
            case "J":
                return "#6A1B9A";
            case "K":
                return "#43A047";
            case "L":
                return "#00ACC1";
            default:
                return "#6B7280";
        }
    }

    private String escapeHtml(String texto) {
        if (texto == null) {
            return "";
        }

        return texto.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }
}