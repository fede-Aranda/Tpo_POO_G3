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
        calendario = new Calendario(2000,2090);
    }

    public GestorDeReservas(int anioInicio, int anioFinal){
        eventos = new TreeSet<>();
        calendario = new Calendario(anioInicio,anioFinal);
    }

    public Calendario getCalendario() {
        return calendario;
    }

    public TreeSet<Evento> getEventos() {
        return eventos;
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
}
