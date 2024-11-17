public class evento {

    //atributos:
    private String titulo;
    private fecha fecha;

    private String ubicacion;
    private String descripcion;
    private String[] integrantes;

    private int horaInicio;
    private int duracion;

    //constructores:

    public evento (String titulo,fecha fecha,String ubicacion,String descripcion,String[] integrantes,int horaInicio,int duracion){
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

    public int getHoraInicio() {
        return horaInicio;
    }

    public int getInicio (){
        return horaInicio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public fecha getFecha() {
        return fecha;
    }

    public void setFecha(fecha fecha) {
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

    public int getDuracion(){return duracion;}
}