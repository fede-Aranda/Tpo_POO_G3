package interfaces;

import algoritmos.input;
import clases.Evento;
import clases.Fecha;
import clases.GestorDeReservas;

import java.io.FileNotFoundException;
import java.io.IOException;

public class interfazPorConsola {
    //_________________________________________________________________________________________________
    //                                      Pruebas Graficas
    //_________________________________________________________________________________________________

    // Los eventos ya están ordenados en el TreeSet
    // Imprimir los eventos ordenados
    private static void imprimirEventosPorConsola(GestorDeReservas gestor){
        System.out.println("___________________________________________________");
        System.out.println("Lista de eventos:");
        int i = 1;
        for(Evento evento : gestor.eventos) {
            System.out.println(i  + " - " + evento.getTitulo() + " - " + evento.getFecha().getDia() + " / "
                    + evento.getFecha().getMes() +  " / "
                    + evento.getFecha().getAnio() + " "
            );
            i++;
        }
        System.out.println("___________________________________________________");
    }

    private static int sanitizarEntradaMinutos(input input){
        System.out.println("Introduzca los minutos: ");
        int minutos = input.numero();
        while(minutos >=60 || minutos<0){
            System.out.println("Entrada invalida, solo se aceptarán valores entre 0 y 59");
            System.out.println("A continuacion vuelva a ingresar los minutos");
            minutos = input.numero();
        }
        return minutos;
    }
    private static int sanitizarEntradaHora(input input){
        System.out.println("Introduzca la hora: ");
        int hora = input.numero();
        while( hora >= 24 || hora < 0){
            System.out.println("Entrada invalida, solo se aceptarán valores entre 0 y 23");
            System.out.println("A continuacion vuelva a ingresar la hora deseada");
            hora = input.numero();
        }
        return hora;
    }

    private static int sanitizarEntradaAnio(int anio, input input, GestorDeReservas gestor){
        while(!gestor.getCalendario().existeAnio(anio)){
            System.out.println("Error, el año no pertenece al calendario");
            System.out.println("A continuacion vuelva a ingresar el año deseado");
            anio = input.numero();
        }
        return anio;
    }

    private static int sanitizarEntradaMes(int mes, input input){
        while( mes > 12 || mes <= 0){
            System.out.println("Entrada invalida, solo se aceptarán valores entre 01 y 12");
            System.out.println("A continuacion vuelva a ingresar el mes deseado");
            mes = input.numero();
        }
        return mes;
    }

    private static int sanitizarEntradaDia(int dia, input input){
        while( dia > 31 || dia <= 0){
            System.out.println("Entrada invalida, solo se aceptarán valores entre 1 y 31");
            System.out.println("A continuacion vuelva a ingresar el dia deseado");
            dia = input.numero();
        }
        return dia;
    }

    private static Fecha ingresarFecha(input input, GestorDeReservas gestor){
        int dia;
        int mes;
        int anio;
        Fecha fecha;

        System.out.println("ingrese el año del evento:");
        anio = input.numero();
        sanitizarEntradaAnio(anio,input, gestor);
        System.out.println("ingrese el mes del evento:");
        mes = input.numero();
        sanitizarEntradaMes(mes,input);
        System.out.println("ingrese el dia del evento:");
        dia = input.numero();
        sanitizarEntradaDia(dia,input);

        fecha = new Fecha(dia,mes,anio);

        return fecha;
    }

    private static Fecha sanitizarEntradaFecha(input input, GestorDeReservas gestor){

        Fecha fecha = ingresarFecha(input, gestor);

        while(!gestor.getCalendario().existeFecha(fecha)){
            System.out.println("La fecha ingresada no pertenece al calendario, deberá ingresar una fecha válida para continuar");

            fecha = ingresarFecha(input, gestor);
        }

        return fecha;
    }


    private static String[] ingresarIntegrantes(input input){
        boolean run;
        String[] integrantes = new String[20];
        int pos = 0;
        String respuesta;
        System.out.println("ingrese el nombre del anfitrión del evento");
        integrantes[pos] = input.string();
        System.out.println();
        System.out.println("Desea añadir mas integrantes?");
        while(input.yesOrNo() && pos < 20){
            pos++;
            System.out.println("ingrese el nombre del integrante N°"+pos);
            integrantes[pos] = input.string();
            System.out.println("desea añadir otro integrante?");
        }
        if(pos==20){
            System.out.println("Se alcanzo el maximo de integrantes");
        }
        return integrantes;
    }

