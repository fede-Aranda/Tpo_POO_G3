package interfaces;
import clases.Evento;
import clases.GestorDeReservas;

import javax.swing.*;
import java.awt.*;

public class DetalleEvento extends JFrame {
    private JLabel lblTitulo;
    private JLabel lblUbicacion;
    private JLabel lblDescripcion;
    private JLabel lblIntegrantes;
    private JLabel lblFecha;
    private JLabel lblHoraInicio;
    private JLabel lblHoraFin;

    private JButton btnEditarEvento;
    private JButton btnVolver;

    private Evento evento;
    private GestorDeReservas gestor;

    public DetalleEvento(Evento evento, GestorDeReservas gestor) {
        this.evento = evento;
        this.gestor = gestor;

        setTitle("Detalle del Evento");
        setSize(500, 400);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Crear etiquetas
        lblTitulo = new JLabel();
        lblUbicacion = new JLabel();
        lblDescripcion = new JLabel();
        lblIntegrantes = new JLabel();
        lblFecha = new JLabel();
        lblHoraInicio = new JLabel();
        lblHoraFin = new JLabel();

        // Mostrar los datos en dos columnas
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Título:"), gbc);
        gbc.gridx = 1;
        add(lblTitulo, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Ubicación:"), gbc);
        gbc.gridx = 1;
        add(lblUbicacion, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1;
        add(lblDescripcion, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Integrantes:"), gbc);
        gbc.gridx = 1;
        add(lblIntegrantes, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Fecha:"), gbc);
        gbc.gridx = 1;
        add(lblFecha, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Hora de inicio:"), gbc);
        gbc.gridx = 1;
        add(lblHoraInicio, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Duración:"), gbc);
        gbc.gridx = 1;
        add(lblHoraFin, gbc);

        // Crear botones con estilo redondeado
        btnEditarEvento = crearBotonConHover("Editar Evento", Color.GRAY);
        btnVolver = crearBotonConHover("Volver", Color.GRAY);

        // Añadir botones uno al lado del otro
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(btnEditarEvento);
        buttonPanel.add(btnVolver);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);

        // Acciones de los botones
        btnEditarEvento.addActionListener(e -> abrirFormularioEdicion());
        btnVolver.addActionListener(e -> dispose());

        // Actualizar detalles iniciales
        actualizarDetalles();
    }

    private JButton crearBotonConHover(String texto, Color colorHover) {
        JButton boton = new JButton(texto);

        // Estilo del botón
        boton.setPreferredSize(new Dimension(130, 30)); // Tamaño más grande
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        return boton;
    }

    private void abrirFormularioEdicion() {
        FormularioEvento formularioEvento = new FormularioEvento(evento, gestor);
        formularioEvento.setVisible(true);

        actualizarDetalles();
    }

    private void actualizarDetalles() {
        lblTitulo.setText(evento.getTitulo());
        lblUbicacion.setText(evento.getUbicacion());
        lblDescripcion.setText(evento.getDescripcion());
        lblIntegrantes.setText(String.join(", ", evento.getIntegrantes()));
        lblFecha.setText(evento.getFecha().toStringFecha());
        lblHoraInicio.setText(formatearHora(evento.getHoraInicio()));
        lblHoraFin.setText(formatearDuracion(evento.getDuracion()));
    }

    private String formatearHora(int bloques) {
        int horas = bloques / 4;
        int minutos = (bloques % 4) * 15;
        return String.format("%02d:%02d" + "h", horas, minutos);
    }

    private String formatearDuracion(int bloques) {
        int horas = bloques / 4;
        int minutos = (bloques % 4) * 15;
        return String.format("%d h %d min", horas, minutos);
    }
}
