package clases;

import java.util.TreeSet; //para las colecciones de clases (eventos en nuestro caso)
import java.io.*; // Implementa un manejo de errores adecuado para capturar excepciones como IOException o FileNotFoundException.
import java.util.Scanner; //Se utiliza un Scanner para leer el archivo línea por línea. Cada línea se divide en un array de cadenas, separando los valores por las comas. Luego, se crean nuevos objetos evento a partir de estos datos.
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import algoritmos.input;

public class GestorDeReservas {
    private Calendario calendario;
    public TreeSet<Evento>  eventos;

    public GestorDeReservas(){
        eventos = new TreeSet<>();
        calendario = new Calendario(2020,2030);
    }

    public GestorDeReservas(int anioInicio, int anioFinal){
        eventos = new TreeSet<>();
        calendario = new Calendario(anioInicio,anioFinal);
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

        Evento nuevoEvento = new Evento(
                titulo,
                new Fecha(dia, mes, anio),
                ubicacion,
                descripcion,
                integrantes,
                convertirHoraToPostBlock(horaInicio,minutosInicio),
                convertirHoraToPostBlock(duracionEnHoras,duracionEnMinutos)
        );
        eventos.add(nuevoEvento);
        calendario.agregarEvento(nuevoEvento);
    }

    public void generarEvento(
            String titulo,
            Fecha fecha,
            String ubicacion,
            String descripcion,
            String[] integrantes,
            int horaInicio,
            int minutosInicio,
            int horaFinal,
            int minutosFinal){

        int duracionEnHoras = horaFinal - horaInicio;
        int duracionEnMinutos = minutosFinal - minutosInicio;

        Evento nuevoEvento = new Evento(
                titulo,
                fecha,
                ubicacion,
                descripcion,
                integrantes,
                convertirHoraToPostBlock(horaInicio,minutosInicio),
                convertirHoraToPostBlock(duracionEnHoras,duracionEnMinutos)
        );
        eventos.add(nuevoEvento);
        calendario.agregarEvento(nuevoEvento);
    }

    public void eliminarEvento(Evento eventoEnCuestion){
        if(eventos.remove(eventoEnCuestion)){
            calendario.eliminarEvento(eventoEnCuestion);
        }
    }

    public void editarEvento(
            Evento evento,
            String newTitulo,
            int dia, int mes, int anio,
            String newUbicacion,
            String newDescripcion,
            String[] newIntegrantes,
            int horaInicio,
            int minutosInicio,
            int horaFinal,
            int minutosFinal){
        int duracionEnHoras = horaFinal - horaInicio;
        int duracionEnMinutos = minutosFinal - minutosInicio;

        evento.editar(
                newTitulo,
                new Fecha(dia, mes, anio),
                newUbicacion,
                newDescripcion,
                newIntegrantes,
                convertirHoraToPostBlock(horaInicio,minutosInicio),
                convertirHoraToPostBlock(duracionEnHoras,duracionEnMinutos)
        );
    }

