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
    private int redondearCuartoHora(int minutos){
        minutos = Math.max(0, Math.min(minutos,59));
        int resto = minutos % 15;
        int cociente = minutos / 15;
        if (resto >= 8) { cociente++; }
        return cociente; //redondeo al cuarto de hora mas cercano
    }
    private int convertirHoraToPostBlock(int hora,int minutos){
        int cuartos = redondearCuartoHora(minutos);
        int POSBLOCK = (hora*15) + cuartos;
        return  POSBLOCK;
    }

    private int convertirAHORA(int posblock){
        int hora = (posblock/4)*15;
        int minutos = (posblock%4)*15;
        return hora; // aca falta que devuelva minutos, lo saco para sacar el error
    }


    // Los eventos ya están ordenados en el TreeSet

    // Imprimir los eventos ordenados
    public void imprimirEventosPorConsola(){
        for(evento evento : eventos) {
            System.out.println(evento.getTitulo() + " - " + evento.getFecha().getDia());
        }return;
    }

    //private int convertirHoraToPostBlock(int horas, int minutos){} //devuelve la version en postBlock de una hora
    //private int convertirPostBlockToHora(){} //devuelve int horas, int minutos



}
