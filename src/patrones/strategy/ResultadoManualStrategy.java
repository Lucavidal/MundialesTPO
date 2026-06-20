package patrones.strategy;



import modelo.Partido;
import modelo.Resultado;
import modelo.enums.FaseTorneo;

import java.util.Scanner;

/**
 * Implementación de EstrategiaResultado que solicita el resultado por consola.
 * Útil para simulaciones controladas o demostraciones.
 */
public class ResultadoManualStrategy implements IEstrategiaResultado {

    private Scanner scanner;

    public ResultadoManualStrategy() {
        this.scanner = new Scanner(System.in);
    }

    public ResultadoManualStrategy(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Resultado simularResultado(Partido p) {
        System.out.println("\n--- Ingrese resultado para: "
                + p.getEquipoLocal().getNombre()
                + " vs "
                + p.getEquipoVisitante().getNombre() + " ---");

        System.out.print("Goles " + p.getEquipoLocal().getNombre() + ": ");
        int golesLocal = leerEnteroPositivo();

        System.out.print("Goles " + p.getEquipoVisitante().getNombre() + ": ");
        int golesVisitante = leerEnteroPositivo();

        boolean esFaseEliminatoria = p.getFase() != FaseTorneo.GRUPOS;

        // En eliminatorias con empate se piden penales
        if (esFaseEliminatoria && golesLocal == golesVisitante) {
            System.out.println("Empate en tiempo reglamentario. Se define por penales.");
            int penalesLocal, penalesVisitante;
            do {
                System.out.print("Penales " + p.getEquipoLocal().getNombre() + ": ");
                penalesLocal = leerEnteroPositivo();
                System.out.print("Penales " + p.getEquipoVisitante().getNombre() + ": ");
                penalesVisitante = leerEnteroPositivo();
                if (penalesLocal == penalesVisitante) {
                    System.out.println("Los penales no pueden ser iguales. Reingrese.");
                }
            } while (penalesLocal == penalesVisitante);
            return new Resultado(golesLocal, golesVisitante, penalesLocal, penalesVisitante);
        }

        return new Resultado(golesLocal, golesVisitante);
    }

    /** Lee un entero no negativo desde consola, repite si el input es inválido */
    private int leerEnteroPositivo() {
        while (true) {
            try {
                int valor = Integer.parseInt(scanner.nextLine().trim());
                if (valor >= 0) return valor;
                System.out.print("Ingrese un número mayor o igual a 0: ");
            } catch (NumberFormatException e) {
                System.out.print("Valor inválido. Ingrese un número entero: ");
            }
        }
    }
}