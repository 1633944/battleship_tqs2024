package main.model;

public class Tablero {
    private final String[][] matriz;
    private final int tamaño;

    public Tablero() {
        this.tamaño = 10;
        this.matriz = new String[tamaño][tamaño];
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
        return false;
    }

    public String recibirDisparo(int fila, int columna) {
        if (matriz[fila][columna].equals("~")) {
            matriz[fila][columna] = "O";
            return "Agua";
        }
        return "Ya has disparado aquí";
    }

    public String[][] getMatriz() {
        return matriz;
    }

    public int getTamaño() {
        return tamaño;
    }
}
