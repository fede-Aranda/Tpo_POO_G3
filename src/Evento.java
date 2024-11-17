public class Evento implements Comparable<Evento>{

    //atributos:
    private String titulo;
    private Fecha fecha;

    private String ubicacion;
    private String descripcion;
    private String[] integrantes;

    private int horaInicio;
    private int duracion;

    //constructores:

    public Evento(
            String titulo,
            Fecha fecha,
            String ubicacion,
            String descripcion,
            String[] integrantes,
            int horaInicio,
            int duracion
    ){
        this.titulo = titulo;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.integrantes = integrantes;
        this.horaInicio = horaInicio;
        this.duracion = duracion;
    }

    // Getters y Setters:

    public void setHoraInicio(int horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public int getDuracion() {return duracion;}

    public int getHoraInicio() {
        return horaInicio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Fecha getFecha() {
        return fecha;
    }

    public void setFecha(Fecha fecha) {
        this.fecha = fecha;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String[] getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(String[] integrantes) {
        this.integrantes = integrantes;
    }

    //metodos:

    public void editar(
            String newTitulo,
            Fecha newFecha,
            String newUbicacion,
            String newDescripcion,
            String[] newIntegrantes,
            int newHoraInicio,
            int newDuracion
    ){
        String titulo = getTitulo();
        Fecha fecha = getFecha();
        String ubicacion = getUbicacion();
        String descripcion = getUbicacion();
        String[] integrantes = getIntegrantes();
        int horaInicio = getHoraInicio();
        int duracion = getDuracion();

        if( !(titulo.equals(newTitulo)) && newTitulo != null ){
            setTitulo(newTitulo);
        }
        if(fecha != newFecha && newFecha != null ){
            setFecha(newFecha);
        }
        if( !(ubicacion.equals(newUbicacion)) && newUbicacion != null ){
            setUbicacion(newUbicacion);
        }
        if(!(descripcion.equals(newDescripcion)) && newDescripcion != null ){
            setDescripcion(newDescripcion);
        }
        if(integrantes != newIntegrantes && newIntegrantes != null ){
            setIntegrantes(newIntegrantes);
        }
        if(horaInicio != newHoraInicio){
            setHoraInicio(newHoraInicio);
        }
        if(duracion != newDuracion){
            setDuracion(newDuracion);
        }
    }

    @Override
    public int compareTo(Evento otherEvento) {
        //comparar por fecha de inicio o por título.
        return this.getFecha().compareTo(otherEvento.getFecha()); // Compara por fecha
    }
}