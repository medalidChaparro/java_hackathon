package medalid.chaparro.view;

import medalid.chaparro.controller.ComputadoraController;
import medalid.chaparro.model.Computadora;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.util.List;

public class ComputadoraView extends JFrame {

    private JTextField txtTipo, txtMarca, txtModelo, txtSO, txtRAM, txtAlmacenamiento, txtFechaMant, txtFechaReg;
    private JButton btnRegistrar, btnActualizar, btnEliminar, btnRefrescar;
    private JTable table;
    private DefaultTableModel model;

    private ComputadoraController controller = new ComputadoraController();

    public ComputadoraView() {
        setTitle("Inventario de Computadoras - Hackathon 251-S2");
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel Formulario
        JPanel panelForm = new JPanel(new GridLayout(10, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Formulario de Computadora"));

        txtTipo = new JTextField();
        txtMarca = new JTextField();
        txtModelo = new JTextField();
        txtSO = new JTextField();
        txtRAM = new JTextField();
        txtAlmacenamiento = new JTextField();
        txtFechaMant = new JTextField();
        txtFechaReg = new JTextField();

        panelForm.add(new JLabel("Tipo de Equipo:")); panelForm.add(txtTipo);
        panelForm.add(new JLabel("Marca:")); panelForm.add(txtMarca);
        panelForm.add(new JLabel("Modelo:")); panelForm.add(txtModelo);
        panelForm.add(new JLabel("Sistema Operativo:")); panelForm.add(txtSO);
        panelForm.add(new JLabel("RAM (GB):")); panelForm.add(txtRAM);
        panelForm.add(new JLabel("Almacenamiento (GB):")); panelForm.add(txtAlmacenamiento);
        panelForm.add(new JLabel("Fecha Mantenimiento (YYYY-MM-DD):")); panelForm.add(txtFechaMant);
        panelForm.add(new JLabel("Fecha Registro (YYYY-MM-DD):")); panelForm.add(txtFechaReg);

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
        model.setColumnIdentifiers(new Object[]{"ID", "Tipo", "Marca", "Modelo", "SO", "RAM", "Almacenamiento", "Fecha Mant", "Fecha Reg", "Estado"});
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
            if (txtTipo.getText().trim().isEmpty() ||
                    txtMarca.getText().trim().isEmpty() ||
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
            c.setTipoEquipo(txtTipo.getText().trim());
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

            c.setEstado("activo");

            // Intentar registrar
            if (controller.registrar(c)) {
                JOptionPane.showMessageDialog(this, "✅ Computadora registrada correctamente");
                limpiarFormulario();
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error al registrar. Revisa la conexión con la base de datos.");
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
            c.setTipoEquipo(txtTipo.getText().trim());
            c.setMarca(txtMarca.getText().trim());
            c.setModelo(txtModelo.getText().trim());
            c.setSistemaOperativo(txtSO.getText().trim());

            // Validar números
            int ram;
            int almacenamiento;
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
                        "Por favor ingresa fechas en formato YYYY-MM-DD",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            c.setEstado("activo");

            if (controller.actualizar(c)) {
                JOptionPane.showMessageDialog(this, "✅ Computadora actualizada correctamente");
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
                JOptionPane.showMessageDialog(this, "✅ Computadora eliminada (lógico)");
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
                    c.getId(), c.getTipoEquipo(), c.getMarca(), c.getModelo(), c.getSistemaOperativo(),
                    c.getRam(), c.getAlmacenamiento(), c.getFechaMantenimiento(), c.getFechaRegistro(), c.getEstado()
            });
        }
    }

    private void limpiarFormulario() {
        txtTipo.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
        txtSO.setText("");
        txtRAM.setText("");
        txtAlmacenamiento.setText("");
        txtFechaMant.setText("");
        txtFechaReg.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ComputadoraView().setVisible(true));
    }
}