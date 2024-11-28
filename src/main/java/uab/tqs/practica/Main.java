package uab.tqs.practica;

import uab.tqs.practica.controller.PartidaController;
import uab.tqs.practica.model.Partida;
import uab.tqs.practica.view.ConsolaVista;

public class Main {
    public static void main(String[] args) {
        Partida partida = new Partida();
        ConsolaVista vista = new ConsolaVista();
        PartidaController controlador = new PartidaController(partida, vista);
        controlador.iniciarPartida();
    }
}
