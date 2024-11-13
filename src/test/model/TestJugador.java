package test.model;

import main.model.Barco;
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
        assertEquals("Jugador1", jugador.getNombre());
        assertEquals(12, jugador.getBarcosRestantes());
        assertNotNull(jugador.getTablero());
    }

    @Test
    void testColocarBarcoValido() {
        assertTrue(jugador.colocarBarco(0, 0, 4, true));
        String[][] matriz = jugador.getTablero().getMatriz();
        for (int i = 0; i < 4; i++) {
            assertEquals("B", matriz[0][i]);
        }
    }

    @Test
    void testColocarBarcoFueraTablero() {
        assertFalse(jugador.colocarBarco(0, 7, 4, true));
        assertFalse(jugador.colocarBarco(7, 0, 4, false));
    }

    @Test
    void testColocarBarcosSuperpuestos() {
        assertTrue(jugador.colocarBarco(0, 0, 3, true));
        assertFalse(jugador.colocarBarco(0, 0, 2, true));
    }

    @Test
    void testRecibirDisparoAgua() {
        assertEquals("Agua", jugador.recibirDisparo(0, 0));
        assertEquals(12, jugador.getBarcosRestantes());
    }

    @Test
    void testRecibirDisparoTocado() {
        jugador.colocarBarco(0, 0, 4, true);
        assertEquals("Tocado", jugador.recibirDisparo(0, 0));
        assertEquals(11, jugador.getBarcosRestantes());
    }

    @Test
    void testRecibirDisparoRepetido() {
        jugador.colocarBarco(0, 0, 2, true);
        assertEquals("Tocado", jugador.recibirDisparo(0, 0));
        assertEquals("Ya has disparado aquí", jugador.recibirDisparo(0, 0));
        assertEquals(11, jugador.getBarcosRestantes());
    }

    @Test
    void testRecibirDisparoTocadoYHundido() {
        jugador.colocarBarco(0, 0, 2, true);
        assertEquals("Tocado", jugador.recibirDisparo(0, 0));
        assertEquals("Tocado y hundido", jugador.recibirDisparo(0, 1));
        assertEquals(10, jugador.getBarcosRestantes());
    }

    @Test
    void testHaPerdido() {
        jugador.colocarBarco(0, 0, 2, true);
        assertFalse(jugador.haPerdido());

        jugador.recibirDisparo(0, 0);
        jugador.recibirDisparo(0, 1);

        jugador.colocarBarco(2, 0, 3, true);
        for (int i = 0; i < 3; i++) {
            jugador.recibirDisparo(2, i);
        }

        jugador.colocarBarco(4, 0, 3, true);
        for (int i = 0; i < 3; i++) {
            jugador.recibirDisparo(4, i);
        }

        jugador.colocarBarco(6, 0, 4, true);
        for (int i = 0; i < 4; i++) {
            jugador.recibirDisparo(6, i);
        }

        assertTrue(jugador.haPerdido());
    }

    @Test
    void testColocarBarcosCompletos() {
        assertTrue(jugador.colocarBarco(0, 0, 4, true));
        assertTrue(jugador.colocarBarco(2, 0, 3, true));
        assertTrue(jugador.colocarBarco(4, 0, 3, true));
        assertTrue(jugador.colocarBarco(6, 0, 2, true));
    }

    @Test
    void testRecibirDisparoVertical() {
        jugador.colocarBarco(0, 0, 4, false);
        assertEquals("Tocado", jugador.recibirDisparo(0, 0));
        assertEquals("Tocado", jugador.recibirDisparo(1, 0));
    }

    @Test
    void testRecibirDisparoHorizontal() {
        jugador.colocarBarco(0, 0, 4, true);
        assertEquals("Tocado", jugador.recibirDisparo(0, 0));
        assertEquals("Tocado", jugador.recibirDisparo(0, 1));
    }

    @Test
    void testRecibirDisparoYVerificarBarcosRestantes() {
        jugador.colocarBarco(0, 0, 4, true);
        assertEquals(12, jugador.getBarcosRestantes());
        jugador.recibirDisparo(0, 0);
        assertEquals(11, jugador.getBarcosRestantes());
        jugador.recibirDisparo(0, 1);
        assertEquals(10, jugador.getBarcosRestantes());
    }
}
