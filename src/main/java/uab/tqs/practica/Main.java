package uab.tqs.practica;

import uab.tqs.practica.controller.PartidaController;
import uab.tqs.practica.model.Partida;
import uab.tqs.practica.view.ConsolaVista;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Partida partida = new Partida();
        ConsolaVista vista = new ConsolaVista();
        Scanner scanner = new Scanner(System.in); // Inicializar el Scanner
        PartidaController controlador = new PartidaController(partida, vista, scanner);
        controlador.iniciarPartida();
    }
}