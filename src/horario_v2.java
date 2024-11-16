// Nueva clase de horario implementando los cambios en el modelo de clases
// Posiblemente temmporal(?
public class horario_v2 {
    private boolean[] array_disponiblidad = new boolean[96]; //ver si podemos hacer las particiones dinamicas
    private int capacidad_array = 96;

    // Primero pregunto si mi evento entra con getCapacidad, si entra, entonces
    // confirmo que tengo el espacio continuo con checkDisponibilidad

    public int checkDisponibilidad(evento nuevoevento){
        int largo = nuevoevento.getDuracion();
        int contador = 0;
        int inicioDisponibilidad = -1;
        for (int i = 0; i < array_disponiblidad.length; i++) {
            if (!array_disponiblidad[i]){ // si el valor es false = desocupado
                if (contador == 0){
                    inicioDisponibilidad = i; // Guarda la poscion de donde empieza la disponibilidad
                }
                contador++;
                if (contador >= largo){
                    return inicioDisponibilidad;
                }
            } else{
                contador = 0;  // reseteo si hay una interrupcion
                inicioDisponibilidad = -1;
            }
        }
        return inicioDisponibilidad;
    }
    public int getCapacidad(){
        return capacidad_array;
    }

    public void agregarEvento(evento nuevoevento,int pos_array){ // tendria que haber otra igual que no tome pos array sino que llame a check
        // disponibilidad y lo maneje interno
        int inicio = nuevoevento.getInicio();
        int duracion = nuevoevento.getDuracion();
        // me quede aca :/
        // me voy a dormir, no doy mas :)
    }


    public int convertToPOSBLOCK(){ // terminar de implementar
        return 0;
    }
}
