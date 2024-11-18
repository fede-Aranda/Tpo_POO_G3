import clases.GestorDeReservas;
import interfaces.InterfazEvento;
import interfaces.interfazPorConsola;

public class Main {
    public static void main(String[] args) {

        //prueba por consola:
        interfazPorConsola.run();

        //prueba por interfaz:
        System.out.println("Iniciando interfaz gráfica...");
        InterfazEvento interfaz = new InterfazEvento();
        interfaz.setVisible(true);
        System.out.println("interfaz gráfica iniciada.");
    }
}