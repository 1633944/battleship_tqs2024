package uab.tqs.practica.controller;

import uab.tqs.practica.model.Partida;
import uab.tqs.practica.model.Jugador;
import uab.tqs.practica.view.ConsolaVista;

import java.util.Scanner;

public class PartidaController {
    private final Partida partida;
    private final ConsolaVista vista;
    private final Scanner scanner;

    public PartidaController(Partida partida, ConsolaVista vista) {
        this.partida = partida;
        this.vista = vista;
        this.scanner = new Scanner(System.in);
    }

    public void iniciarPartida() {
        configurarBarcos(partida.getJugador1());
        configurarBarcos(partida.getJugador2());
        iniciarJuego();
    }

    private void configurarBarcos(Jugador jugador) {
        jugador.colocarBarcos(); // Configuración manual de barcos
        vista.mostrarTablero(jugador.getTablero().getMatriz());
    }

    private void iniciarJuego() {
        boolean turnoJugador1 = true;

        while (true) {
            Jugador jugadorAtacante = turnoJugador1 ? partida.getJugador1() : partida.getJugador2();
            Jugador jugadorDefensor = turnoJugador1 ? partida.getJugador2() : partida.getJugador1();

            vista.mostrarMensaje("Turno del Jugador " + (turnoJugador1 ? 1 : 2));
            boolean acertado = disparar(jugadorAtacante, jugadorDefensor);

            if (jugadorDefensor.haPerdido()) {
                vista.mostrarMensaje("¡GANADOR JUGADOR " + (turnoJugador1 ? 1 : 2) + "!");
                break; // Fin del juego
            }

            if (!acertado) {
                turnoJugador1 = !turnoJugador1; // Cambiar turno solo si no ha acertado
            }
        }
    }

    private boolean disparar(Jugador jugadorAtacante, Jugador jugadorDefensor) {
        vista.mostrarMensaje("Fila del disparo:");
        int fila = scanner.nextInt();
        vista.mostrarMensaje("Columna del disparo:");
        int columna = scanner.nextInt();

        String resultado = jugadorDefensor.recibirDisparo(fila, columna);
        vista.mostrarMensaje("Resultado del disparo: " + resultado);
        vista.mostrarTablero(jugadorDefensor.getTablero().getMatriz());

        return resultado.equals("Tocado") || resultado.equals("Tocado y hundido");
    }
}
