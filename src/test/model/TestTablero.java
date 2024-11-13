package test.model;

import main.model.Tablero;
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
                assertEquals("~", matriz[i][j], "El tablero debe inicializarse con agua en todas las posiciones.");
            }
        }
    }

    @Test
    public void testColocarBarcoHorizontalExito() {
        assertTrue(tablero.colocarBarco(0, 0, 3, true), "Debería poder colocar el barco horizontalmente.");
        String[][] matriz = tablero.getMatriz();
        assertEquals("B", matriz[0][0]);
        assertEquals("B", matriz[0][1]);
        assertEquals("B", matriz[0][2]);
    }

    @Test
    public void testColocarBarcoVerticalExito() {
        assertTrue(tablero.colocarBarco(0, 0, 3, false), "Debería poder colocar el barco verticalmente.");
        String[][] matriz = tablero.getMatriz();
        assertEquals("B", matriz[0][0]);
        assertEquals("B", matriz[1][0]);
        assertEquals("B", matriz[2][0]);
    }

    @Test
    public void testDisparoAgua() {
        assertEquals("Agua", tablero.recibirDisparo(5, 5), "Debería devolver 'Agua' si no hay un barco en la posición.");
        assertEquals("O", tablero.getMatriz()[5][5], "Debería marcarse como 'O' en el tablero.");
    }

    @Test
    public void testDisparoTocado() {
        tablero.colocarBarco(0, 0, 2, true);
        assertEquals("Tocado", tablero.recibirDisparo(0, 0), "Debería devolver 'Tocado' si impacta en un barco.");
        assertEquals("X", tablero.getMatriz()[0][0], "Debería marcarse como 'X' en el tablero.");
    }
}
