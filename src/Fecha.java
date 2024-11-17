public class Fecha implements Comparable<Fecha>{

    //atributos:
    private int dia;
    private int mes;
    private int anio;

    // ... constructores
    public Fecha(int dia, int mes, int anio){
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
    }

    // getters y setters ...


    @Override
    public int compareTo(Fecha otraFecha) {
        if (this.anio != otraFecha.anio) {
            return this.anio - otraFecha.anio;
        } else if (this.mes != otraFecha.mes) {
            return this.mes - otraFecha.mes;
        } else {
            return this.dia - otraFecha.dia;
        }
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getDia() {
        return dia;
    }

    public int getMes() {
        return mes;
    }

    public int getAnio() {
        return anio;
    }

}
