package uab.tqs.practica.model;

import uab.tqs.practica.model.Tablero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestTablero {
    private Tablero tablero;

    @BeforeEach
    public void setUp() {
        tablero = new Tablero();
    }

    @Test
    public void testInicializacionTablero() {
        String[][] matriz = tablero.getMatriz();
        assertEquals(10, tablero.getTamaño());
        
        for (int i = 0; i < tablero.getTamaño(); i++) {
            for (int j = 0; j < tablero.getTamaño(); j++) {
                assertEquals("~", matriz[i][j]);
            }
        }
    }

    @Test
    public void testColocarBarcoHorizontalExito() {
        assertTrue(tablero.colocarBarco(0, 0, 3, true));
        String[][] matriz = tablero.getMatriz();
        assertEquals("B", matriz[0][0]);
        assertEquals("B", matriz[0][1]);
        assertEquals("B", matriz[0][2]);
        assertEquals("~", matriz[0][3]); // Verifica que no se coloque más allá de la longitud
    }

    @Test
    public void testColocarBarcoVerticalExito() {
        assertTrue(tablero.colocarBarco(0, 0, 3, false));
        String[][] matriz = tablero.getMatriz();
        assertEquals("B", matriz[0][0]);
        assertEquals("B", matriz[1][0]);
        assertEquals("B", matriz[2][0]);
        assertEquals("~", matriz[3][0]); // Verifica que no se coloque más allá de la longitud
    }

    @Test
    public void testColocarBarcosSuperpuestos() {
        assertTrue(tablero.colocarBarco(0, 0, 3, true));
        assertFalse(tablero.colocarBarco(0, 1, 3, true)); // Intenta colocar sobre un barco existente
    }

    @Test
    public void testColocarBarcoFueraLimites() {
        assertFalse(tablero.colocarBarco(0, 8, 3, true)); // Horizontal fuera de límites
        assertFalse(tablero.colocarBarco(8, 0, 3, false)); // Vertical fuera de límites
    }

    @Test
    public void testDisparoAgua() {
        assertEquals("Agua", tablero.recibirDisparo(0, 0));
        assertEquals("O", tablero.getMatriz()[0][0]);
    }

    @Test
    public void testDisparoTocado() {
        tablero.colocarBarco(0, 0, 3, true);
        assertEquals("Tocado", tablero.recibirDisparo(0, 0));
        assertEquals("X", tablero.getMatriz()[0][0]);
    }

    @Test
    public void testDisparoRepetido() {
        tablero.recibirDisparo(0, 0); // Primer disparo (agua)
        assertEquals("Ya has disparado aquí", tablero.recibirDisparo(0, 0));
    }

    @Test
    public void testDisparoRepetidoEnBarco() {
        tablero.colocarBarco(0, 0, 2, true);
        tablero.recibirDisparo(0, 0); // Primer disparo (tocado)
        assertEquals("Ya has disparado aquí", tablero.recibirDisparo(0, 0));
    }

    @Test
    public void testHundirBarcoHorizontalCompleto() {
        tablero.colocarBarco(0, 0, 2, true);
        assertEquals("Tocado", tablero.recibirDisparo(0, 0));
        assertEquals("Tocado y hundido", tablero.recibirDisparo(0, 1));
    }

    @Test
    public void testHundirBarcoVerticalCompleto() {
        tablero.colocarBarco(0, 0, 2, false);
        assertEquals("Tocado", tablero.recibirDisparo(0, 0));
        assertEquals("Tocado y hundido", tablero.recibirDisparo(1, 0));
    }

    @Test
    public void testBarcoNoHundidoConDisparosNoConsecutivos() {
        tablero.colocarBarco(0, 0, 3, true);
        assertEquals("Tocado", tablero.recibirDisparo(0, 0));
        assertEquals("Tocado", tablero.recibirDisparo(0, 2));
        // El barco no está hundido porque falta la posición del medio
        assertEquals("Tocado y hundido", tablero.recibirDisparo(0, 1));
    }

    @Test
    public void testMultiplesBarcos() {
        tablero.colocarBarco(0, 0, 2, true); // Primer barco horizontal
        tablero.colocarBarco(2, 0, 2, false); // Segundo barco vertical
        
        // Hundir primer barco
        assertEquals("Tocado", tablero.recibirDisparo(0, 0));
        assertEquals("Tocado y hundido", tablero.recibirDisparo(0, 1));
        
        // Hundir segundo barco
        assertEquals("Tocado", tablero.recibirDisparo(2, 0));
        assertEquals("Tocado y hundido", tablero.recibirDisparo(3, 0));
    }

    @Test
    public void testDisparosFallidos() {
        tablero.colocarBarco(0, 0, 2, true);
        assertEquals("Agua", tablero.recibirDisparo(1, 0));
        assertEquals("Agua", tablero.recibirDisparo(0, 2));
        assertEquals("Agua", tablero.recibirDisparo(1, 1));
    }
}