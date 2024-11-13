package test.model;

import main.model.Jugador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestJugador {
    
    private Jugador jugador;
    
    @BeforeEach
    void setUp() {
        jugador = new Jugador("Jugador1");
    }

    @Test
    void testCreacionJugador() {
        assertNotNull(jugador);
    }

    @Test
    void testColocarBarcoValido() {
        assertTrue(jugador.colocarBarco(0, 0, 4, true));
    }

    @Test
    void testColocarBarcoInvalido() {
        assertFalse(jugador.colocarBarco(0, 8, 4, true));
    }

    @Test
    void testRecibirDisparoAgua() {
        assertEquals("Agua", jugador.recibirDisparo(0, 0));
    }

    @Test
    void testRecibirDisparoTocado() {
        jugador.colocarBarco(0, 0, 4, true);
        assertEquals("Tocado", jugador.recibirDisparo(0, 0));
    }

    @Test
    void testRecibirDisparoRepetido() {
        jugador.colocarBarco(0, 0, 4, true);
        jugador.recibirDisparo(0, 0);
        assertEquals("Ya has disparado aquí", jugador.recibirDisparo(0, 0));
    }

    @Test
    void testHaPerdido() {
        jugador.colocarBarco(0, 0, 2, true);
        jugador.recibirDisparo(0, 0);
        jugador.recibirDisparo(0, 1);
        assertTrue(jugador.haPerdido());
    }
}
