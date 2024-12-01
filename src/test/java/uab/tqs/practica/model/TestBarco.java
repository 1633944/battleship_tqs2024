package uab.tqs.practica.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestBarco {
    private Barco barcoHorizontal;
    private Barco barcoVertical;

    @BeforeEach
    public void setUp() {
        barcoHorizontal = new Barco(4, true);
        barcoVertical = new Barco(3, false);
    }

    @Test
    public void testCrearBarcoHorizontal() {
        assertEquals(4, barcoHorizontal.getLongitud());
        assertTrue(barcoHorizontal.esHorizontal());
    }

    @Test
    public void testCrearBarcoVertical() {
        assertEquals(3, barcoVertical.getLongitud());
        assertFalse(barcoVertical.esHorizontal());
    }

    @Test
    public void testLongitudInvalida() {
        assertThrows(IllegalArgumentException.class, () -> new Barco(0, true));
        assertThrows(IllegalArgumentException.class, () -> new Barco(-1, false));
    }

    @Test
    public void testImpactoFueraDeRangoNegativo() {
        assertThrows(IllegalArgumentException.class, () -> barcoVertical.registrarImpacto(-1));
    }

    @Test
    public void testImpactoFueraDeRangoPositivo() {
        assertThrows(IllegalArgumentException.class, () -> barcoVertical.registrarImpacto(5));
    }

    @Test
    public void testImpactoEnPosicionesValidas() {
        barcoHorizontal.registrarImpacto(0);
        barcoHorizontal.registrarImpacto(2);
        assertFalse(barcoHorizontal.estaHundido());
    }

    @Test
    public void testBarcoNoHundidoInicialmente() {
        assertFalse(barcoHorizontal.estaHundido());
        assertFalse(barcoVertical.estaHundido());
    }

    @Test
    public void testHundirBarcoCompletamente() {
        barcoVertical.registrarImpacto(0);
        barcoVertical.registrarImpacto(1);
        barcoVertical.registrarImpacto(2);
        assertTrue(barcoVertical.estaHundido());
    }

    @Test
    public void testRegistrarImpactoDuplicado() {
        barcoVertical.registrarImpacto(1);
        barcoVertical.registrarImpacto(1);
        barcoVertical.registrarImpacto(0);
        barcoVertical.registrarImpacto(2);
        assertTrue(barcoVertical.estaHundido());
    }

    @Test
    public void testImpactosAlternados() {
        barcoHorizontal.registrarImpacto(0);
        barcoHorizontal.registrarImpacto(3);
        assertFalse(barcoHorizontal.estaHundido());
        barcoHorizontal.registrarImpacto(1);
        barcoHorizontal.registrarImpacto(2);
        assertTrue(barcoHorizontal.estaHundido());
    }

    @Test
    public void testBarcoLongitudMinima() {
        Barco barcoCorto = new Barco(1, true);
        assertEquals(1, barcoCorto.getLongitud());
        barcoCorto.registrarImpacto(0);
        assertTrue(barcoCorto.estaHundido());
    }

    @Test
    public void testImpactosValidos() {
        barcoHorizontal.registrarImpacto(0);
        barcoHorizontal.registrarImpacto(2);
        barcoHorizontal.registrarImpacto(3);
        assertFalse(barcoHorizontal.estaHundido());
    }

    @Test
    public void testPairwiseTestingCompleto() {
        // Caso 1: longitud mínima, horizontal
        Barco barcoCortoHorizontal = new Barco(1, true);
        barcoCortoHorizontal.registrarImpacto(0);
        assertTrue(barcoCortoHorizontal.estaHundido());

        // Caso 2: longitud mínima, vertical
        Barco barcoCortoVertical = new Barco(1, false);
        barcoCortoVertical.registrarImpacto(0);
        assertTrue(barcoCortoVertical.estaHundido());

        // Caso 3: longitud mayor, horizontal
        Barco barcoMedianoHorizontal = new Barco(2, true);
        barcoMedianoHorizontal.registrarImpacto(0);
        assertFalse(barcoMedianoHorizontal.estaHundido());

        // Caso 4: longitud mayor, vertical
        Barco barcoMedianoVertical = new Barco(2, false);
        barcoMedianoVertical.registrarImpacto(1);
        assertFalse(barcoMedianoVertical.estaHundido());
    }

    @Test
    public void testEstadoNoModificadoPorEstaHundido() {
        barcoHorizontal.registrarImpacto(0);
        boolean hundidoAntes = barcoHorizontal.estaHundido();
        boolean hundidoDespues = barcoHorizontal.estaHundido();
        assertEquals(hundidoAntes, hundidoDespues);
    }

    @Test
    public void testEstaHundidoAfterMultipleImpacts() {
        barcoHorizontal.registrarImpacto(0);
        barcoHorizontal.registrarImpacto(1);
        barcoHorizontal.registrarImpacto(2);
        barcoHorizontal.registrarImpacto(3);
        assertTrue(barcoHorizontal.estaHundido());
    }

    @Test
    public void testRegistrarImpactoOnSunkShip() {
        barcoVertical.registrarImpacto(0);
        barcoVertical.registrarImpacto(1);
        barcoVertical.registrarImpacto(2);
        assertTrue(barcoVertical.estaHundido());
        barcoVertical.registrarImpacto(1); // Impact on already sunk ship
        assertTrue(barcoVertical.estaHundido());
    }

    @Test
    public void testRegistrarImpactoBoundaryValues() {
        barcoHorizontal.registrarImpacto(0);
        barcoHorizontal.registrarImpacto(3);
        assertFalse(barcoHorizontal.estaHundido());
    }

    @Test
    public void testBarcoMaxLength() {
        Barco barcoLargo = new Barco(5, true);
        assertEquals(5, barcoLargo.getLongitud());
        barcoLargo.registrarImpacto(0);
        barcoLargo.registrarImpacto(1);
        barcoLargo.registrarImpacto(2);
        barcoLargo.registrarImpacto(3);
        barcoLargo.registrarImpacto(4);
        assertTrue(barcoLargo.estaHundido());
    }
}
