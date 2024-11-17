import java.util.TreeSet;

public class gestorDeReservas {
    private calendario calendario;
    private TreeSet<evento>  eventos;

    public void inicializarGestor(){
        eventos = new TreeSet<>();
        calendario = new calendario(2020,2030);
    }

    public void generarEvento(
            String titulo,
            int dia, int mes, int anio,
            String ubicacion,
            String descripcion,
            String[] integrantes,
            int horaInicio,
            int minutosInicio,
            int horaFinal,
            int minutosFinal){
        int duracionEnHoras = horaFinal - horaInicio;
        int duracionEnMinutos = minutosFinal - minutosInicio;

        evento nuevoEvento = new evento(
                titulo,
                new fecha(dia, mes, anio),
                ubicacion,
                descripcion,
                integrantes,
                convertirHoraToPostBlock(horaInicio,minutosInicio),
                convertirHoraToPostBlock(duracionEnHoras,duracionEnMinutos)
        );

        eventos.add(nuevoEvento);
    }


    // Los eventos ya están ordenados en el TreeSet

    // Imprimir los eventos ordenados
    public void imprimirEventosPorConsola(){
        for(evento evento : eventos) {
            System.out.println(evento.getTitulo() + " - " + evento.getFecha().getDia());
        }
    }

    private int convertirHoraToPostBlock(int horas, int minutos){} //devuelve la version en postBlock de una hora
    private int convertirPostBlockToHora(){} //devuelve int horas, int minutos



}
