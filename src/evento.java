public class evento {
    private String titulo;
    private fecha fecha;

    private String ubicacion;
    private String descripcion;
    private String[] integrantes;

    private int horaInicio;
    private int duracion;

    @Override
    public int compareTo(evento otroEvento) {
        return fecha.compareTo(otroEvento.fecha);
    }
    }

    public void setHoraInicio(int horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public int getHoraInicio() {
        return horaInicio;
    }

    public int getDuracion() {
        return duracion;
    }
    // ... constructores y getters/setters ...

    public int getInicio (){
        return inicio;
    }
    public int getDuracion(){
        return duracion;
    }
}