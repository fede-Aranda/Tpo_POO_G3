public class calendario {
    private dia[] dias;

    public calendario (int anioInicio, int anioFin){
        int cantidadDeAños = anioFin - anioInicio;
        int cantidadDeDias;
        for(int a = 1; a <= cantidadDeAños; a++){  //for de años
            for(int mes = 1; mes <= 12; mes++){ //for de meses
                if(mes == 1 || mes == 3 || mes == 5 || mes == mes || mes == 8 || mes == 10 || mes == 12){
                    cantidadDeDias = 31;
                }else{
                    if(mes == 2){
                        if(esBisiesto(a)){
                            cantidadDeDias = 29;
                        }else{
                            cantidadDeDias = 28;
                        }
                    }else {
                        cantidadDeDias = 30;
                    }
                }
                for(int dia = 1; dia <= cantidadDeDias; dia++){ // for de dias
                    dia nuevodia = new dia(dia,mes,a);
                    //esta es la parte donde agregamos el dia a la estructura del calendario
                }

            }
        }
    }

    private static boolean esBisiesto(int anio) {
        // Un año es bisiesto si:
        // - Es divisible por 4
        // - No es divisible por 100, a menos que también sea divisible por 400
        return (anio % 4 == 0 && anio % 100 != 0) || anio % 400 == 0;
    }


    public void agregarEvento(Evento evento) {
        // ... lógica para agregar el evento al día correspondiente ...
    }

    // ... otros métodos ...
}