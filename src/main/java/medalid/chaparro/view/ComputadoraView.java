package medalid.chaparro.view;

import medalid.chaparro.controller.ComputadoraController;
import medalid.chaparro.model.Computadora;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.util.List;

public class ComputadoraView extends JFrame {

    private JTextField txtMarca, txtModelo, txtSO, txtRAM, txtAlmacenamiento, txtFechaMant, txtFechaReg;
    private JButton btnRegistrar, btnActualizar, btnEliminar, btnRefrescar;
    private JTable table;
    private DefaultTableModel model;

    // NUEVOS COMPONENTES OBLIGATORIOS
    private JComboBox<String> cboTipo;
    private JRadioButton rbActivo, rbInactivo;
    private ButtonGroup grupoEstado;
    private JCheckBox chkGarantia;

    private ComputadoraController controller = new ComputadoraController();

    public ComputadoraView() {
        setTitle("Inventario de Computadoras - Hackathon 251-S2");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel Formulario
        JPanel panelForm = new JPanel(new GridLayout(13, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Formulario de Computadora"));

        // --- NUEVO: ComboBox para tipo ---
        panelForm.add(new JLabel("Tipo de Equipo:"));
        cboTipo = new JComboBox<>(new String[]{"Laptop", "Desktop", "All-in-One", "Servidor"});
        panelForm.add(cboTipo);

        txtMarca = new JTextField();
        txtModelo = new JTextField();
        txtSO = new JTextField();
        txtRAM = new JTextField();
        txtAlmacenamiento = new JTextField();
        txtFechaMant = new JTextField();
        txtFechaReg = new JTextField();

        panelForm.add(new JLabel("Marca:")); panelForm.add(txtMarca);
        panelForm.add(new JLabel("Modelo:")); panelForm.add(txtModelo);
        panelForm.add(new JLabel("Sistema Operativo:")); panelForm.add(txtSO);
        panelForm.add(new JLabel("RAM (GB):")); panelForm.add(txtRAM);
        panelForm.add(new JLabel("Almacenamiento (GB):")); panelForm.add(txtAlmacenamiento);
        panelForm.add(new JLabel("Fecha Mantenimiento (YYYY-MM-DD):")); panelForm.add(txtFechaMant);
        panelForm.add(new JLabel("Fecha Registro (YYYY-MM-DD):")); panelForm.add(txtFechaReg);

        // --- NUEVO: RadioButtons para Estado ---
        panelForm.add(new JLabel("Estado:"));
        JPanel panelEstado = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rbActivo = new JRadioButton("Activo", true);
        rbInactivo = new JRadioButton("Inactivo");

        grupoEstado = new ButtonGroup();
        grupoEstado.add(rbActivo);
        grupoEstado.add(rbInactivo);

        panelEstado.add(rbActivo);
        panelEstado.add(rbInactivo);
        panelForm.add(panelEstado);

        // --- NUEVO: CheckBox Garantía ---
        panelForm.add(new JLabel("Garantía:"));
        chkGarantia = new JCheckBox("Tiene garantía");
        panelForm.add(chkGarantia);

        // Botones
        btnRegistrar = new JButton("Registrar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnRefrescar = new JButton("Refrescar Tabla");

        panelForm.add(btnRegistrar);
        panelForm.add(btnActualizar);
        panelForm.add(btnEliminar);
        panelForm.add(btnRefrescar);

        // Tabla
        model = new DefaultTableModel();
        table = new JTable(model);
        model.setColumnIdentifiers(new Object[]{
                "ID", "Tipo", "Marca", "Modelo", "SO", "RAM",
                "Almacenamiento", "Fecha Mant", "Fecha Reg", "Estado", "Garantía"
        });
        JScrollPane scroll = new JScrollPane(table);

        add(panelForm, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        cargarTabla();

        // Acciones botones
        btnRegistrar.addActionListener(e -> registrar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());
        btnRefrescar.addActionListener(e -> cargarTabla());
    }

    private void registrar() {
        try {
            // Validar campos vacíos
            if (txtMarca.getText().trim().isEmpty() ||
                    txtModelo.getText().trim().isEmpty() ||
                    txtSO.getText().trim().isEmpty() ||
                    txtRAM.getText().trim().isEmpty() ||
                    txtAlmacenamiento.getText().trim().isEmpty() ||
                    txtFechaMant.getText().trim().isEmpty() ||
                    txtFechaReg.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Todos los campos son obligatorios",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Computadora c = new Computadora();
            c.setTipoEquipo(cboTipo.getSelectedItem().toString());
            c.setMarca(txtMarca.getText().trim());
            c.setModelo(txtModelo.getText().trim());
            c.setSistemaOperativo(txtSO.getText().trim());

            // Validar números
            int ram, almacenamiento;
            try {
                ram = Integer.parseInt(txtRAM.getText().trim());
                almacenamiento = Integer.parseInt(txtAlmacenamiento.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Por favor ingresa valores numéricos válidos en RAM y Almacenamiento",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            c.setRam(ram);
            c.setAlmacenamiento(almacenamiento);

            // Validar fechas
            try {
                c.setFechaMantenimiento(Date.valueOf(txtFechaMant.getText().trim()));
                c.setFechaRegistro(Date.valueOf(txtFechaReg.getText().trim()));
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this,
                        "Por favor ingresa fechas válidas en formato YYYY-MM-DD",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Nuevo: estado y garantía
            c.setEstado(rbActivo.isSelected() ? "activo" : "inactivo");
            c.setGarantia(chkGarantia.isSelected() ? "sí" : "no");

            if (controller.registrar(c)) {
                JOptionPane.showMessageDialog(this, "✅ Computadora registrada correctamente");
                limpiarFormulario();
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error al registrar");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Error inesperado: " + ex.getMessage());
        }
    }

    private void actualizar() {
        try {
            int fila = table.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una fila");
                return;
            }

            Computadora c = new Computadora();
            c.setId((int) model.getValueAt(fila, 0));
            c.setTipoEquipo(cboTipo.getSelectedItem().toString());
            c.setMarca(txtMarca.getText().trim());
            c.setModelo(txtModelo.getText().trim());
            c.setSistemaOperativo(txtSO.getText().trim());

            // Validar números
            int ram, almacenamiento;
            try {
                ram = Integer.parseInt(txtRAM.getText().trim());
                almacenamiento = Integer.parseInt(txtAlmacenamiento.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Numeros inválidos en RAM o almacenamiento");
                return;
            }
            c.setRam(ram);
            c.setAlmacenamiento(almacenamiento);

            // Validar fechas
            try {
                c.setFechaMantenimiento(Date.valueOf(txtFechaMant.getText().trim()));
                c.setFechaRegistro(Date.valueOf(txtFechaReg.getText().trim()));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error en formato de fecha");
                return;
            }

            // Nuevo
            c.setEstado(rbActivo.isSelected() ? "activo" : "inactivo");
            c.setGarantia(chkGarantia.isSelected() ? "sí" : "no");

            if (controller.actualizar(c)) {
                JOptionPane.showMessageDialog(this, "✅ Computadora actualizada");
                limpiarFormulario();
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error al actualizar");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Error inesperado: " + ex.getMessage());
        }
    }

    private void eliminar() {
        try {
            int fila = table.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una fila");
                return;
            }

            int id = (int) model.getValueAt(fila, 0);
            if (controller.eliminar(id)) {
                JOptionPane.showMessageDialog(this, "✅ Eliminado lógicamente");
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error al eliminar");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
        }
    }

    private void cargarTabla() {
        model.setRowCount(0);
        List<Computadora> lista = controller.listar();
        for (Computadora c : lista) {
            model.addRow(new Object[]{
                    c.getId(), c.getTipoEquipo(), c.getMarca(), c.getModelo(),
                    c.getSistemaOperativo(), c.getRam(), c.getAlmacenamiento(),
                    c.getFechaMantenimiento(), c.getFechaRegistro(),
                    c.getEstado(), c.getGarantia()
            });
        }
    }

    private void limpiarFormulario() {
        cboTipo.setSelectedIndex(0);
        txtMarca.setText("");
        txtModelo.setText("");
        txtSO.setText("");
        txtRAM.setText("");
        txtAlmacenamiento.setText("");
        txtFechaMant.setText("");
        txtFechaReg.setText("");
        rbActivo.setSelected(true);
        chkGarantia.setSelected(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ComputadoraView().setVisible(true));
    }
}