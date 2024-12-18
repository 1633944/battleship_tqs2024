package uab.tqs.practica.model;

public class Barco {
    private final int longitud;
    private final boolean horizontal;
    private final boolean[] impactos;

    public Barco(int longitud, boolean horizontal) {
        if (longitud <= 0) {
            throw new IllegalArgumentException("La longitud del barco debe ser mayor que 0.");
        }
        this.horizontal = horizontal;
        this.longitud = longitud;
        this.impactos = new boolean[longitud];
    }

    public int getLongitud() {
        return longitud;
    }

    public boolean esHorizontal() {
        return horizontal;
    }

    public void registrarImpacto(int posicion) {  
        if (posicion < 0 || posicion >= impactos.length) {  //mirar si la posicion entrada, esta dentro del tablero.
            throw new IllegalArgumentException("Posición fuera de rango.");
        }
        impactos[posicion] = true;
    }

    public boolean estaHundido() {  //mirar si el barco esta hundido
        for (boolean impacto : impactos) {
            if (!impacto) {
                return false;
            }
        }
        return true;
    }
}
