public class horario {
    private boolean[] CuartosDeHoraEnUso = new boolean[96];//array de horas utilizadas representadas en grupos de 4 booleans. longitud:(24 . 4)
    //cada
    private boolean full;

    private void setEvento(int hora_Inicio, int minutos_Inicio, int hora_Final, int minutos_Final){
        //Sanitizo la entrada de minutos
        minutos_Inicio =  redondearCuartoHora(minutos_Inicio);
        minutos_Final = redondearCuartoHora(minutos_Final);
        int duracion = (hora_Final - hora_Inicio)*4; // calculo la duracion del evento en particiones
        int pos_Inicio = hora_Inicio + (minutos_Inicio)/15; // convieto el horario de inicio a una poscion en mi array
        if (checkDisponibilidad(pos_Inicio, duracion)==false){
            pass // levantar excepcion (horario ocupado)
        }
        int index = 0;
        while(duracion > index) { // rellena todos los esapcios que ocupa el evento en espacio dsiponible
            CuartosDeHoraEnUso[(hora_Inicio + ((minutos_Inicio / 15)+index))] = true;
            index++;
        }
    }
    private  boolean checkDisponibilidad(int pos_Inicio,int duracion){
        int index = 0;
        while(duracion > index){
            if (CuartosDeHoraEnUso[index]) {
                return false;
            } index++;
        }
        return true;
    }
    private int redondearCuartoHora(int minutos){
        minutos = Math.max(0, Math.min(minutos,59));
        int resto = minutos % 15;
        int cociente = minutos / 15;
        if (resto >= 8) { cociente++; }
        return cociente*15; //redondeo al cuarto de hora mas cercano
    }
    //private getInicio() {}

    //private getFin() {}

}