    public static void run(){
        //inicializando variables para la prueba
        int op;
        int num;
        int pos;

        //Construir GestorDeReservas:
        GestorDeReservas gestor = new GestorDeReservas();

        //datos de ejemplo
        gestor.generarEvento("Reunión de equipo", 15, 3, 2023, "Sala de juntas", "Planificar el proyecto", new String[]{"Juan", "María", "Pedro"}, 10, 0, 12, 0);
        gestor.generarEvento("Cumpleaños de Ana", 25, 5, 2023, "Mi casa", "Fiesta sorpresa", new String[]{"Ana", "Luis", "Sofía"}, 19, 30, 22, 0);


        //input
        input input = new input();
        input.inicializar();
        // arranca program:
        Boolean programrun = true;
        while(programrun){
            imprimirEventosPorConsola(gestor);

            System.out.println("\n ingrese la operacion que desea realizar: ");
            System.out.print("" + //
                    "1 --> Agregar Evento \n" +
                    "2 --> Eliminar Evento \n" +
                    "3 --> Editar Evento \n" +
                    "4 --> Guardar Cambios  \n" +
                    "5 --> Cargar ultima agenda \n" +
                    "6 --> Vaciar agenda \n" +
                    "7 --> Finalizar programa \n");

            op = input.numero();
            System.out.println();
            if ((op == 2 || op == 3) && gestor.getEventos().isEmpty()){
                System.out.println("No puede realizar la operación elegida debido a que no hay eventos agendados. Primero agregue un evento y luego vuelva a intentarlo");
            } else{
                switch(op){
                    case 1:
                    {
                        String titulo;
                        String ubicacion;
                        String descripcion;
                        String[] integrantes;
                        int horaInicio;
                        int minInicio;
                        int horaFin;
                        int minFin;

                        Fecha fecha = sanitizarEntradaFecha(input,gestor);

                        System.out.println("A continuación va introducir el horario de INICIO del evento (primero horas y luego minutos)");
                        horaInicio = sanitizarEntradaHora(input);
                        minInicio= sanitizarEntradaMinutos(input);

                        System.out.println("A continuación va introducir el horario de FINALIZACION del evento. Este deberá ser posterior al horario de inicio. (primero horas y luego minutos)");

                        horaFin  = sanitizarEntradaHora(input);
                        minFin = sanitizarEntradaMinutos(input);

                        while(horaFin < horaInicio || (horaFin == horaInicio && minFin < minInicio)){
                            System.out.println("El horario de finalizacion debe ser posterior al horario de inicio. Debe volver a introducir ambos");
                            System.out.println("Horario de inicio: ");
                            horaFin  = sanitizarEntradaHora(input);
                            System.out.println("Horario de finalizacion: ");
                            minFin = sanitizarEntradaMinutos(input);
                        }

                        System.out.println("ingrese un titulo para el evento: ");
                        titulo = input.string();

                        System.out.println("ingrese una descripción: ");
                        descripcion = input.string();

                        System.out.println("ingrese la ubicacion del evento: ");
                        ubicacion = input.string();

                        integrantes = ingresarIntegrantes(input);

                        System.out.println("Está a punto de cargar un nuevo evento, desea continuar?");
                        if(input.yesOrNo()){
                            gestor.generarEvento(
                                    titulo,
                                    fecha,
                                    ubicacion,
                                    descripcion,
                                    integrantes,
                                    horaInicio,
                                    minInicio,
                                    horaFin,
                                    minFin);
                        }
                        break;
                    }
                    case 2:
                    {
                        int indice;
                        System.out.print("Ingrese el índice del evento que deseas eliminar: ");
                        indice = input.numero();
                        Evento eventoEliminar = null;
                        if (indice > 0 && indice < gestor.getEventos().size()) {
                            int i = 1;
                            for(Evento evento : gestor.getEventos()) {
                                if(i==indice) {
                                    eventoEliminar = evento;
                                }
                                i++;
                            }
                            if(eventoEliminar != null){
                                System.out.println("Está a punto de eliminar el siguiente evento: ");
                                System.out.println(indice + eventoEliminar.getTitulo() + " - " + eventoEliminar.getFecha().getDia() + " / "
                                        + eventoEliminar.getFecha().getMes() +  " / "
                                        + eventoEliminar.getFecha().getAnio() + " "
                                );
                                System.out.println("¿Desea continuar?");
                                if(input.yesOrNo()){
                                    gestor.eliminarEvento(eventoEliminar);
                                }else{
                                    System.out.println("Operacion cancelada.");
                                }
                            }
                        }else{
                            System.out.println("ingresó un indice inválido");
                        }
                        break;
                    }
                    case 3:
                    {
                        break;
                    }
                    case 4:
                    {
                        try {
                            gestor.guardarDatos();
                            System.out.println("Eventos guardados correctamente.");
                        } catch (IOException e) {
                            System.out.println("Error al guardar los eventos: " + e.getMessage());
                        }
                        break;
                    }
                    case 5:
                    {
                        try {
                            gestor.cargarDatos();
                            System.out.println("Eventos cargados correctamente.");
                        } catch (FileNotFoundException e) {
                            System.out.println("Error al cargar los eventos: " + e.getMessage());
                        }
                        break;
                    }
                    case 6:
                    {
                        gestor.getEventos().clear();
                        break;
                    }
                    case 7:
                    {
                        programrun = false;
                        break;
                    }
                    default:
                    {
                        System.out.println("Ingrese una opción válida \n");
                    }
                }
            }
        }
        input.cerrar();
        System.out.println("Programa finalizado");
    }
}
