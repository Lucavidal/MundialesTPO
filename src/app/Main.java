package app;

import app.MenuConsola;
import patrones.facade.SistemaMundialFacade;


public class Main {

    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("  BIENVENIDO AL SISTEMA MUNDIAL DE FÚTBOL");
        System.out.println("===========================================");


        SistemaMundialFacade facade = new SistemaMundialFacade();


        MenuConsola menu = new MenuConsola(facade);
        menu.mostrarMenu();
    }
}