package test.model;

import main.model.Partida;
import main.model.Jugador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPartida {
    private Partida partida;

    @BeforeEach
    public void setUp() {
        partida = new Partida();
    }

    @Test
    public void testInicializarJugadores() {
        Jugador jugador1 = partida.getJugador1();
        Jugador jugador2 = partida.getJugador2();

        assertNotNull(jugador1, "El jugador 1 no debe ser nulo");
        assertNotNull(jugador2, "El jugador 2 no debe ser nulo");
        assertEquals("Jugador 1", jugador1.getNombre(), "El nombre del jugador 1 es incorrecto");
        assertEquals("Jugador 2", jugador2.getNombre(), "El nombre del jugador 2 es incorrecto");
    }

    @Test
    public void testJugadoresDistintos() {
        Jugador jugador1 = partida.getJugador1();
        Jugador jugador2 = partida.getJugador2();

        assertNotSame(jugador1, jugador2, "Los jugadores deben ser instancias distintas");
    }

    @Test
    public void testTablerosInicializados() {
        Jugador jugador1 = partida.getJugador1();
        Jugador jugador2 = partida.getJugador2();

        assertNotNull(jugador1.getTablero(), "El tablero del jugador 1 no debe ser nulo");
        assertNotNull(jugador2.getTablero(), "El tablero del jugador 2 no debe ser nulo");
    }
}
