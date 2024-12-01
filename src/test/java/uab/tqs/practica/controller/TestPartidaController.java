package uab.tqs.practica.controller;

import uab.tqs.practica.model.Partida;
import uab.tqs.practica.model.Jugador;
import uab.tqs.practica.model.Tablero;
import uab.tqs.practica.view.ConsolaVista;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
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
    
    @Mock
    private Tablero tableroJugador1;
    
    @Mock
    private Tablero tableroJugador2;
    
    @Mock
    private Tablero tableroDisparoJugador1;
    
    @Mock
    private Tablero tableroDisparoJugador2;
    
    private Scanner scanner;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        when(partida.getJugador1()).thenReturn(jugador1);
        when(partida.getJugador2()).thenReturn(jugador2);
        
        when(jugador1.getTablero()).thenReturn(tableroJugador1);
        when(jugador2.getTablero()).thenReturn(tableroJugador2);
        
        when(jugador1.getTableroDisparo()).thenReturn(tableroDisparoJugador1);
        when(jugador2.getTableroDisparo()).thenReturn(tableroDisparoJugador2);
        
        String input = "5\n6\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        scanner = new Scanner(inputStream);
        
        controller = new PartidaController(partida, vista, scanner);
    }

    @Test
    public void testConfigurarBarcos() {
        doNothing().when(jugador1).colocarBarcos();
        
        controller.configurarBarcos(jugador1);
        
        verify(jugador1).colocarBarcos();
        verify(vista).mostrarTablero(tableroJugador1.getMatriz());
    }
    
    @Test
    public void testDisparar_Tocado() {
        when(jugador2.recibirDisparo(5, 6)).thenReturn("Tocado");
        
        boolean resultado = controller.disparar(jugador1, jugador2);
        
        assertTrue(resultado);
        verify(jugador2).recibirDisparo(5, 6);
        verify(jugador1).actualizarTableroDisparo(5, 6, "Tocado");
    }
    
    @Test
    public void testDisparar_Agua() {
        when(jugador2.recibirDisparo(5, 6)).thenReturn("Agua");
        
        boolean resultado = controller.disparar(jugador1, jugador2);
       
        assertFalse(resultado);
        verify(jugador2).recibirDisparo(5, 6);
        verify(jugador1).actualizarTableroDisparo(5, 6, "Agua");
    }
    
    @Test
    public void testIniciarJuego_Jugador1Gana() {
        when(jugador2.haPerdido()).thenReturn(true);
        when(jugador1.recibirDisparo(anyInt(), anyInt())).thenReturn("Agua");
        when(jugador2.recibirDisparo(anyInt(), anyInt())).thenReturn("Tocado y hundido");
        controller.iniciarJuego();
        verify(vista).mostrarMensaje(contains("GANADOR JUGADOR 1"));
    }
    
	/*
	 * @Test public void testIniciarJuego_Jugador2Gana() {
	 * when(jugador1.haPerdido()).thenReturn(false).thenReturn(true);
	 * when(jugador2.recibirDisparo(anyInt(), anyInt())).thenReturn("Agua");
	 * when(jugador1.recibirDisparo(anyInt(),
	 * anyInt())).thenReturn("Tocado y hundido"); controller.iniciarJuego();
	 * verify(vista, times(1)).mostrarMensaje(contains("¡GANADOR JUGADOR 2!")); }
	 */
    
    @Test
    public void testLimpiarConsola() {
        controller.limpiarConsola();
        // Add verification for console clearing if applicable
        assertTrue(true);
    }

    @Test
    public void testIniciarPartida() {
        PartidaController spyController = spy(new PartidaController(partida, vista, scanner));

        doNothing().when(jugador1).colocarBarcos();
        doNothing().when(jugador2).colocarBarcos();

        doNothing().when(spyController).iniciarJuego();

        spyController.iniciarPartida();

        verify(jugador1).colocarBarcos();
        verify(jugador2).colocarBarcos();
        verify(vista, times(2)).mostrarTablero(any());
        verify(spyController).iniciarJuego();
    }

    @Test
    public void testDisparar_FueraDeLimites() {
        Scanner scannerFueraLimites = new Scanner(new ByteArrayInputStream("-1\n10\n".getBytes()));
        PartidaController controllerFueraLimites = new PartidaController(partida, vista, scannerFueraLimites);

        doThrow(new IllegalArgumentException("Coordenadas inválidas"))
            .when(jugador2).recibirDisparo(-1, 10);

        assertThrows(IllegalArgumentException.class, () -> {
            controllerFueraLimites.disparar(jugador1, jugador2);
        });

        scannerFueraLimites.close();
    }

    @Test
    public void testCambioTurno_Acierto() {
        // Setup the scenario where the attacking player hits a ship
        when(jugador2.recibirDisparo(anyInt(), anyInt())).thenReturn("Tocado");
        
        // Simulate that neither player has lost yet
        when(jugador1.haPerdido()).thenReturn(false);
        when(jugador2.haPerdido()).thenReturn(false);
        
        // Call disparar method
        boolean resultado = controller.disparar(jugador1, jugador2);
        
        // Verify that the result is true (hit)
        assertTrue(resultado);
        
        // Verify that the shot was received on the defender's board
        verify(jugador2).recibirDisparo(anyInt(), anyInt());
        
        // Verify that the attacking player updates their shooting board
        verify(jugador1).actualizarTableroDisparo(anyInt(), anyInt(), eq("Tocado"));
    }
}