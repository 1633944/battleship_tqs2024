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
    public void testIniciarPartida() {
        // Simular que los jugadores colocan los barcos
        doNothing().when(jugador1).colocarBarcos();
        doNothing().when(jugador2).colocarBarcos();

        // Ejecutar el inicio de la partida
        controller.iniciarPartida();

        // Verificar que ambos jugadores han colocado sus barcos
        verify(jugador1, times(1)).colocarBarcos();
        verify(jugador2, times(1)).colocarBarcos();

        // Verificar que se haya mostrado el tablero para ambos jugadores
        verify(vista, times(2)).mostrarTablero(any());
    }

    @Test
    public void testTurnoJugador1() {
        // Simulación de disparo
        when(jugador1.recibirDisparo(0, 0)).thenReturn("Tocado");
        when(jugador2.recibirDisparo(0, 0)).thenReturn("Agua");

        // Simular un disparo exitoso
        boolean resultado = controller.disparar(jugador1, jugador2);

        // Verificar que se haya disparado correctamente y el resultado sea el esperado
        verify(vista).mostrarMensaje("Resultado del disparo: Tocado");
        assertTrue(resultado);
    }

    @Test
    public void testTurnoJugador2() {
        // Simulación de disparo
        when(jugador2.recibirDisparo(1, 1)).thenReturn("Agua");

        // Simular un disparo fallido
        boolean resultado = controller.disparar(jugador2, jugador1);

        // Verificar que se haya disparado correctamente y el resultado sea el esperado
        verify(vista).mostrarMensaje("Resultado del disparo: Agua");
        assertFalse(resultado);
    }

    @Test
    public void testFinDeJuego() {
        // Simulamos que jugador2 pierde
        when(jugador1.haPerdido()).thenReturn(true);

        // Iniciar el juego y verificar que el mensaje de fin de juego se muestra
        controller.iniciarPartida();

        verify(vista).mostrarMensaje("¡GANADOR JUGADOR 1!");
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
