public class Mes {

    //atributos
    private Dia[] dias;
    private int cantidadDeDias;

    //constructor
    public Mes(int cantidadDeDias){
        dias = new Dia[cantidadDeDias]; //preparo la lista dias
        Dia nuevodia;
        for(int dia = 0; dia < cantidadDeDias; dia++){ // for de dias
            nuevodia = new Dia(); //creo un dia nuevo
            dias[dia] = nuevodia; //lo agrego a la lista
        }
        this.cantidadDeDias =  cantidadDeDias;
    }

    //getters

    //para conseguir el dia de un mes, siempre será la posicion numero (dia-1) del array dias[]
    public Dia getDia(int dia){
        if(dia < 1 || dia > cantidadDeDias){
            throw new RuntimeException("dia_inválido_para_el_mes");
        }else{
            return dias[dia-1];
        }
    }
}
