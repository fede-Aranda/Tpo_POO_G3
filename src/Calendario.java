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

    //demas metodos:

    public Dia getDiaDelEvento(Evento evento) {
        Fecha fecha = evento.getFecha();
        int anioDelEvento = fecha.getAnio();
        int mes = fecha.getMes();
        int dia = fecha.getDia();
        if (!existeAnio(anioDelEvento)) {
            throw new RuntimeException("El año del evento se encuentra fuera de los límites del calendario");
        } else {
            int posicionArray = anioDelEvento - anioInicio;
            // Verificamos si la posición en el arreglo es válida y si el mes y día existen en ese año
            if (posicionArray >= 0 && posicionArray < anios.length &&
                    mes >= 1 && mes <= 12 &&
                    dia >= 1 && dia <= anios[posicionArray].getMes(mes).getCantidadDeDias()) {
                return anios[posicionArray].getMes(mes).getDia(dia);
            } else {
                return null; // Indicamos que no se encontró el día
            }
        }
    }

    public void agregarEvento(Evento evento) {
        getDiaDelEvento(evento).agregarEvento(evento);
    }

    public void eliminarEvento(Evento evento){
        getDiaDelEvento(evento).eliminarEvento(evento);
    }

    public boolean existeAnio(int anio){
        return(!(anio > (anioInicio + cantidadDeAniosAlmacenados)) || (anio < anioInicio));
    }

    public boolean existeFecha(Fecha fecha){
        int anioDelEvento = fecha.getAnio();
        int mes = fecha.getMes();
        int dia = fecha.getDia();
        if (!existeAnio(anioDelEvento)) {
            return false;
        } else {
            int posicionArray = anioDelEvento - anioInicio;
            // Verificamos si la posición en el arreglo es válida y si el mes y día existen en ese año
            return posicionArray >= 0 && posicionArray < anios.length &&
                    mes >= 1 && mes <= 12 &&
                    dia >= 1 && dia <= anios[posicionArray].getMes(mes).getCantidadDeDias();
        }
    }
}