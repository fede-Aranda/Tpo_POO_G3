public class calendario {
    private anio[] anios;
    private int anioInicio;
    private int cantidadDeAniosAlmacenados;

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

    public void agregarEvento(evento evento) {
        fecha fecha = evento.getFecha();
        int anioDelEvento = fecha.getAnio();
        if((anioDelEvento > (anioInicio + cantidadDeAniosAlmacenados)) || (anioDelEvento < anioInicio) ){
            throw new RuntimeException("El año del evento se encuentra fuera de los limites del calendario");
        } else{ // (inicio + x = evento) => (evento - inicio = x)
            int posicionArray = anioDelEvento - anioInicio;
            ((anios[posicionArray].getMes(fecha.getMes())).getDia(fecha.getDia())).agregarEvento(evento);
        }
    }

    // ... otros métodos ...
}