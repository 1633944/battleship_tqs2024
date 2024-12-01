package uab.tqs.practica.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestJugador {

    private Jugador jugador;
    private Tablero tableroMock;

    @BeforeEach
    void setUp() {
        tableroMock = Mockito.mock(Tablero.class);
        jugador = new Jugador("Jugador1", tableroMock);
    }

    @Test
    void testCreacionJugador() {
        assertNotNull(jugador);
        assertEquals("Jugador1", jugador.getNombre());
        assertEquals(12, jugador.getBarcosRestantes());
        assertNotNull(jugador.getTablero());
        assertNotNull(jugador.getTableroDisparo());
    }

    @Test
    void testColocarBarcoValido() {
        when(tableroMock.colocarBarco(0, 0, 4, true)).thenReturn(true);
        assertTrue(jugador.colocarBarco(0, 0, 4, true));
        verify(tableroMock).colocarBarco(0, 0, 4, true);
    }

    @Test
    void testColocarBarcoInvalido() {
        when(tableroMock.colocarBarco(0, 7, 4, true)).thenReturn(false);
        assertFalse(jugador.colocarBarco(0, 7, 4, true));
        verify(tableroMock).colocarBarco(0, 7, 4, true);
    }

    @Test
    void testRecibirDisparoAgua() {
        when(tableroMock.recibirDisparo(0, 0)).thenReturn("Agua");
        assertEquals("Agua", jugador.recibirDisparo(0, 0));
        assertEquals(12, jugador.getBarcosRestantes());
        verify(tableroMock).recibirDisparo(0, 0);
    }

    @Test
    void testRecibirDisparoTocado() {
        when(tableroMock.recibirDisparo(0, 0)).thenReturn("Tocado");
        assertEquals("Tocado", jugador.recibirDisparo(0, 0));
        assertEquals(11, jugador.getBarcosRestantes());
        verify(tableroMock).recibirDisparo(0, 0);
    }

    @Test
    void testRecibirDisparoYaDisparado() {
        when(tableroMock.recibirDisparo(0, 0)).thenReturn("Ya has disparado aquí");
        assertEquals("Ya has disparado aquí", jugador.recibirDisparo(0, 0));
        verify(tableroMock).recibirDisparo(0, 0);
    }

    @Test
    void testActualizarTableroDisparo() {
        jugador.actualizarTableroDisparo(0, 0, "Tocado");
        assertEquals("X", jugador.getTableroDisparo().getMatriz()[0][0]);

        jugador.actualizarTableroDisparo(1, 1, "Agua");
        assertEquals("O", jugador.getTableroDisparo().getMatriz()[1][1]);
    }

    @Test
    void testObtenerEntradaValida() {
        String input = "invalid\n-1\n10\n5\n"; // Entradas inválidas y luego una válida
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Scanner scanner = new Scanner(System.in);
        int resultado = jugador.obtenerEntradaValida(scanner, "Introduce un número válido: ", 0, 9);

        assertEquals(5, resultado);
    }

    @Test
    void testColocarBarcos() {
        String input = "0\n0\n0\n2\n0\n0\n4\n0\n0\n6\n0\n1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        when(tableroMock.colocarBarco(anyInt(), anyInt(), eq(4), eq(true))).thenReturn(true);
        when(tableroMock.colocarBarco(anyInt(), anyInt(), eq(3), eq(true))).thenReturn(true);
        when(tableroMock.colocarBarco(anyInt(), anyInt(), eq(2), eq(false))).thenReturn(true);

        jugador.colocarBarcos();

        verify(tableroMock).colocarBarco(0, 0, 4, true);
        verify(tableroMock).colocarBarco(2, 0, 3, true);
        verify(tableroMock).colocarBarco(4, 0, 3, true);
        verify(tableroMock).colocarBarco(6, 0, 2, false);
        verify(tableroMock, times(4)).colocarBarco(anyInt(), anyInt(), anyInt(), anyBoolean());
    }
    
    @Test
    void testHaPerdido() {
        when(tableroMock.recibirDisparo(0, 0)).thenReturn("Tocado");
        when(tableroMock.recibirDisparo(0, 1)).thenReturn("Tocado y hundido");

        jugador.colocarBarco(0, 0, 2, true);
        jugador.recibirDisparo(0, 0);
        jugador.recibirDisparo(0, 1);

        assertEquals(10, jugador.getBarcosRestantes());
        assertFalse(jugador.haPerdido());

        // Hundimos los barcos restantes
        when(tableroMock.recibirDisparo(anyInt(), anyInt())).thenReturn("Tocado y hundido");
        for (int i = 0; i < 10; i++) {
            jugador.recibirDisparo(i / 3, i % 3); // Simulamos 10 disparos para hundir barcos
        }

        assertEquals(0, jugador.getBarcosRestantes());
        assertTrue(jugador.haPerdido());
    }

    @Test
    void testColocarBarcoConErrores() {
        // Intento de colocar un barco con entradas inválidas
        when(tableroMock.colocarBarco(anyInt(), anyInt(), anyInt(), anyBoolean())).thenReturn(false);

        jugador.colocarBarco(10, 10, 3, true); // Fuera del tablero
        jugador.colocarBarco(-1, -1, 2, true); // Fuera del rango permitido

        verify(tableroMock, times(2)).colocarBarco(anyInt(), anyInt(), anyInt(), anyBoolean());
    }

    // Additional Tests

    @Test
    void testRecibirDisparoEnCeldaVacia() {
        when(tableroMock.recibirDisparo(1, 1)).thenReturn("Agua");
        assertEquals("Agua", jugador.recibirDisparo(1, 1));
        verify(tableroMock).recibirDisparo(1, 1);
    }

    @Test
    void testRecibirDisparoEnCeldaYaGolpeada() {
        when(tableroMock.recibirDisparo(2, 2)).thenReturn("Tocado").thenReturn("Ya has disparado aquí");
        jugador.colocarBarco(2, 2, 2, true);
        assertEquals("Tocado", jugador.recibirDisparo(2, 2));
        assertEquals("Ya has disparado aquí", jugador.recibirDisparo(2, 2));
        verify(tableroMock, times(2)).recibirDisparo(2, 2);
    }

    @Test
    void testObtenerEntradaValidaConMultiplesEntradasInvalidas() {
        String input = "10\n-1\n5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        int entrada = jugador.obtenerEntradaValida(scanner, "Introduce un número: ", 0, 9);
        assertEquals(5, entrada);
    }
}