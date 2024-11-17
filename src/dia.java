import java.util.TreeSet;

public class dia{
    private TreeSet<evento> eventos;
    //private horario horario;


    // constructores

    public dia (){
        //this.horario = new horario();
        eventos = new TreeSet<>();
    }

    // getters/setters ...

    public void agregarEvento(evento eventoEnCuestion){
        //if(this.horario.agregarEvento(eventoEnCuestion)){
            eventos.add(eventoEnCuestion); //agrego el evento en cuestion a la lista de eventos
        //}
    }

    public void eliminarEvento(evento eventoEnCuestion){
        eventos.remove(eventoEnCuestion);
    }
}
