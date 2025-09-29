import java.util.ArrayList;

public class Vehiculo {

    public enum Orientacion { HORIZONTAL, VERTICAL }

    private String id;               // Identificador (ej: "A", "B" ...)
    private int fila;                // Fila de la casilla inicial (0-5)
    private int columna;             // Columna de la casilla inicial (0-5)
    private int longitud;            // 2 = coche, 3 = camión
    private Orientacion orientacion; // Dirección del vehículo
    private ArrayList<int[]> posiciones = new ArrayList<>();

    public Vehiculo(String id, int fila, int columna) {
        this.id = id;
        this.fila = fila;
        this.columna = columna;
        this.longitud = 1; // Longitud minima cuando se construye (1 casilla)
        
        // añadimos la posición inicial
        agregarPosicion(fila, columna);
    }

    // Metodo para añadir una posicion cuando construimos el vehiculo
    public void agregarPosicion(int fila, int columna) {
        posiciones.add(new int[]{fila, columna});
    }
    
    public ArrayList<int[]> getPosiciones(){
        return this.posiciones;
    }

    public String getPoscionesString(){

        ArrayList<int[]> posiciones = getPosiciones();
        String resultado = "";

        for (int[] is : posiciones) {
            resultado = resultado + "(" + is[0] +","+ is[1] +")";
        }
        
        return resultado;

    }

    // --- Getters ---
    public String getId() {
        return id;
    }

    public int getFila() {
        return fila;
    }

    public void incrementarLongitud(){ // Incrementa en 1 la longitud
        this.longitud++;
    } 
  

    public int getColumna() {
        return columna;
    }

    public int getLongitud() {
        return longitud;
    }

    public Orientacion getOrientacion(){
        return this.orientacion;
    }

    public boolean esHorizontal() {
        return this.orientacion == Orientacion.HORIZONTAL;
    }

    public void setHorizontal( ) {
        this.orientacion = Orientacion.HORIZONTAL;
    }

     public void setVertical( ) {
        this.orientacion = Orientacion.VERTICAL;
    }


    public boolean tieneIdValido(){

        boolean esValido = false;
        String c_validos = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        // Si es "o" o está dentro del listado de caracteres válidos, lo marcamos como válido
        if (this.getId().equals("o")) {
            esValido = true;
        } else {

            if (c_validos.contains(this.getId())) {
                esValido = true;
            }
        }

        return esValido;

    }

    @Override
    public String toString() {
        return String.format("Vehiculo{id='%s', fila=%d, columna=%d, longitud=%d, orientacion=%s}",
                id, fila, columna, longitud, orientacion);
    }
}
