package test.model;

import main.model.Barco;
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
    public void testImpactoFueraDeRangoNegativo() {
        barcoVertical.registrarImpacto(-1);
        assertFalse(barcoVertical.estaHundido());
    }

    @Test
    public void testImpactoFueraDeRangoPositivo() {
        barcoVertical.registrarImpacto(5);
        assertFalse(barcoVertical.estaHundido());
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
}
