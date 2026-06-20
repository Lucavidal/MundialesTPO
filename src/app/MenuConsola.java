package app;

import modelo.Equipo;
import modelo.enums.FaseTorneo;
import patrones.facade.SistemaMundialFacade;
import simulacion.SimuladorMundial;

import java.util.Scanner;

public class MenuConsola {

    private SistemaMundialFacade facade;
    private Scanner scanner;

    public MenuConsola(SistemaMundialFacade facade) {
        this.facade  = facade;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n+====================================================+");
            System.out.println("|      SISTEMA COPA MUNDIAL DE FUTBOL 2026          |");
            System.out.println("|      Formato real: 48 equipos / 12 grupos         |");
            System.out.println("+====================================================+");
            System.out.println("|  --- CONFIGURACION ---                            |");
            System.out.println("|   1. Iniciar torneo                               |");
            System.out.println("|  --- FASE DE GRUPOS (12 grupos) ---               |");
            System.out.println("|   2. Simular fase de grupos                       |");
            System.out.println("|  --- ELIMINATORIAS (32 = 24 + 8 mejores 3ros) --- |");
            System.out.println("|   3. Simular Dieciseisavos  (32 -> 16)            |");
            System.out.println("|   4. Simular Octavos        (16 ->  8)            |");
            System.out.println("|   5. Simular Cuartos        ( 8 ->  4)            |");
            System.out.println("|   6. Simular Semifinal      ( 4 ->  2)            |");
            System.out.println("|   7. Simular Tercer Puesto                        |");
            System.out.println("|   8. Simular Final          ( 2 ->  1)            |");
            System.out.println("|   9. Simular TODAS las eliminatorias              |");
            System.out.println("|  --- REPORTES CONSOLA ---                         |");
            System.out.println("|  10. Ver campeon                                  |");
            System.out.println("|  11. Reporte de partidos                          |");
            System.out.println("|  12. Reporte tabla de posiciones                  |");
            System.out.println("|  13. Reporte campeon                              |");
            System.out.println("|  14. Reporte top goleadores                       |");
            System.out.println("|  15. Reporte top asistidores                      |");
            System.out.println("|  --- REPORTE VISUAL ---                           |");
            System.out.println("|  16. Abrir reporte visual en navegador            |");
            System.out.println("|   0. Salir                                        |");
            System.out.println("+====================================================+");
            System.out.print("Seleccione una opcion (0-19): ");

            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
                procesarOpcion(opcion);
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        System.out.println("Hasta la proxima Copa del Mundo!");
    }

    public void procesarOpcion(int opcion) {
        switch (opcion) {

            // --- Configuracion ---
            case 1:  facade.iniciarTorneo();facade.cargarEquipos();facade.cargarJugadores();facade.generarGrupos();break;

            // --- Grupos ---
            case 2:  facade.simularFaseGrupos(); break;

            // --- Eliminatorias fase x fase ---
            case 3:  simularFase(FaseTorneo.DIECISEISAVOS); break;
            case 4:  simularFase(FaseTorneo.OCTAVOS);       break;
            case 5:  simularFase(FaseTorneo.CUARTOS);       break;
            case 6:  simularFase(FaseTorneo.SEMIFINAL);     break;
            case 7: simularFase(FaseTorneo.TERCER_PUESTO); break;
            case 8: simularFase(FaseTorneo.FINAL);         break;
            case 9: simularTodas();                        break;

            // --- Reportes consola ---
            case 10:
                Equipo c = facade.obtenerCampeon();
                System.out.println(c != null
                        ? "\nCAMPEON: " + c.getNombre() + " (" + c.getPais() + ")"
                        : "El torneo aun no tiene campeon.");
                break;
            case 11: facade.generarReporte("partidos");   break;
            case 12: facade.generarReporte("tabla");      break;
            case 13: facade.generarReporte("campeon");    break;
            case 14: facade.generarReporte("goleador");   break;
            case 15: facade.generarReporte("asistidor");  break;

            // --- Reporte visual ---
            case 16: facade.abrirReporteVisual(); break;

            case 0: break;

            default: System.out.println("Opcion no valida. Elija entre 0 y 19.");
        }
    }

    private void simularFase(FaseTorneo fase) {
        SimuladorMundial sim = facade.getSimuladorMundial();
        if (sim == null) {
            System.out.println("Primero genere y simule los grupos (opciones 4 y 5).");
            return;
        }
        sim.simularFaseEspecifica(fase);
    }

    private void simularTodas() {
        SimuladorMundial sim = facade.getSimuladorMundial();
        if (sim == null) {
            System.out.println("Primero genere y simule los grupos (opciones 4 y 5).");
            return;
        }
        sim.simularEliminatorias();
    }
}