import java.util.ArrayList;

public class Tablero {
    private final int SIZE = 6;
    private final ArrayList<Vehiculo> vehiculos = new ArrayList<>();
    private final String[][] tablero;

    public Tablero() {
        tablero = new String[SIZE][SIZE];
        inicializar();
    }

    // Metodo para llenar de huecos vacios "o" el tablero
    private void inicializar() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                tablero[i][j] = "o";
            }
        }
    }

    
    public void agregarVehiculo(Vehiculo v) {
        vehiculos.add(v); // Añadimos a la lista
        colocarVehiculo(v); // Colocamos en la matriz "tablero"
    }

    private void colocarVehiculo(Vehiculo v) {
        int fila = v.getFila();
        int col = v.getColumna();

        for (int k = 0; k < v.getLongitud(); k++) {
            if (v.esHorizontal()) {
                if (col + k < SIZE) {
                    tablero[fila][col + k] = v.getId();
                }
            } else { // VERTICAL
                if (fila + k < SIZE) { // Para no salirnos del tablero (aunque no deberia ser posible por las comprobaciones)
                    tablero[fila + k][col] = v.getId();
                }
            }
        }
    }


    public String getIdPorPosicion(String[] posiciones){

        String col = posiciones[0];
        String fila = posiciones[1];

        return tablero[Integer.parseInt(col)][Integer.parseInt(fila)];
    }

    public void mostrar() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(tablero[i][j] + " ");
            }
            System.out.println();
        }
    }

    public Vehiculo getVehiculoPorID(String id_buscado){
        for (Vehiculo vehi : vehiculos) {
            if (vehi.getId().equals(id_buscado)) { // Si es el que buscamos, lo devolvemos
                return vehi;
            }
        }

        return null;

    }

    public String getTamanioPorID(String id_buscado){

        Vehiculo vehi = getVehiculoPorID(id_buscado);
        String resultado = "";
        
        if (vehi != null) {
            resultado = String.valueOf(vehi.getLongitud());
        }

        return resultado;
    }

    public ArrayList<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public int getSize() {
        return SIZE;
    }
}
