import java.util.TreeSet; //para las colecciones de clases (eventos en nuestro caso)
import java.io.*; // Implementa un manejo de errores adecuado para capturar excepciones como IOException o FileNotFoundException.
import java.util.Scanner; //Se utiliza un Scanner para leer el archivo línea por línea. Cada línea se divide en un array de cadenas, separando los valores por las comas. Luego, se crean nuevos objetos evento a partir de estos datos.
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import algoritmos.input;

public class GestorDeReservas {
    private Calendario calendario;
    private TreeSet<Evento>  eventos;

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
    public void guardarDatos(String rutaArchivo) throws IOException {
        // Obtener la ruta del directorio actual
        String directorioBase = Paths.get("").toAbsolutePath().toString();

        // Crear una subcarpeta para los archivos guardados
        String carpetaGuardados = "guardadoDeEventos";
        Path directorioGuardados = Paths.get(directorioBase, carpetaGuardados);
        Files.createDirectories(directorioGuardados);

        // Construir la ruta completa del archivo
        String rutaCompleta = directorioGuardados.toString() + "/copiaParaRecuperacionDeEventos.txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(rutaArchivo))) {
            for (Evento evento : eventos) {
                String linea = evento.getTitulo() + "," +
                        evento.getFecha().getDia() + "," +
                        evento.getFecha().getMes() + "," +
                        evento.getFecha().getAnio() + "," +
                        evento.getUbicacion() + "," +
                        evento.getDescripcion() + "," +
                        // Convertir el array de integrantes en una cadena (por ejemplo, usando String.join)
                        String.join("/", evento.getIntegrantes()) + "," +
                        evento.getHoraInicio() + "," +
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
                String[] datos = linea.split(",");

                String titulo = datos[0];
                Fecha fechaEvento = new Fecha(Integer.parseInt(datos[1]),Integer.parseInt(datos[2]),Integer.parseInt(datos[3]));
                String ubicacion = datos[4];
                String descripcion = datos[5];
                String[] integrantes = datos[6].split("/"); // Convertir la cadena en un array
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
        for(Evento evento : eventos) {
            System.out.println(evento.getTitulo() + " - " + evento.getFecha().getDia() + " / "
                    + evento.getFecha().getMes() +  " / "
                    + evento.getFecha().getAnio() + " "
            );
        }
        System.out.println("___________________________________________________");
    }

    private int sanitizarEntradaMinutos(int minutos, input input){
        while(minutos >=60 || minutos<0){
            System.out.println("Entrada invalida, solo se aceptarán valores entre 0 y 59");
            System.out.println("A continuacion vuelva a ingresar los minutos");
            minutos = input.numero();
        }
        return minutos;
    }
    private int sanitizarEntradaHora(int hora, input input){
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

    public Fecha sanitizarEntradaFecha(input input){

        Fecha fecha = ingresarFecha(input);

        while(!calendario.existeFecha(fecha)){
            System.out.println("La fecha ingresada no pertenece al calendario, deberá ingresar una fecha válida para continuar");

            fecha = ingresarFecha(input);
        }

        return fecha;
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
                    "1 --> Agregar Evento \n" +
                    "2 --> Eliminar Evento \n" +
                    "3 --> Editar Evento \n" +
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

                        System.out.println("ingrese un titulo para el evento: ");
                        titulo = input.string();

                        sanitizarEntradaFecha(input);
                        break;
                    }
                    case 2:
                    {
                        System.out.println("ingrese el numero que desea contar: ");
                        num = input.numero();
                        System.out.println("el elemento aparece "+String.valueOf(listaPrueba.count(num))+" veces");
                        break;
                    }
                    case 3:
                    {
                        System.out.println("ingrese el numero del cual quiere conocer su indice: ");
                        num = input.numero();
                        System.out.println("el indice del elemento es "+String.valueOf(listaPrueba.index(num)));
                        break;
                    }
                    case 4:
                    {
                        System.out.println("ingrese la posicion de la cual quiere conocer su valor: ");
                        num = input.numero();
                        System.out.println("El elemento ubicado en la posicion es "+String.valueOf(listaPrueba.recuperarPos(num)));
                        break;
                    }
                    case 5:
                    {
                        System.out.println("¿Que valor desea insertar?");
                        num = input.numero();
                        System.out.println("¿En que posicion desea insertar el valor?");
                        pos = input.numero();
                        listaPrueba.insert(num, pos);;
                        break;
                    }
                    case 6:
                    {
                        listaPrueba.pop();
                        break;
                    }

                    case 7:
                    {
                        System.out.println("¿Que valor desea remover?");
                        num = input.numero();
                        System.out.print("" + //
                                "1 --> Remover el primero \n" + //
                                "2 --> Remover todos \n");
                        op = input.numero();
                        while(op!=1 && op!=2){
                            System.out.println("Ingrese una opción válida");
                            op = input.numero();
                        }
                        if(op==1){
                            listaPrueba.remove(num);
                        }else{
                            listaPrueba.removeAll(num);
                        }
                        break;
                    }
                    case 8:
                    {
                        if(listaPrueba.listaVacia()){
                            System.out.println("La lista está vacia");
                        } else{
                            System.out.println("Hay elementos en la lista");
                        }
                        break;
                    }
                    case 9:
                    {
                        listaPrueba.clear();
                        break;
                    }
                    case 10:
                    {
                        System.out.print("El largo de la lista es: ");
                        System.out.println(metodosLista.len(listaPrueba));
                        break;
                    }
                    case 11:
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
