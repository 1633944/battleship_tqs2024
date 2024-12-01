package uab.tqs.practica.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestTablero {

    private Tablero tablero;

    @BeforeEach
    void setUp() {
        tablero = new Tablero();
    }

    @Test
    void testInicializarTablero() {
        String[][] matriz = tablero.getMatriz();
        for (int i = 0; i < tablero.getTamaño(); i++) {
            for (int j = 0; j < tablero.getTamaño(); j++) {
                assertEquals("~", matriz[i][j]);
            }
        }
    }

    @Test
    void testColocarBarcoHorizontalValido() {
        assertTrue(tablero.colocarBarco(0, 0, 4, true));
        String[][] matriz = tablero.getMatriz();
        for (int i = 0; i < 4; i++) {
            assertEquals("B", matriz[0][i]);
        }
    }

    @Test
    void testColocarBarcoVerticalValido() {
        assertTrue(tablero.colocarBarco(0, 0, 4, false));
        String[][] matriz = tablero.getMatriz();
        for (int i = 0; i < 4; i++) {
            assertEquals("B", matriz[i][0]);
        }
    }

    @Test
    void testColocarBarcoHorizontalFueraDeLimite() {
    	assertThrows(IllegalArgumentException.class, () -> tablero.colocarBarco(0, 7, 4, true));
    }

    @Test
    void testColocarBarcoVerticalFueraDeLimite() {
    	assertThrows(IllegalArgumentException.class, () -> tablero.colocarBarco(7, 0, 4, false));
    }

    @Test
    void testColocarBarcoHorizontalSuperpuesto() {
        tablero.colocarBarco(0, 0, 4, true);
        assertThrows(IllegalArgumentException.class, () -> tablero.colocarBarco(0, 2, 4, true));
    }

    @Test
    void testColocarBarcoVerticalSuperpuesto() {
        tablero.colocarBarco(0, 0, 4, false);
        assertThrows(IllegalArgumentException.class, () ->tablero.colocarBarco(2, 0, 4, false));
    }

    @Test
    void testRecibirDisparoAgua() {
        assertEquals("Agua", tablero.recibirDisparo(0, 0));
        assertEquals("O", tablero.getMatriz()[0][0]);
    }

    @Test
    void testRecibirDisparoTocado() {
        tablero.colocarBarco(0, 0, 4, true);
        assertEquals("Tocado", tablero.recibirDisparo(0, 0));
        assertEquals("X", tablero.getMatriz()[0][0]);
    }

    @Test
    void testRecibirDisparoTocadoYHundido() {
        tablero.colocarBarco(0, 0, 2, true);
        assertEquals("Tocado", tablero.recibirDisparo(0, 0));
        assertEquals("Tocado y hundido", tablero.recibirDisparo(0, 1));
    }

    @Test
    void testRecibirDisparoRepetido() {
        tablero.colocarBarco(0, 0, 2, true);
        tablero.recibirDisparo(0, 0);
        assertEquals("Ya has disparado aquí", tablero.recibirDisparo(0, 0));
    }

    @Test
    void testImprimirTablero() {
        tablero.imprimirTablero();
    }

    @Test
    void testRecibirDisparoEnBarcoHorizontal() {
        tablero.colocarBarco(0, 0, 3, true);
        assertEquals("Tocado", tablero.recibirDisparo(0, 0));
        assertEquals("X", tablero.getMatriz()[0][0]);
        assertEquals("Tocado", tablero.recibirDisparo(0, 1));
        assertEquals("X", tablero.getMatriz()[0][1]);
        assertEquals("Tocado y hundido", tablero.recibirDisparo(0, 2));
        assertEquals("X", tablero.getMatriz()[0][2]);
    }

    @Test
    void testRecibirDisparoEnBarcoVertical() {
        tablero.colocarBarco(0, 0, 3, false);
        assertEquals("Tocado", tablero.recibirDisparo(0, 0));
        assertEquals("X", tablero.getMatriz()[0][0]);
        assertEquals("Tocado", tablero.recibirDisparo(1, 0));
        assertEquals("X", tablero.getMatriz()[1][0]);
        assertEquals("Tocado y hundido", tablero.recibirDisparo(2, 0));
        assertEquals("X", tablero.getMatriz()[2][0]);
    }

    @Test
    void testRecibirDisparoSinBarco() {
        assertEquals("Agua", tablero.recibirDisparo(5, 5));
        assertEquals("O", tablero.getMatriz()[5][5]);
    }

    @Test
    void testRecibirDisparoYHundirBarco() {
        tablero.colocarBarco(0, 0, 3, true);
        assertEquals("Tocado", tablero.recibirDisparo(0, 0));
        assertEquals("X", tablero.getMatriz()[0][0]);
        assertEquals("Tocado", tablero.recibirDisparo(0, 1));
        assertEquals("X", tablero.getMatriz()[0][1]);
        assertEquals("Tocado y hundido", tablero.recibirDisparo(0, 2));
        assertEquals("X", tablero.getMatriz()[0][2]);
    }

    @Test
    void testRecibirDisparoYHundirBarcoVertical() {
        tablero.colocarBarco(0, 0, 3, false);
        assertEquals("Tocado", tablero.recibirDisparo(0, 0));
        assertEquals("X", tablero.getMatriz()[0][0]);
        assertEquals("Tocado", tablero.recibirDisparo(1, 0));
        assertEquals("X", tablero.getMatriz()[1][0]);
        assertEquals("Tocado y hundido", tablero.recibirDisparo(2, 0));
        assertEquals("X", tablero.getMatriz()[2][0]);
    }

    @Test
    void testRecibirDisparoEnCasillaVacia() {
        tablero.colocarBarco(0, 0, 3, true);
        String resultado = tablero.recibirDisparo(5, 5);
        assertEquals("Agua", resultado);
        assertEquals("O", tablero.getMatriz()[5][5]);
    }
    @Test
    void testDisparoEnLimiteTablero() {
        assertEquals("Agua", tablero.recibirDisparo(9, 9)); // Última casilla.
        assertEquals("O", tablero.getMatriz()[9][9]);
        tablero.colocarBarco(8, 8, 2, true);
        assertEquals("Tocado", tablero.recibirDisparo(8, 8));
        assertEquals("X", tablero.getMatriz()[8][8]);
    }
    @Test
    void testDisparoSobreBarcoHundido() {
        tablero.colocarBarco(0, 0, 2, true);
        tablero.recibirDisparo(0, 0);
        tablero.recibirDisparo(0, 1); // Hundir barco.
        assertEquals("Ya has disparado aquí", tablero.recibirDisparo(0, 0)); 
        assertEquals("Ya has disparado aquí", tablero.recibirDisparo(0, 1));
    }
    @Test
    void testDisparoEnTableroVacio() {
        for (int i = 0; i < tablero.getTamaño(); i++) {
            for (int j = 0; j < tablero.getTamaño(); j++) {
                assertEquals("Agua", tablero.recibirDisparo(i, j));
                assertEquals("O", tablero.getMatriz()[i][j]);
            }
        }
    }


}
