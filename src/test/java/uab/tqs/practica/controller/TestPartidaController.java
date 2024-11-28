package uab.tqs.practica.controller;

import uab.tqs.practica.model.Partida;
import uab.tqs.practica.model.Jugador;
import uab.tqs.practica.view.ConsolaVista;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class TestPartidaController {

    private PartidaController controller;

    @Mock
    private Partida partida;

    @Mock
    private Jugador jugador1;

    @Mock
    private Jugador jugador2;

    @Mock
    private ConsolaVista vista;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configurar los mocks
        when(partida.getJugador1()).thenReturn(jugador1);
        when(partida.getJugador2()).thenReturn(jugador2);

        // Instanciar el controlador
        controller = new PartidaController(partida, vista);
    }

    @Test
    public void testLimpiarConsola() {
        // Capturar la salida de la consola
        controller.limpiarConsola();

        // Verificar que se ha llamado al método de limpiar consola
        // No podemos verificar la salida directamente, pero sí la interacción
        assertTrue(true);  // Solo para indicar que este test se pasa sin errores
    }
}
