package interfaces;
import clases.Evento;
import clases.Fecha;
import clases.GestorDeReservas;

import javax.swing.*;
import java.awt.*;

public class FormularioEvento extends JDialog {
    private JTextField campoTitulo;
    private JTextField campoUbicacion;
    private JTextField campoDescripcion;
    private JTextField campoIntegrantes;
    private JSpinner spinnerDia;
    private JSpinner spinnerMes;
    private JSpinner spinnerAnio;
    private JSpinner spinnerHoraInicio;
    private JSpinner spinnerMinutoInicio;
    private JSpinner spinnerHoraFin;
    private JSpinner spinnerMinutoFin;

    private JButton guardarBtn;
    private JButton cancelarBtn;

    private Evento eventoExistente; // Si se pasa, es para edición
    private GestorDeReservas gestor; // Para gestionar eventos

    public FormularioEvento(Evento evento, GestorDeReservas gestor) {
        this.eventoExistente = evento;
        this.gestor = gestor;

        setTitle(eventoExistente == null ? "Crear clases.Evento" : "Editar clases.Evento");
        setSize(400, 500);
        setModal(true);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel formularioPanel = new JPanel(new GridBagLayout()); // Cambiado a GridBagLayout
        formularioPanel.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos de formulario
        agregarCampoFormulario(formularioPanel, gbc, "Título:", campoTitulo = new JTextField(), 0);
        agregarCampoFormulario(formularioPanel, gbc, "Ubicación:", campoUbicacion = new JTextField(), 1);

        // Usando JTextArea para descripción

        agregarCampoFormulario(formularioPanel, gbc, "Descripción:",campoDescripcion = new JTextField(), 2);

        agregarCampoFormulario(formularioPanel, gbc, "Integrantes (separados por /):", campoIntegrantes = new JTextField(), 3);

        // Spinners para fecha y hora
        agregarCampoFormulario(formularioPanel, gbc, "Día:", spinnerDia = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1)), 4);
        agregarCampoFormulario(formularioPanel, gbc, "clases.Mes:", spinnerMes = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1)), 5);
        agregarCampoFormulario(formularioPanel, gbc, "Año:", spinnerAnio = new JSpinner(new SpinnerNumberModel(2023, 1900, 2100, 1)), 6);
        agregarCampoFormulario(formularioPanel, gbc, "Hora Inicio:", spinnerHoraInicio = new JSpinner(new SpinnerNumberModel(0, 0, 23, 1)), 7);
        agregarCampoFormulario(formularioPanel, gbc, "Minuto Inicio:", spinnerMinutoInicio = new JSpinner(new SpinnerNumberModel(0, 0, 45, 15)), 8);
        agregarCampoFormulario(formularioPanel, gbc, "Hora Fin:", spinnerHoraFin = new JSpinner(new SpinnerNumberModel(0, 0, 23, 1)), 9);
        agregarCampoFormulario(formularioPanel, gbc, "Minuto Fin:", spinnerMinutoFin = new JSpinner(new SpinnerNumberModel(0, 0, 45, 15)), 10);

        add(formularioPanel, BorderLayout.CENTER);

        // Panel de botones
        JPanel botonesPanel = new JPanel();
        guardarBtn = new JButton("Guardar");
        cancelarBtn = new JButton("Cancelar");

        botonesPanel.add(guardarBtn);
        botonesPanel.add(cancelarBtn);

        add(botonesPanel, BorderLayout.SOUTH);
        botonesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        guardarBtn.setPreferredSize(new Dimension(130, 30)); // Tamaño más grande
        guardarBtn.setFocusPainted(false);
        guardarBtn.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
        guardarBtn.setFont(new Font("Arial", Font.BOLD, 14));

        cancelarBtn.setPreferredSize(new Dimension(130, 30)); // Tamaño más grande
        cancelarBtn.setFocusPainted(false);
        cancelarBtn.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
        cancelarBtn.setFont(new Font("Arial", Font.BOLD, 14));


        // Listener para guardar
        guardarBtn.addActionListener(e -> guardarEvento());

        // Listener para cancelar
        cancelarBtn.addActionListener(e -> dispose());
    }

    private void guardarEvento() {
        try {
            String titulo = campoTitulo.getText();
            String ubicacion = campoUbicacion.getText();
            String descripcion = campoDescripcion.getText();
            String[] integrantes = campoIntegrantes.getText().split("/");

            int dia = (int) spinnerDia.getValue();
            int mes = (int) spinnerMes.getValue();
            int anio = (int) spinnerAnio.getValue();
            Fecha fecha = new Fecha(dia, mes, anio);

            int horaInicio = (int) spinnerHoraInicio.getValue();
            int minutoInicio = (int) spinnerMinutoInicio.getValue();
            int horaInicioBloques = horaInicio * 4 + (minutoInicio / 15);

            int horaFin = (int) spinnerHoraFin.getValue();
            int minutoFin = (int) spinnerMinutoFin.getValue();
            int horaFinBloques = horaFin * 4 + (minutoFin / 15);

            if (horaFinBloques < horaInicioBloques) {
                JOptionPane.showMessageDialog(this, "La hora de fin debe ser mayor o igual a la hora de inicio.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int duracionBloques = horaFinBloques - horaInicioBloques;

            if (duracionBloques <= 0) {
                JOptionPane.showMessageDialog(this, "La duración debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }


            if (eventoExistente == null) {
                // Crear nuevo evento
                gestor.generarEvento(
                        titulo, dia, mes, anio, ubicacion, descripcion,
                        integrantes, horaInicio, minutoInicio, horaFin, minutoFin
                );
            } else {
                // Editar evento existente
                gestor.editarEvento(
                        eventoExistente, titulo, dia, mes, anio,
                        ubicacion, descripcion, integrantes,
                        horaInicio, minutoInicio, horaFin, minutoFin
                );
            }

            dispose(); // Cerrar el formulario después de guardar
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar el evento: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void agregarCampoFormulario(JPanel panel, GridBagConstraints gbc, String etiqueta, JComponent campo, int fila) {
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(new JLabel(etiqueta), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(campo, gbc);

        if (campo instanceof JTextField || campo instanceof JTextArea) {
            campo.setPreferredSize(new Dimension(200, 30));
        }
    }
}
