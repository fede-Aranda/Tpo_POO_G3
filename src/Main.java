public class Main {
    public static void main(String[] args) {
        GestorDeReservas gestor = new GestorDeReservas();
        gestor.probarPorConsola();

        gestor.generarEvento("Reunión de equipo", 15, 3, 2023, "Sala de juntas", "Planificar el proyecto", new String[]{"Juan", "María", "Pedro"}, 10, 0, 12, 0);
        gestor.generarEvento("Cumpleaños de Ana", 25, 5, 2023, "Mi casa", "Fiesta sorpresa", new String[]{"Ana", "Luis", "Sofía"}, 19, 30, 22, 0);

    }
}