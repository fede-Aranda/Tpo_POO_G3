public class calendario {
    private final anio[] anios;
    private final int anioInicio;
    private final int cantidadDeAniosAlmacenados;

    public calendario (int anioInicio, int anioFin){
        int cantidadDeAnios = anioFin - anioInicio;
        this.anioInicio = anioInicio;
        this.cantidadDeAniosAlmacenados = cantidadDeAnios;
        anios = new anio[cantidadDeAnios]; //preparo la lista de años
        int anioActual = anioInicio;
        anio nuevoAnio;
        for(int a = 0; a < cantidadDeAnios; a++){  //for de años
            nuevoAnio = new anio(anioActual);
            anios[a] = nuevoAnio;
            anioActual++;
        }
    }

    // ... otros métodos ...

    public dia getDiaDelEvento(evento evento){ //dado un evento te devuelve el dia al que pertenece
        fecha fecha = evento.getFecha();
        int anioDelEvento = fecha.getAnio();
        if((anioDelEvento > (anioInicio + cantidadDeAniosAlmacenados)) || (anioDelEvento < anioInicio) ){
            throw new RuntimeException("El año del evento se encuentra fuera de los limites del calendario");
        } else{ // (año de inicio + x = año del evento) => (evento - inicio = x)
            int posicionArray = anioDelEvento - anioInicio;
            return (anios[posicionArray].getMes(fecha.getMes())).getDia(fecha.getDia());
        }
    }

    public void agregarEvento(evento evento) {
        getDiaDelEvento(evento).agregarEvento(evento);
    }

    public void eliminarEvento(evento evento){
        getDiaDelEvento(evento).eliminarEvento(evento);
    }
}