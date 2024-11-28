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
    public void testCrearBarcoHorizontal() { //cn pe
        assertEquals(4, barcoHorizontal.getLongitud());
        assertTrue(barcoHorizontal.esHorizontal());
    }

    @Test
    public void testCrearBarcoVertical() { //cn pe
        assertEquals(3, barcoVertical.getLongitud());
        assertFalse(barcoVertical.esHorizontal());
    }

    @Test
    public void testImpactoFueraDeRango() { //cn vlvf
        assertThrows(IllegalArgumentException.class, () -> barcoVertical.registrarImpacto(-1));
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
    public void testImpactoTodasMenosUna() {
        barcoHorizontal.registrarImpacto(0);
        barcoHorizontal.registrarImpacto(1);
        barcoHorizontal.registrarImpacto(2);
        assertFalse(barcoHorizontal.estaHundido());
    }

    @Test
    public void testImpactosAlternados() { //cn pe
        barcoHorizontal.registrarImpacto(0);
        barcoHorizontal.registrarImpacto(3);
        assertFalse(barcoHorizontal.estaHundido());
        barcoHorizontal.registrarImpacto(1);
        barcoHorizontal.registrarImpacto(2);
        assertTrue(barcoHorizontal.estaHundido());
    }

    @Test
    public void testBarcoLongitudMinima() { //cn vlvf
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
    public void testImpactosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> barcoHorizontal.registrarImpacto(-1));
        assertThrows(IllegalArgumentException.class, () -> barcoHorizontal.registrarImpacto(4));
    }
    
    @Test
    public void testLongitudInvalida() {
        assertThrows(IllegalArgumentException.class, () -> new Barco(0, true));
        assertThrows(IllegalArgumentException.class, () -> new Barco(-3, false));
    }
    
    @Test
    public void testPairwiseTestingCompleto() { //cn pt
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


}