public class Calendario {
    private final Anio[] anios;
    private final int anioInicio;
    private final int cantidadDeAniosAlmacenados;

    public Calendario(int anioInicio, int anioFin){
        int cantidadDeAnios = anioFin - anioInicio;
        this.anioInicio = anioInicio;
        this.cantidadDeAniosAlmacenados = cantidadDeAnios;
        anios = new Anio[cantidadDeAnios]; //preparo la lista de años
        int anioActual = anioInicio;
        Anio nuevoAnio;
        for(int a = 0; a < cantidadDeAnios; a++){  //for de años
            nuevoAnio = new Anio(anioActual);
            anios[a] = nuevoAnio;
            anioActual++;
        }
    }

    // ... otros métodos ...

    public Dia getDiaDelEvento(Evento evento){ //dado un evento te devuelve el dia al que pertenece
        Fecha fecha = evento.getFecha();
        int anioDelEvento = fecha.getAnio();
        if((anioDelEvento > (anioInicio + cantidadDeAniosAlmacenados)) || (anioDelEvento < anioInicio) ){
            throw new RuntimeException("El año del evento se encuentra fuera de los limites del calendario");
        } else{ // (año de inicio + x = año del evento) => (evento - inicio = x)
            int posicionArray = anioDelEvento - anioInicio;
            return (anios[posicionArray].getMes(fecha.getMes())).getDia(fecha.getDia());
        }
    }

    public void agregarEvento(Evento evento) {
        getDiaDelEvento(evento).agregarEvento(evento);
    }

    public void eliminarEvento(Evento evento){
        getDiaDelEvento(evento).eliminarEvento(evento);
    }
}