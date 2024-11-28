package uab.tqs.practica.view;

import java.util.Arrays;

public class ConsolaVista {
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarTablero(String[][] tablero) {
        System.out.println("Tablero:");
        for (String[] fila : tablero) {
            System.out.println(Arrays.toString(fila));
        }
    }
}
