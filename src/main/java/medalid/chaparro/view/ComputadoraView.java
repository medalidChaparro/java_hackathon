package medalid.chaparro.view;

import medalid.chaparro.controller.ComputadoraController;
import medalid.chaparro.model.Computadora;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.util.List;

/**
 * Vista principal del sistema (Swing)
 *
 * Esta clase representa toda la interfaz gráfica:
 * - Formulario para registrar y editar computadoras
 * - Tabla para mostrar los registros
 * - Botones para ejecutar acciones CRUD
 *
 * La vista NO maneja lógica de negocio ni conexión a base de datos.
 * Solo interactúa con el Controller.
 */
public class ComputadoraView extends JFrame {

    // Campos de texto (inputs)
    private JTextField txtMarca, txtModelo, txtSO, txtRAM, txtAlmacenamiento, txtFechaMant, txtFechaReg;

    // Nuevos componentes personalizados
    private JComboBox<String> cboTipo;        // ComboBox para tipo de equipo
    private JRadioButton rbActivo, rbInactivo; // Estado
    private ButtonGroup grupoEstado;
    private JCheckBox chkGarantia;           // Nuevo campo: garantía

    // Botones CRUD
    private JButton btnRegistrar, btnActualizar, btnEliminar, btnRefrescar;

    // Componentes de tabla
    private JTable table;
    private DefaultTableModel model;

    // Controller (intermediario con Service y DAO)
    private ComputadoraController controller = new ComputadoraController();


    // ========================
    //      CONSTRUCTOR
    // ========================
    public ComputadoraView() {

        setTitle("Inventario de Computadoras - Hackathon 251-S2");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ---------------------------
        // PANEL DEL FORMULARIO
        // ---------------------------
        JPanel panelForm = new JPanel(new GridLayout(13, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Formulario de Computadora"));

        // ComboBox para tipo de equipo
        panelForm.add(new JLabel("Tipo de Equipo:"));
        cboTipo = new JComboBox<>(new String[]{"Laptop", "Desktop", "All-in-One", "Servidor"});
        panelForm.add(cboTipo);

        // Campos de texto
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

        // ---------------------------
        // RadioButtons: Estado
        // ---------------------------
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

        // ---------------------------
        // CheckBox: Garantía
        // ---------------------------
        panelForm.add(new JLabel("Garantía:"));
        chkGarantia = new JCheckBox("Tiene garantía");
        panelForm.add(chkGarantia);

        // ---------------------------
        // Botones CRUD
        // ---------------------------
        btnRegistrar = new JButton("Registrar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnRefrescar = new JButton("Refrescar Tabla");

        panelForm.add(btnRegistrar);
        panelForm.add(btnActualizar);
        panelForm.add(btnEliminar);
        panelForm.add(btnRefrescar);

        // ---------------------------
        // TABLA
        // ---------------------------
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

        // Eventos
        btnRegistrar.addActionListener(e -> registrar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());
        btnRefrescar.addActionListener(e -> cargarTabla());
    }


    // ====================================================
    // MÉTODO: Registrar computadora
    // ====================================================
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

            // Crear objeto
            Computadora c = new Computadora();
            c.setTipoEquipo(cboTipo.getSelectedItem().toString());
            c.setMarca(txtMarca.getText().trim());
            c.setModelo(txtModelo.getText().trim());
            c.setSistemaOperativo(txtSO.getText().trim());

            // Validar números
            try {
                c.setRam(Integer.parseInt(txtRAM.getText().trim()));
                c.setAlmacenamiento(Integer.parseInt(txtAlmacenamiento.getText().trim()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "RAM y Almacenamiento deben ser números válidos",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar fechas
            try {
                c.setFechaMantenimiento(Date.valueOf(txtFechaMant.getText().trim()));
                c.setFechaRegistro(Date.valueOf(txtFechaReg.getText().trim()));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Formato de fecha incorrecto (YYYY-MM-DD)",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Estado y garantía
            c.setEstado(rbActivo.isSelected() ? "activo" : "inactivo");
            c.setGarantia(chkGarantia.isSelected() ? "sí" : "no");

            // Registrar
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


    // ====================================================
    // MÉTODO: Actualizar computadora
    // ====================================================
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

            // Números
            try {
                c.setRam(Integer.parseInt(txtRAM.getText().trim()));
                c.setAlmacenamiento(Integer.parseInt(txtAlmacenamiento.getText().trim()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Números inválidos");
                return;
            }

            // Fechas
            try {
                c.setFechaMantenimiento(Date.valueOf(txtFechaMant.getText().trim()));
                c.setFechaRegistro(Date.valueOf(txtFechaReg.getText().trim()));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Fecha inválida");
                return;
            }

            // Estado y garantía (nuevo)
            c.setEstado(rbActivo.isSelected() ? "activo" : "inactivo");
            c.setGarantia(chkGarantia.isSelected() ? "sí" : "no");

            // Actualizar
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


    // ====================================================
    // MÉTODO: Eliminar (lógico)
    // ====================================================
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


    // ====================================================
    // Cargar registros en la tabla
    // ====================================================
    private void cargarTabla() {
        model.setRowCount(0); // Limpiar tabla

        List<Computadora> lista = controller.listar();

        for (Computadora c : lista) {
            model.addRow(new Object[]{
                    c.getId(),
                    c.getTipoEquipo(),
                    c.getMarca(),
                    c.getModelo(),
                    c.getSistemaOperativo(),
                    c.getRam(),
                    c.getAlmacenamiento(),
                    c.getFechaMantenimiento(),
                    c.getFechaRegistro(),
                    c.getEstado(),
                    c.getGarantia()
            });
        }
    }


    // ====================================================
    // Limpiar formulario
    // ====================================================
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


    // ====================================================
    // MAIN
    // ====================================================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ComputadoraView().setVisible(true));
    }
}