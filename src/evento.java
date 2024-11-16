public class evento {
    private String titulo;
    private fecha fecha;

    private String ubicacion;
    private String descripcion;
    private String[] integrantes;

    //  inicio y duracion se tienen que guardar en POSBLOCK
    private int inicio;
    private int duracion;

    // ... constructores y getters/setters ...

    public int getInicio (){
        return inicio;
    }
    public int getDuracion(){
        return duracion;
    }
}