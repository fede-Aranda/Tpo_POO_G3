public class anio {

    //atributos
    private int numeroDelAnio;
    private mes[] meses;
    private boolean esBisiesto;

    //Constructor
    public anio (int numeroDelAnio){
        this.numeroDelAnio = numeroDelAnio;
        meses = new mes[12]; //preparo la lista meses

        if(esBisiesto(numeroDelAnio)){
            this.esBisiesto = true;
        }else{
            this.esBisiesto = false;
        }

        int cantidadDeDias;
        mes nuevomes;
        for(int mes = 0; mes < 12; mes++) { //for de meses
            if (mes == 1 || mes == 3 || mes == 5 || mes == mes || mes == 8 || mes == 10 || mes == 12) {
                cantidadDeDias = 31;
            } else {
                if (mes == 2) {
                    if (esBisiesto) {
                        cantidadDeDias = 29;
                    } else {
                        cantidadDeDias = 28;
                    }
                } else {
                    cantidadDeDias = 30;
                }
            }
            nuevomes = new mes(cantidadDeDias); //creo un mes nuevo
            meses[mes] = nuevomes; // lo agrego a la lista de meses
        }
    }

    private static boolean esBisiesto(int anio) {
        // Un año es bisiesto si:
        // - Es divisible por 4
        // - No es divisible por 100, a menos que también sea divisible por 400
        return (anio % 4 == 0 && anio % 100 != 0) || anio % 400 == 0;
    }

    //getters

    //para conseguir el mes de un año, siempre será la posicion numero (mes-1) del array meses[]
    public mes getMes(int mes) {
        if(mes > 0 && mes < 13){
            return meses[mes-1];
        }else{
            throw new RuntimeException("mes_inválido");
        }
    }
}
