package View;

import DaoImp.PedidoDaoImp;
import Model.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class PedidoView extends JPanel {

    public static class PedidoRegistro {
        public final String codigo;
        public final String cliente;
        public final int mesa;
        public final String tipo;
        public final String detalle;
        public final double subtotal;
        public final double impuesto;
        public final double total;
        public String estado;
        public final LocalDateTime fecha;

        public PedidoRegistro(String codigo, String cliente, int mesa, String tipo, String detalle,
                              double subtotal, String estado, LocalDateTime fecha) {
            this.codigo = codigo;
            this.cliente = cliente;
            this.mesa = mesa;
            this.tipo = tipo;
            this.detalle = detalle;
            this.subtotal = subtotal;
            this.impuesto = Math.round(subtotal * 0.13);
            this.total = subtotal + this.impuesto;
            this.estado = estado;
            this.fecha = fecha;
        }
    }

    private static final List<PedidoRegistro> PEDIDOS = new ArrayList<>();
    private static final List<Runnable> LISTENERS = new ArrayList<>();

    static {
        LocalDateTime ahora = LocalDateTime.now();
      
    }

    private final DefaultTableModel model;
    private final JTextField txtCodigo;
    private final JTextField txtCliente;
    private final JSpinner spnMesa;
    private final JComboBox<String> cmbTipo;
    private final JTextField txtDetalle;
    private final JTextField txtSubtotal;

    public PedidoView() {
        setBackground(new Color(244, 246, 250));
        setBorder(BorderFactory.createEmptyBorder(28, 32, 28, 32));
        setLayout(new BorderLayout(0, 18));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel title = new JLabel("Pedidos");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(new Color(23, 28, 38));
        JLabel sub = new JLabel("Registro general de pedidos del restaurante (sincronizado con Caja y Cocina)");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 14));
        sub.setForeground(new Color(100, 110, 128));
        header.add(title, BorderLayout.NORTH);
        header.add(sub, BorderLayout.SOUTH);
        add(header, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(0, 18));
        center.setOpaque(false);

        // Cuadro superior extendido verticalmente para agregar un nuevo pedido cómodamente
        JPanel formCard = new JPanel(new GridBagLayout());
        formCard.setBackground(Color.WHITE);
        formCard.setPreferredSize(new Dimension(0, 195));
        formCard.setMinimumSize(new Dimension(0, 195));
        formCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 216, 226), 1, false),
                BorderFactory.createEmptyBorder(18, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 8, 7, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel formTitle = new JLabel("Agregar Nuevo Pedido");
        formTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        formTitle.setForeground(new Color(23, 28, 38));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 6;
        formCard.add(formTitle, gbc);
        gbc.gridwidth = 1;

        txtCodigo = crearCampo("PED-60101", 130);
        txtCliente = crearCampo("Cliente Salón", 170);
        spnMesa = new JSpinner(new SpinnerNumberModel(3, 0, 24, 1));
        spnMesa.setPreferredSize(new Dimension(85, 36));
        spnMesa.setMinimumSize(new Dimension(85, 36));
        spnMesa.setBorder(BorderFactory.createLineBorder(new Color(195, 203, 214), 1, false));
        ((JSpinner.DefaultEditor) spnMesa.getEditor()).getTextField().setFont(new Font("SansSerif", Font.PLAIN, 13));

        cmbTipo = new JComboBox<>(new String[]{"En restaurante", "Para llevar", "Express"});
        cmbTipo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        cmbTipo.setBackground(Color.WHITE);
        cmbTipo.setPreferredSize(new Dimension(145, 36));
        cmbTipo.setBorder(BorderFactory.createLineBorder(new Color(195, 203, 214), 1, false));

        txtDetalle = crearCampo("1 Pizza Suprema, 2 Refrescos", 230);
        txtSubtotal = crearCampo("9500", 120);

        // Fila 1 de inputs
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.weightx = 0;
        formCard.add(crearLabel("N° Pedido:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.25;
        formCard.add(txtCodigo, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        formCard.add(crearLabel("Cliente:"), gbc);
        gbc.gridx = 3;
        gbc.weightx = 0.35;
        formCard.add(txtCliente, gbc);

        gbc.gridx = 4;
        gbc.weightx = 0;
        formCard.add(crearLabel("Mesa (0=Llevar):"), gbc);
        gbc.gridx = 5;
        gbc.weightx = 0.15;
        formCard.add(spnMesa, gbc);

        // Fila 2 de inputs
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.weightx = 0;
        formCard.add(crearLabel("Tipo:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.25;
        formCard.add(cmbTipo, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        formCard.add(crearLabel("Qué pidió:"), gbc);
        gbc.gridx = 3;
        gbc.weightx = 0.45;
        formCard.add(txtDetalle, gbc);

        gbc.gridx = 4;
        gbc.weightx = 0;
        formCard.add(crearLabel("Subtotal (₡):"), gbc);
        gbc.gridx = 5;
        gbc.weightx = 0.25;
        formCard.add(txtSubtotal, gbc);

        // Fila 3 con botón de guardar pedido
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 6;
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 2));
        btnPanel.setOpaque(false);

        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnLimpiar.setBackground(new Color(238, 242, 247));
        btnLimpiar.setForeground(new Color(23, 28, 38));
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 206, 216), 1, false),
                BorderFactory.createEmptyBorder(9, 16, 9, 16)
        ));
        btnLimpiar.addActionListener(e -> {
            txtCodigo.setText("");
            txtCliente.setText("");
            spnMesa.setValue(0);
            txtDetalle.setText("");
            txtSubtotal.setText("");
            txtCodigo.requestFocus();
        });

        JButton btnGuardar = new JButton("+ Guardar Pedido");
        btnGuardar.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnGuardar.setBackground(new Color(37, 99, 235));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btnGuardar.addActionListener(e -> guardarNuevoPedido());

        btnPanel.add(btnLimpiar);
        btnPanel.add(btnGuardar);
        formCard.add(btnPanel, gbc);

        center.add(formCard, BorderLayout.NORTH);

        // Tabla de pedidos guardados
        JPanel tableCard = new JPanel(new BorderLayout(0, 10));
        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 216, 226), 1, false),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));

        JLabel tblTitle = new JLabel("Pedidos Guardados");
        tblTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        tblTitle.setForeground(new Color(23, 28, 38));
        tableCard.add(tblTitle, BorderLayout.NORTH);

        String[] cols = {"Pedido", "Cliente", "Mesa", "Tipo", "Detalle del Pedido", "Total (IVA inc.)", "Estado"};
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(36);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        table.setBackground(Color.WHITE);
        table.setGridColor(new Color(224, 229, 236));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(224, 229, 236), 1, false));
        scroll.getViewport().setBackground(Color.WHITE);
        tableCard.add(scroll, BorderLayout.CENTER);

        center.add(tableCard, BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        addPedidoListener(this::refrescar);
        refrescar();
    }

    private JLabel crearLabel(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("SansSerif", Font.BOLD, 13));
        l.setForeground(new Color(23, 28, 38));
        return l;
    }

    private JTextField crearCampo(String valorInicial, int minWidth) {
        JTextField tf = new JTextField(valorInicial);
        tf.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tf.setBackground(Color.WHITE);
        tf.setForeground(new Color(23, 28, 38));
        tf.setEditable(true);
        tf.setEnabled(true);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(195, 203, 214), 1, false),
                BorderFactory.createEmptyBorder(7, 10, 7, 10)
        ));
        Dimension d = new Dimension(minWidth, 36);
        tf.setMinimumSize(d);
        tf.setPreferredSize(d);
        return tf;
    }

    private void guardarNuevoPedido() {
        String cod = txtCodigo.getText().trim();
        String cli = txtCliente.getText().trim();
        int mesa = (Integer) spnMesa.getValue();
        String tipo = (String) cmbTipo.getSelectedItem();
        String det = txtDetalle.getText().trim();
        double sub = 0;
        try {
            String clean = txtSubtotal.getText().trim().replace("₡", "").replace(",", "").replace(" ", "");
            if (!clean.isEmpty()) sub = Double.parseDouble(clean);
        } catch (NumberFormatException ignored) {
        }

        if (cod.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa el número de pedido.");
            txtCodigo.requestFocus();
            return;
        }
        if (det.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa el detalle de lo que pidió el cliente.");
            txtDetalle.requestFocus();
            return;
        }
        if (sub <= 0) {
            JOptionPane.showMessageDialog(this, "Ingresa un subtotal válido mayor a 0.");
            txtSubtotal.requestFocus();
            return;
        }

        PedidoRegistro nuevo = new PedidoRegistro(
                cod,
                cli.isEmpty() ? "Cliente General" : cli,
                mesa,
                tipo != null ? tipo : (mesa > 0 ? "En restaurante" : "Para llevar"),
                det,
                sub,
                "Recibido",
                LocalDateTime.now()
        );
        registrarPedido(nuevo);

        try {
            Pedido pModel = new Pedido(0, 1, 1, Math.max(1, mesa), "Recibido", nuevo.total, LocalDateTime.now());
            new PedidoDaoImp().insertar(pModel);
        } catch (RuntimeException ignored) {
        }

        JOptionPane.showMessageDialog(this, "Pedido " + cod + " guardado y enviado a Cocina.");
        txtCodigo.setText("PED-" + (60101 + PEDIDOS.size()));
    }

    public static void addPedidoListener(Runnable r) {
        if (r != null && !LISTENERS.contains(r)) {
            LISTENERS.add(r);
        }
    }

    public static void notifyPedidoListeners() {
        for (Runnable r : new ArrayList<>(LISTENERS)) {
            r.run();
        }
    }

    public static List<PedidoRegistro> getPedidos() {
        return Collections.unmodifiableList(PEDIDOS);
    }

    public static void registrarPedido(PedidoRegistro pedido) {
        if (pedido == null) return;
        for (int i = 0; i < PEDIDOS.size(); i++) {
            if (PEDIDOS.get(i).codigo.equalsIgnoreCase(pedido.codigo)) {
                PEDIDOS.set(i, pedido);
                notifyPedidoListeners();
                return;
            }
        }
        PEDIDOS.add(0, pedido);
        notifyPedidoListeners();
    }

    public static void actualizarEstadoPedido(String codigo, String nuevoEstado) {
        if (codigo == null || nuevoEstado == null) return;
        for (PedidoRegistro p : PEDIDOS) {
            if (codigo.equalsIgnoreCase(p.codigo)) {
                p.estado = nuevoEstado;
                notifyPedidoListeners();
                return;
            }
        }
    }

    public static String formatearColones(double monto) {
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("es", "CR"));
        nf.setMinimumFractionDigits(0);
        nf.setMaximumFractionDigits(0);
        return "₡" + nf.format(Math.round(monto));
    }

    public void refrescar() {
        model.setRowCount(0);
        for (PedidoRegistro p : PEDIDOS) {
            model.addRow(new Object[]{
                    p.codigo,
                    p.cliente,
                    p.mesa > 0 ? "Mesa " + p.mesa : "Para llevar",
                    p.tipo,
                    p.detalle,
                    formatearColones(p.total),
                    p.estado
            });
        }
    }
}
