package clases;

import java.util.TreeSet;

public class Dia {
    private TreeSet<Evento> eventos;

    // constructores

    public Dia(){
        //this.horario = new horario();
        eventos = new TreeSet<>();
    }

    // getters/setters ...

    public void agregarEvento(Evento eventoEnCuestion){
        //if(this.horario.agregarEvento(eventoEnCuestion)){
            eventos.add(eventoEnCuestion); //agrego el evento en cuestion a la lista de eventos
        //}
    }

    public void eliminarEvento(Evento eventoEnCuestion){
        eventos.remove(eventoEnCuestion);
    }
}
