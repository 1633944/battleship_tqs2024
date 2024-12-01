package uab.tqs.practica.model;

import java.util.ArrayList;
import java.util.List;

public class Tablero {
    private final String[][] matriz;
    private final int tamaño;
    private final List<Barco> barcos;
    private final List<int[]> posicionesBarcos;

    public Tablero() {
        this.tamaño = 10;  //matriz 10x10
        this.matriz = new String[tamaño][tamaño];
        this.barcos = new ArrayList<>();
        this.posicionesBarcos = new ArrayList<>();
        inicializarTablero();
    }

    private void inicializarTablero() {
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                matriz[i][j] = "~";
            }
        }
    }

    public boolean colocarBarco(int fila, int columna, int longitud, boolean horizontal) {
        if (fila < 0 || fila >= tamaño || columna < 0 || columna >= tamaño) {  
            throw new IllegalArgumentException("Las coordenadas del barco están fuera del tablero.");
        }

        if (horizontal) {
            if (columna + longitud > tamaño) {
                throw new IllegalArgumentException("El barco se sale del tablero en la dirección horizontal.");
            }
            for (int i = 0; i < longitud; i++) {
                if (!matriz[fila][columna + i].equals("~")) {
                    throw new IllegalArgumentException("Hay un barco en la posición de colocación.");
                }
            }
            for (int i = 0; i < longitud; i++) {
                matriz[fila][columna + i] = "B";
            }
        } else {
            if (fila + longitud > tamaño) {
                throw new IllegalArgumentException("El barco se sale del tablero en la dirección vertical.");
            }
            for (int i = 0; i < longitud; i++) {
                if (!matriz[fila + i][columna].equals("~")) {
                    throw new IllegalArgumentException("Hay un barco en la posición de colocación.");
                }
            }
            for (int i = 0; i < longitud; i++) {
                matriz[fila + i][columna] = "B";
            }
        }

        Barco barco = new Barco(longitud, horizontal);
        barcos.add(barco);
        posicionesBarcos.add(new int[]{fila, columna});
        return true;
    }

    public String recibirDisparo(int fila, int columna) {
        if (fila < 0 || fila >= tamaño || columna < 0 || columna >= tamaño) {
            throw new IllegalArgumentException("Las coordenadas del disparo están fuera del tablero.");
        }

        if (matriz[fila][columna].equals("B")) {
            matriz[fila][columna] = "X";
            
            for (int i = 0; i < barcos.size(); i++) {
                Barco barco = barcos.get(i);
                int[] pos = posicionesBarcos.get(i);
                
                if (barco.esHorizontal()) {
                    if (fila == pos[0] && columna >= pos[1] && columna < pos[1] + barco.getLongitud()) {
                        barco.registrarImpacto(columna - pos[1]);
                        return barco.estaHundido() ? "Tocado y hundido" : "Tocado";
                    }
                } else {
                    if (columna == pos[1] && fila >= pos[0] && fila < pos[0] + barco.getLongitud()) {
                        barco.registrarImpacto(fila - pos[0]);
                        return barco.estaHundido() ? "Tocado y hundido" : "Tocado";
                    }
                }
            }
            return "Tocado"; 
        } else if (matriz[fila][columna].equals("~")) {
            matriz[fila][columna] = "O";
            return "Agua";
        } else {
            return "Ya has disparado aquí";
        }
    }

    public String[][] getMatriz() {
        return matriz;
    }

    public int getTamaño() {
        return tamaño;
    }

    public void imprimirTablero() {
        System.out.println("Tablero:");
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }
}
