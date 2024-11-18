import clases.GestorDeReservas;
import interfaces.*;
public class Main1 {
    public static void main(String[] args) {
        // Crear el gestor de reservas
        GestorDeReservas gestor = new GestorDeReservas();

        // Inicializar la interfaz principal
        InterfazEvento interfaz = new InterfazEvento(gestor);
        interfaz.setVisible(true);
    }
}