
public class RushHour {

    private final static int FILA_SALIDA = 2;
    private static String[] caracteres;

    public static void main(String[] args) {

        // String cadena = "BBJoooHoJDDMHAAooMHoKEEMIoKLFFIGGLoo"; 
        // caracteres = cadena.split("");
        Tablero tablero = new Tablero();
        int estadoErrorCadena = 0;
        
        if (args.length == 0) {
            System.out.println("No se proporcionó ninguna acción.");
            return;
        }

        String accion = args[0];
        String cadena = args[2];

        caracteres = cadena.split("");

        switch (accion) {
            case "verify":
                System.out.println("Ejecutando verify");
                estadoErrorCadena = comprobarCadena(cadena, caracteres, tablero);
                tablero.mostrar();

                System.out.println(estadoErrorCadena);
                break;

            case "question":
                comprobarCadena(cadena, caracteres, tablero);
                String resultado = parsearQuestion(args, tablero);
                tablero.mostrar();

                System.out.println("Ejecutando question");
                System.out.println("RESULTADO: " + resultado);

                break;

            default:
                System.out.println("No has indicado una accion valida");
        }
    }

    public static String parsearQuestion(String[] args, Tablero tablero){

        String flag = null;
        String valorFlag = null;
        String result = "";

        if (args.length >= 4 && args.length <=5) { // 4 a 5 argumentos como maximo
            flag = args[3];

            if(args.length == 5){
                valorFlag = args[4]; // El valor para la flag. Por ej: --whereis F
            } 
        }

        else {
            System.out.println("Error, utiliza 'question' con almenos un parametro");
        }

        // Ejecutar la consulta
        result = ejecutarQuestion(flag, valorFlag, tablero);


        return result;

    }

    public static String ejecutarQuestion(String flag, String valorFlag, Tablero tablero){
        String result = "";
         switch (flag) {
            case "--whereis":
                Vehiculo vehi = tablero.getVehiculoPorID(valorFlag);
                result = vehi.getPoscionesString();
                break;

            case "--size":
                result = tablero.getTamanioPorID(valorFlag);
                break;

            case "--what":
                String posiciones[] = valorFlag.split(","); // Obtenemos las posiciones 
                result = tablero.getIdPorPosicion(posiciones);
                break;

            case "--howmany":
                result = String.valueOf(tablero.getVehiculos().size());
                break;
        }
        
        return result;
    }


    public static int comprobarCadena(String cadena, String[] caracteres, Tablero tablero) {
        int estaRojoValido = 3; 
        int estadoErrorCadena = 0;
        Vehiculo vehiculo_actual;

        if (caracteres.length != 36) {
            estadoErrorCadena = 1;
        } else {
            for (int i = 0; i < caracteres.length && estadoErrorCadena == 0; i++) {
                if (!caracteres[i].equals("o")) {
                    vehiculo_actual = construirVehiculo(caracteres, i);

                    if (estaRojoValido == 3 && vehiculo_actual.getId().equals("A")) {
                        estaRojoValido = comprobarRojoValido(vehiculo_actual, estaRojoValido);
                    }

                    estadoErrorCadena = comprobarVehiculo(vehiculo_actual, tablero, estadoErrorCadena);
                }
            }

            if (estaRojoValido != 0 && estadoErrorCadena == 0) {
                estadoErrorCadena = estaRojoValido;
            }
        }

        return estadoErrorCadena;
    }

    public static int comprobarRojoValido(Vehiculo vehiculo_actual, int estado_actual){
        if (vehiculo_actual.getFila() != FILA_SALIDA) return 4;
        if (!vehiculo_actual.esHorizontal()) return 5;
        return 0;
    }

    public static int comprobarVehiculo(Vehiculo vehiculo_actual, Tablero tablero, int estado_actual) {
        if (vehiculo_actual.getLongitud() < 2 || vehiculo_actual.getLongitud() > 3) {
            return 6;
        }

        if (!vehiculo_actual.tieneIdValido()) {
            return 2;
        }

        for (Vehiculo vehi : tablero.getVehiculos()) {
            if (vehi.getId().equals(vehiculo_actual.getId())) {
                return 7;
            }
        }

        tablero.agregarVehiculo(vehiculo_actual);
        return estado_actual;
    }

    public static Vehiculo construirVehiculo(String[] caracteres, int i) {
        String id_actual = caracteres[i];
        Vehiculo vehiculo_actual = new Vehiculo(id_actual, i / 6, i % 6);

        boolean extendido = extenderVehiculoHorizontal(vehiculo_actual, caracteres, i);

        if (!extendido) {
            extenderVehiculoVertical(vehiculo_actual, caracteres, i);
        }

        return vehiculo_actual;
    }

    private static boolean extenderVehiculoHorizontal(Vehiculo vehiculo, String[] caracteres, int i) {
        String id = vehiculo.getId();
        boolean extendido = false;

        while (i + 1 < caracteres.length && (i % 6) != 5 && id.equals(caracteres[i + 1])) {
            vehiculo.incrementarLongitud();
            caracteres[i + 1] = "o";
            vehiculo.setHorizontal();
            i++;
            extendido = true;

            // Añadimos la nueva posicion horizontal a la lista
           int fila = vehiculo.getFila();
           int col = vehiculo.getColumna() + vehiculo.getLongitud() - 1;
           vehiculo.agregarPosicion(fila, col);
        }

        return extendido;
    }

    private static void extenderVehiculoVertical(Vehiculo vehiculo, String[] caracteres, int i) {
        String id = vehiculo.getId();
        int ptr = 6;

        while (i + ptr < caracteres.length && id.equals(caracteres[i + ptr])) {
            vehiculo.incrementarLongitud();
            caracteres[i + ptr] = "o";
            vehiculo.setVertical();

            // Guardamos la nueva posición vertical a la lista
            int fila = vehiculo.getFila() + vehiculo.getLongitud() - 1;
            int col = vehiculo.getColumna();
            vehiculo.agregarPosicion(fila, col);

            ptr += 6;
        }
    }
}
