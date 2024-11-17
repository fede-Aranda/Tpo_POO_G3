import java.util.TreeSet; //para las colecciones de clases (eventos en nuestro caso)
import java.io.*; // Implementa un manejo de errores adecuado para capturar excepciones como IOException o FileNotFoundException.
import java.util.Scanner; //Se utiliza un Scanner para leer el archivo línea por línea. Cada línea se divide en un array de cadenas, separando los valores por las comas. Luego, se crean nuevos objetos evento a partir de estos datos.
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class gestorDeReservas {
    private calendario calendario;
    public TreeSet<evento>  eventos;

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
        calendario.agregarEvento(nuevoEvento);
    }

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
            for (evento evento : eventos) {
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
                fecha fechaEvento = new fecha(Integer.parseInt(datos[1]),Integer.parseInt(datos[2]),Integer.parseInt(datos[3]));
                String ubicacion = datos[4];
                String descripcion = datos[5];
                String[] integrantes = datos[6].split("/"); // Convertir la cadena en un array
                int horaInicio = Integer.parseInt(datos[7]);
                int duracion = Integer.parseInt(datos[8]);

                evento nuevoEvento = new evento(titulo, fechaEvento, ubicacion, descripcion, integrantes, horaInicio, duracion);
                eventos.add(nuevoEvento);
                calendario.agregarEvento(nuevoEvento);
            }
        }
    }


    // Los eventos ya están ordenados en el TreeSet

    // Imprimir los eventos ordenados
    public void imprimirEventosPorConsola(){
        for(evento evento : eventos) {
            System.out.println(evento.getTitulo() + " - " + evento.getFecha().getDia() + " "
                    + evento.getFecha().getMes() +  " "
                    + evento.getFecha().getAnio() + " "
            );
        }
    }

    //los PostBlock son de 15 minutos
    private int convertirHoraToPostBlock(int horas, int minutos) {
        int postBlock = horas * 4 + (minutos >= 15 ? 1 : 0) + (minutos >= 30 ? 1 : 0) + (minutos >= 45 ? 1 : 0);
        return postBlock;
    }

    private int[] convertirPostBlockToHora(int horaPostBlock) { //el string devuelto siempre será de 2 posiciones
        int horas = horaPostBlock / 4;
        int minutos = (horaPostBlock % 4) * 15;
        return new int[] { horas, minutos };
    }
}
