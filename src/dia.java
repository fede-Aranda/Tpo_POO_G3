public class dia extends fecha {
    private evento[] eventos;
    private int topeDeEventos;
    private int eventosCargados;
    private horario horario;
    // ... constructores y

    public dia (int dia, int mes, int anio){
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
        topeDeEventos = 9; //tope de 10 eventos por dia
        eventosCargados = 0;
        this.horario = new horario();
        this.eventos = new evento[topeDeEventos];
    }

    // getters/setters ...

    public void agregarEvento(evento eventoEnCuestion){
        if(eventosCargados < topeDeEventos) {
            eventos[eventosCargados] = eventoEnCuestion; //agrego el evento en cuestion a la lista de eventos
            eventosCargados++; //se incrementa la cantidad de eventos cargados
        }else{
            throw new RuntimeException("maximo_de_eventos_por_dia_alcanzado");
        }
    }
}
