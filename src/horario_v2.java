// Nueva clase de horario implementando los cambios en el modelo de clases
// Posiblemente temmporal(?

public class horario_v2 {
    private boolean[] array_disponiblidad = new boolean[96]; //ver si podemos hacer las particiones dinamicas
    private int capacidad_array = 96;
    // Primero pregunto si mi evento entra con getCapacidad, si entra, entonces
    // confirmo que tengo el espacio continuo con checkDisponibilidad

    private int checkDisponibilidad(evento nuevoevento){
        int inicio = nuevoevento.getInicio();
        int largo = nuevoevento.getDuracion();
        int contador = 0;
        int inicioDisponibilidad = -1;
        for (int i = inicio; i < array_disponiblidad.length; i++) {
            if (!array_disponiblidad[i]){ // si el valor es false = desocupado
                if (contador == 0){
                    inicioDisponibilidad = i; // Guarda la poscion de donde empieza la disponibilidad
                }
                contador++;
                if (contador >= largo & inicioDisponibilidad == inicio){  // si es ininterrupido y el el inicio de mi evento
                    return inicioDisponibilidad;
                }
            } else{
                contador = 0;  // reseteo si hay una interrupcion
                inicioDisponibilidad = -1;
            }
        }
        return inicioDisponibilidad;
    }
    private int getCapacidad(){
        return capacidad_array;
    }

    public int agregarEvento(evento nuevoevento){
        int inicio = nuevoevento.getInicio();
        int duracion = nuevoevento.getDuracion();
        if (duracion > getCapacidad()){
            return -2; // Codigos de error --> -2 es no entra e por capacidad
        }
        int disponibilidad = checkDisponibilidad(nuevoevento);
        if (disponibilidad == -1){
            return -1; // ----> no entra por disponibilidad
        }else{// Relleno el array desde el inicio de disponibilidad hasta la duracion del evento
            for (int i = disponibilidad; i < Math.min(disponibilidad + duracion, array_disponiblidad.length); i++){
                array_disponiblidad[i]=true;
            } return 1; // Codigo de error ---> existoso
        }
    }
    public void eliminarEvento(evento nuevoevento){
        int inicio = nuevoevento.getInicio();
        int duracion = nuevoevento.getInicio();
        for (int i = inicio; i < Math.min(inicio + duracion, array_disponiblidad.length); i++){
            array_disponiblidad[i] = false;
        } return;

    }
}
