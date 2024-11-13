package main.model;

public class Jugador {
    private String nombre;
    private Tablero tablero;
    private int barcosRestantes;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.tablero = new Tablero();
        this.barcosRestantes = 12;  
    }

    public String getNombre() {
        return nombre;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public int getBarcosRestantes() {
        return barcosRestantes;
    }

    public boolean colocarBarco(int fila, int columna, int longitud, boolean horizontal) {
        boolean colocado = tablero.colocarBarco(fila, columna, longitud, horizontal);
        if (colocado) {
            barcosRestantes--;
        }
        return colocado;
    }

    public String recibirDisparo(int fila, int columna) {
        String resultado = tablero.recibirDisparo(fila, columna);
        if ("Tocado".equals(resultado) || "Tocado y hundido".equals(resultado)) {
            barcosRestantes--;
        }
        return resultado;
    }

    public boolean haPerdido() {
        return barcosRestantes == 0;
    }
}