    //guardado y carga de datos en archivo_____________________________________________________________
    //guardar datos en archivo
    public void guardarDatos() throws IOException {
        // Obtener la ruta del directorio actual
        String directorioBase = Paths.get("").toAbsolutePath().toString();

        // Crear una subcarpeta para los archivos guardados
        String carpetaGuardados = "guardadoDeEventos";
        Path directorioGuardados = Paths.get(directorioBase, carpetaGuardados);
        Files.createDirectories(directorioGuardados);

        // Construir la ruta completa del archivo
        String rutaCompleta = directorioGuardados.toString() + "/copiaParaRecuperacionDeEventos.txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(rutaCompleta))) {
            for (Evento evento : eventos) {
                String linea = evento.getTitulo() + ";" +
                        evento.getFecha().getDia() + ";" +
                        evento.getFecha().getMes() + ";" +
                        evento.getFecha().getAnio() + ";" +
                        evento.getUbicacion() + ";" +
                        evento.getDescripcion() + ";" +
                        // Convertir el array de integrantes en una cadena (por ejemplo, usando String.join)
                        String.join("#", evento.getIntegrantes()) + ";" +
                        evento.getHoraInicio() + ";" +
                        evento.getDuracion();
                writer.println(linea);
            }
        }
    }

    //cargar datos de un archivo

    public void cargarDatos() throws FileNotFoundException {
        String CARPETA_GUARDADOS = "guardadoDeEventos"; // Nombre constante de la carpeta

        // Obtener la ruta del directorio actual
        String directorioBase = Paths.get("").toAbsolutePath().toString();

        // Construir la ruta completa del archivo
        String rutaCompleta = directorioBase + "/" + CARPETA_GUARDADOS + "/copiaParaRecuperacionDeEventos.txt";

        try (Scanner scanner = new Scanner(new File(rutaCompleta))) {
            while (scanner.hasNextLine())
            {
                String linea = scanner.nextLine();
                String[] datos = linea.split(";");

                String titulo = datos[0];
                Fecha fechaEvento = new Fecha(Integer.parseInt(datos[1]),Integer.parseInt(datos[2]),Integer.parseInt(datos[3]));
                String ubicacion = datos[4];
                String descripcion = datos[5];
                String[] integrantes = datos[6].split("#"); // Convertir la cadena en un array
                int horaInicio = Integer.parseInt(datos[7]);
                int duracion = Integer.parseInt(datos[8]);

                Evento nuevoEvento = new Evento(titulo, fechaEvento, ubicacion, descripcion, integrantes, horaInicio, duracion);
                eventos.add(nuevoEvento);
                calendario.agregarEvento(nuevoEvento);
            }
        }
    }
    //__________________________________________________________________________________________

    //los PostBlock son de 15 minutos
    private int convertirHoraToPostBlock(int horas, int minutos) {
        return horas * 4 + (minutos >= 15 ? 1 : 0) + (minutos >= 30 ? 1 : 0) + (minutos >= 45 ? 1 : 0);
    }

    private int[] convertirPostBlockToHora(int horaPostBlock) { //el string devuelto siempre será de 2 posiciones
        int horas = horaPostBlock / 4;
        int minutos = (horaPostBlock % 4) * 15;
        return new int[] { horas, minutos };
    }

    //_________________________________________________________________________________________________
    //                                      Pruebas Graficas
    //_________________________________________________________________________________________________

    // Los eventos ya están ordenados en el TreeSet
    // Imprimir los eventos ordenados
    private void imprimirEventosPorConsola(){
        System.out.println("___________________________________________________");
        System.out.println("Lista de eventos:");
        int i = 1;
        for(Evento evento : eventos) {
            System.out.println(i  + " - " + evento.getTitulo() + " - " + evento.getFecha().getDia() + " / "
                    + evento.getFecha().getMes() +  " / "
                    + evento.getFecha().getAnio() + " "
            );
            i++;
        }
        System.out.println("___________________________________________________");
    }

    private int sanitizarEntradaMinutos(input input){
        System.out.println("Introduzca los minutos: ");
        int minutos = input.numero();
        while(minutos >=60 || minutos<0){
            System.out.println("Entrada invalida, solo se aceptarán valores entre 0 y 59");
            System.out.println("A continuacion vuelva a ingresar los minutos");
            minutos = input.numero();
        }
        return minutos;
    }
    private int sanitizarEntradaHora(input input){
        System.out.println("Introduzca la hora: ");
        int hora = input.numero();
        while( hora >= 24 || hora < 0){
            System.out.println("Entrada invalida, solo se aceptarán valores entre 0 y 23");
            System.out.println("A continuacion vuelva a ingresar la hora deseada");
            hora = input.numero();
        }
        return hora;
    }

    private int sanitizarEntradaAnio(int anio, input input){
        while(!calendario.existeAnio(anio)){
            System.out.println("Error, el año no pertenece al calendario");
            System.out.println("A continuacion vuelva a ingresar el año deseado");
            anio = input.numero();
        }
        return anio;
    }

    private int sanitizarEntradaMes(int mes, input input){
        while( mes > 12 || mes <= 0){
            System.out.println("Entrada invalida, solo se aceptarán valores entre 01 y 12");
            System.out.println("A continuacion vuelva a ingresar el mes deseado");
            mes = input.numero();
        }
        return mes;
    }

    private int sanitizarEntradaDia(int dia, input input){
        while( dia > 31 || dia <= 0){
            System.out.println("Entrada invalida, solo se aceptarán valores entre 1 y 31");
            System.out.println("A continuacion vuelva a ingresar el dia deseado");
            dia = input.numero();
        }
        return dia;
    }

    private Fecha ingresarFecha(input input){
        int dia;
        int mes;
        int anio;
        Fecha fecha;

        System.out.println("ingrese el año del evento:");
        anio = input.numero();
        sanitizarEntradaAnio(anio,input);
        System.out.println("ingrese el mes del evento:");
        mes = input.numero();
        sanitizarEntradaMes(mes,input);
        System.out.println("ingrese el dia del evento:");
        dia = input.numero();
        sanitizarEntradaDia(dia,input);

        fecha = new Fecha(dia,mes,anio);

        return fecha;
    }

    private Fecha sanitizarEntradaFecha(input input){

        Fecha fecha = ingresarFecha(input);

        while(!calendario.existeFecha(fecha)){
            System.out.println("La fecha ingresada no pertenece al calendario, deberá ingresar una fecha válida para continuar");

            fecha = ingresarFecha(input);
        }

        return fecha;
    }


    private String[] ingresarIntegrantes(input input){
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



    public void probarPorConsola(){
        //inicializando variables para la prueba
        int op;
        int num;
        int pos;

        //Variables para agregar o editar eventos:

        //input
        input input = new input();
        input.inicializar();
        // arranca program:
        Boolean programrun = true;
        while(programrun){
            imprimirEventosPorConsola();

            System.out.println("\n ingrese la operacion que desea realizar: ");
            System.out.print("" + //
                    "1 --> Agregar clases.Evento \n" +
                    "2 --> Eliminar clases.Evento \n" +
                    "3 --> Editar clases.Evento \n" +
                    "4 --> Guardar Cambios  \n" +
                    "5 --> Cargar ultima agenda \n" +
                    "6 --> Vaciar agenda \n" +
                    "7 --> Finalizar programa \n");

            op = input.numero();
            System.out.println();
            if ((op == 2 || op == 3) && eventos.isEmpty()){
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

                        Fecha fecha = sanitizarEntradaFecha(input);

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
                            generarEvento(
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
                        if (indice > 0 && indice < eventos.size()) {
                            int i = 1;
                            for(Evento evento : eventos) {
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
                                    eliminarEvento(eventoEliminar);
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
                            guardarDatos();
                            System.out.println("Eventos guardados correctamente.");
                        } catch (IOException e) {
                            System.out.println("Error al guardar los eventos: " + e.getMessage());
                        }
                        break;
                    }
                    case 5:
                    {
                        try {
                            cargarDatos();
                            System.out.println("Eventos cargados correctamente.");
                        } catch (FileNotFoundException e) {
                            System.out.println("Error al cargar los eventos: " + e.getMessage());
                        }
                        break;
                    }
                    case 6:
                    {
                        eventos.clear();
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
