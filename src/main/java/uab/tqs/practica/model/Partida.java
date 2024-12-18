package uab.tqs.practica.model;

public class Partida {
    private final Jugador jugador1;
    private final Jugador jugador2;

    public Partida() {
        this.jugador1 = new Jugador("Jugador 1", new Tablero());
        this.jugador2 = new Jugador("Jugador 2", new Tablero());
    }

    public Jugador getJugador1() {
        return jugador1;
    }

    public Jugador getJugador2() {
        return jugador2;
    }
}