package uab.tqs.practica.controller;

import uab.tqs.practica.model.Partida;
import uab.tqs.practica.model.Jugador;
import uab.tqs.practica.view.ConsolaVista;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

        when(partida.getJugador1()).thenReturn(jugador1);
        when(partida.getJugador2()).thenReturn(jugador2);

        controller = new PartidaController(partida, vista);
    }

    @Test
    public void testIniciarPartida() {
        doNothing().when(jugador1).colocarBarcos();
        doNothing().when(jugador2).colocarBarcos();

        controller.iniciarPartida();

        verify(jugador1, times(1)).colocarBarcos();
        verify(jugador2, times(1)).colocarBarcos();

        verify(vista, times(2)).mostrarTablero(any());
    }
}
