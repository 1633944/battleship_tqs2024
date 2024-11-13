package main.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Jugador {
    private final String nombre;
    private final Tablero tablero;
    private final List<Barco> barcos;
    private int barcosRestantes;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.tablero = new Tablero();
        this.barcos = new ArrayList<>();
        this.barcosRestantes = 12; // Total de casillas ocupadas por barcos (4 + 3 + 3 + 2)
    }

    public void colocarBarcos() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println(nombre + ", coloca un barco de longitud 4:");
        colocarBarco(scanner, 4);
        
        for (int i = 0; i < 2; i++) {
            System.out.println(nombre + ", coloca un barco de longitud 3:");
            colocarBarco(scanner, 3);
        }
        
        System.out.println(nombre + ", coloca un barco de longitud 2:");
        colocarBarco(scanner, 2);
    }

    private void colocarBarco(Scanner scanner, int longitud) {
        boolean colocado = false;
        while (!colocado) {
            mostrarTablero();
            
            int fila = obtenerEntradaValida(scanner, "Fila (0-9): ", 0, 9);
            int columna = obtenerEntradaValida(scanner, "Columna (0-9): ", 0, 9);
            int orientacion = obtenerEntradaValida(scanner, "Orientación (0 para horizontal, 1 para vertical): ", 0, 1);
            boolean horizontal = (orientacion == 0);

            colocado = colocarBarco(fila, columna, longitud, horizontal);
            
            if (!colocado) {
                System.out.println("No se pudo colocar el barco. Intenta de nuevo.");
            }
        }
    }

    private int obtenerEntradaValida(Scanner scanner, String mensaje, int min, int max) {
        int entrada;
        while (true) {
            System.out.print(mensaje);
            if (scanner.hasNextInt()) {
                entrada = scanner.nextInt();
                if (entrada >= min && entrada <= max) {
                    break;
                } else {
                    System.out.println("Por favor, introduce un número entre " + min + " y " + max + ".");
                }
            } else {
                System.out.println("Entrada no válida. Por favor, introduce un número.");
                scanner.next();
            }
        }
        return entrada;
    }

    public boolean colocarBarco(int fila, int columna, int longitud, boolean horizontal) {
        return tablero.colocarBarco(fila, columna, longitud, horizontal);
    }

    public String recibirDisparo(int fila, int columna) {
        String resultado = tablero.recibirDisparo(fila, columna);
        if (resultado.equals("Tocado") || resultado.equals("Tocado y hundido")) {
            barcosRestantes--;
        }
        return resultado;
    }

    public boolean haPerdido() {
        return barcosRestantes <= 0;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public int getBarcosRestantes() {
        return barcosRestantes;
    }

    public String getNombre() {
        return nombre;
    }

    public void mostrarTablero() {
        System.out.println("Tablero de " + nombre + ":");
        tablero.imprimirTablero();
    }
}