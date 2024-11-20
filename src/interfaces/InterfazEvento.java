package interfaces;
import clases.GestorDeReservas;
import clases.Evento;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfazEvento extends JFrame {
    private GestorDeReservas gestor;
    private JList<Evento> listaEventos;
    private DefaultListModel<Evento> modeloListaEventos;
    private JButton btnAgregarEvento;
    private JButton btnVerDetalle;

    public InterfazEvento() {
        this.gestor = new GestorDeReservas();

        setTitle("Gestor de Eventos");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        modeloListaEventos = new DefaultListModel<>();
        actualizarListaEventos();

        listaEventos = new JList<>(modeloListaEventos);
        listaEventos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaEventos.setFont(new Font("Arial", Font.PLAIN, 14));
        listaEventos.setFixedCellHeight(30);
        listaEventos.setFixedCellWidth(300);

        JScrollPane scrollPane = new JScrollPane(listaEventos);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Eventos Disponibles"));

        btnAgregarEvento = new JButton("Agregar Evento");
        btnAgregarEvento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirFormularioNuevoEvento();
            }
        });
        btnAgregarEvento.setPreferredSize(new Dimension(130, 30)); // Tamaño más grande
        btnAgregarEvento.setFocusPainted(false);
        btnAgregarEvento.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
        btnAgregarEvento.setFont(new Font("Arial", Font.BOLD, 14));
        btnAgregarEvento.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnVerDetalle = new JButton("Ver Detalle");
        btnVerDetalle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirDetalleEvento();
            }
        });
        btnVerDetalle.setPreferredSize(new Dimension(130, 30)); // Tamaño más grande
        btnVerDetalle.setFocusPainted(false);
        btnVerDetalle.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
        btnVerDetalle.setFont(new Font("Arial", Font.BOLD, 14));
        btnVerDetalle.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());
        panelBotones.add(btnAgregarEvento);
        panelBotones.add(btnVerDetalle);

        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void actualizarListaEventos() {
        modeloListaEventos.clear(); // Limpia el modelo antes de agregar elementos nuevos
        for (Evento evento : gestor.eventos) {
            if (evento != null) { // Validar que el evento no sea nulo antes de agregarlo
                modeloListaEventos.addElement(evento);
            }
        }
    }

    private void abrirFormularioNuevoEvento() {
        FormularioEvento formulario = new FormularioEvento(null, gestor); // Nuevo evento
        formulario.setVisible(true);
        formulario.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                actualizarListaEventos();
            }
        });
    }

    private void abrirDetalleEvento() {
        Evento eventoSeleccionado = listaEventos.getSelectedValue();
        if (eventoSeleccionado != null) { // Validación de selección
            DetalleEvento detalle = new DetalleEvento(eventoSeleccionado, gestor);
            detalle.setVisible(true);
            detalle.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    actualizarListaEventos();
                }
            });
        } else {
            // Mensaje de error si no se seleccionó nada
            JOptionPane.showMessageDialog(this, "Seleccione un evento para ver el detalle.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
