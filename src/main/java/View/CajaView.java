/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Controladores.FacturaController;
import DaoImp.FacturaDaoImp;
import Model.Factura;
import Servicios.FacturaService;
import View.MesaView.EstadoMesa;
import View.PedidoView.PedidoRegistro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 *
 * @author nacho
 */
public class CajaView extends JPanel {

    public static class FacturaRegistro {
        public final String numeroFactura;
        public final String codigoPedido;
        public final String cliente;
        public final int mesa;
        public final String metodoPago;
        public final String detalle;
        public final double subtotal;
        public final double impuestos;
        public final double total;
        public final LocalDateTime fechaEmision;

        public FacturaRegistro(String numeroFactura, String codigoPedido, String cliente, int mesa,
                               String metodoPago, String detalle, double subtotal, double impuestos,
                               double total, LocalDateTime fechaEmision) {
            this.numeroFactura = numeroFactura;
            this.codigoPedido = codigoPedido;
            this.cliente = cliente;
            this.mesa = mesa;
            this.metodoPago = metodoPago;
            this.detalle = detalle;
            this.subtotal = subtotal;
            this.impuestos = impuestos;
            this.total = total;
            this.fechaEmision = fechaEmision;
        }
    }

    private static final List<FacturaRegistro> FACTURAS = new ArrayList<>();
    private static final List<Runnable> LISTENERS = new ArrayList<>();
    private static int secuenciaFactura = 104;

    static {
        LocalDate hoy = LocalDate.now();
        FACTURAS.add(new FacturaRegistro("FAC-0101", "PED-20790", "Andrea Solís", 2, "Tarjeta",
                "1 Casado con Bistec, 1 Refresco Natural", 6200, 806, 7006, hoy.atTime(9, 25)));
        FACTURAS.add(new FacturaRegistro("FAC-0102", "PED-20795", "Luis Brenes", 11, "Efectivo",
                "2 Hamburguesas Dobles, 1 Papas", 12400, 1612, 14012, hoy.atTime(10, 10)));
        FACTURAS.add(new FacturaRegistro("FAC-0103", "PED-20800", "Camila Rojas", 5, "SINPE Móvil",
                "1 Pizza Suprema, 2 Gaseosas", 10500, 1365, 11865, hoy.atTime(10, 48)));
    }

    private final JLabel lblPedidosDia = new JLabel();
    private final JLabel lblVentasDia = new JLabel();
    private final DefaultTableModel facturasModel;

    private final JTextField txtPedido;
    private final JTextField txtCliente;
    private final JSpinner spnMesa;
    private final JComboBox<String> cmbMetodo;
    private final JTextField txtDetalle;
    private final JTextField txtSubtotal;
    private final JLabel lblImpuestoCalc = new JLabel("IVA (13%): ₡0");
    private final JLabel lblTotalCalc = new JLabel("Total: ₡0");

    public CajaView() {
        setBackground(new Color(244, 246, 250));
        setBorder(BorderFactory.createEmptyBorder(28, 32, 28, 32));
        setLayout(new BorderLayout(0, 18));

        JPanel topPanel = new JPanel(new BorderLayout(16, 12));
        topPanel.setOpaque(false);

        JLabel title = new JLabel("Caja y Facturación");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(new Color(23, 28, 38));
        topPanel.add(title, BorderLayout.WEST);

        JPanel kpiRow = new JPanel(new GridLayout(1, 2, 14, 0));
        kpiRow.setOpaque(false);

        JPanel cardPedidosDia = crearTarjeta();
        cardPedidosDia.setLayout(new BorderLayout());
        JLabel lblPedTitle = new JLabel("Cantidad de pedidos / facturas al día");
        lblPedTitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblPedTitle.setForeground(new Color(100, 110, 128));
        lblPedidosDia.setFont(new Font("SansSerif", Font.BOLD, 26));
        lblPedidosDia.setForeground(new Color(37, 99, 235));
        cardPedidosDia.add(lblPedTitle, BorderLayout.NORTH);
        cardPedidosDia.add(lblPedidosDia, BorderLayout.CENTER);

        JPanel cardVentasDia = crearTarjeta();
        cardVentasDia.setLayout(new BorderLayout());
        JLabel lblVenTitle = new JLabel("Total facturado hoy");
        lblVenTitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblVenTitle.setForeground(new Color(100, 110, 128));
        lblVentasDia.setFont(new Font("SansSerif", Font.BOLD, 26));
        lblVentasDia.setForeground(new Color(22, 163, 74));
        cardVentasDia.add(lblVenTitle, BorderLayout.NORTH);
        cardVentasDia.add(lblVentasDia, BorderLayout.CENTER);

        kpiRow.add(cardPedidosDia);
        kpiRow.add(cardVentasDia);
        topPanel.add(kpiRow, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(0, 16));
        centerPanel.setOpaque(false);

        JPanel formCard = crearTarjeta();
        formCard.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel formTitle = new JLabel("Ingresar Nueva Factura (guarda el pedido en Pedidos y Cocina)");
        formTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        formTitle.setForeground(new Color(23, 28, 38));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 6;
        formCard.add(formTitle, gbc);
        gbc.gridwidth = 1;

        txtPedido = crearCampoEditable("PED-50101", 135);
        txtCliente = crearCampoEditable("Cliente Mostrador", 165);
        txtSubtotal = crearCampoEditable("8500", 135);
        txtDetalle = crearCampoEditable("1 Casado con Pollo, 1 Refresco", 215);

        spnMesa = new JSpinner(new SpinnerNumberModel(1, 0, 24, 1));
        spnMesa.setPreferredSize(new Dimension(85, 36));
        spnMesa.setMinimumSize(new Dimension(85, 36));
        spnMesa.setBorder(BorderFactory.createLineBorder(new Color(195, 203, 214), 1, false));
        ((JSpinner.DefaultEditor) spnMesa.getEditor()).getTextField().setFont(new Font("SansSerif", Font.PLAIN, 13));

        cmbMetodo = new JComboBox<>(new String[]{"Efectivo", "Tarjeta", "SINPE Móvil"});
        cmbMetodo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        cmbMetodo.setBackground(Color.WHITE);
        cmbMetodo.setPreferredSize(new Dimension(145, 36));
        cmbMetodo.setBorder(BorderFactory.createLineBorder(new Color(195, 203, 214), 1, false));

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.weightx = 0;
        formCard.add(crearLabel("N° Pedido:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.25;
        formCard.add(txtPedido, gbc);

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

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.weightx = 0;
        formCard.add(crearLabel("Subtotal (₡):"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.25;
        formCard.add(txtSubtotal, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        formCard.add(crearLabel("Método pago:"), gbc);
        gbc.gridx = 3;
        gbc.weightx = 0.35;
        formCard.add(cmbMetodo, gbc);

        gbc.gridx = 4;
        gbc.weightx = 0;
        formCard.add(crearLabel("Detalle (qué pidió):"), gbc);
        gbc.gridx = 5;
        gbc.weightx = 0.40;
        formCard.add(txtDetalle, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 0.25;
        lblImpuestoCalc.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblImpuestoCalc.setForeground(new Color(100, 110, 128));
        formCard.add(lblImpuestoCalc, gbc);

        gbc.gridx = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 0.35;
        lblTotalCalc.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTotalCalc.setForeground(new Color(23, 28, 38));
        formCard.add(lblTotalCalc, gbc);

        gbc.gridx = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 0.40;
        JPanel actionsBox = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actionsBox.setOpaque(false);

        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnLimpiar.setBackground(new Color(238, 242, 247));
        btnLimpiar.setForeground(new Color(23, 28, 38));
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 206, 216), 1, false),
                BorderFactory.createEmptyBorder(9, 14, 9, 14)
        ));
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        JButton btnFacturar = new JButton("Emitir Factura");
        btnFacturar.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnFacturar.setBackground(new Color(37, 99, 235));
        btnFacturar.setForeground(Color.WHITE);
        btnFacturar.setFocusPainted(false);
        btnFacturar.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btnFacturar.addActionListener(e -> emitirFactura());

        actionsBox.add(btnLimpiar);
        actionsBox.add(btnFacturar);
        formCard.add(actionsBox, gbc);

        centerPanel.add(formCard, BorderLayout.NORTH);

        JPanel tableCard = crearTarjeta();
        tableCard.setLayout(new BorderLayout(0, 10));
        JLabel tblTitle = new JLabel("Facturas Registradas Hoy");
        tblTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        tblTitle.setForeground(new Color(23, 28, 38));
        tableCard.add(tblTitle, BorderLayout.NORTH);

        String[] cols = {"Factura", "Pedido", "Cliente", "Mesa", "Pago", "Subtotal", "IVA 13%", "Total", "Hora"};
        facturasModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(facturasModel);
        table.setRowHeight(34);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        table.setBackground(Color.WHITE);
        table.setGridColor(new Color(224, 229, 236));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(224, 229, 236), 1, false));
        scroll.getViewport().setBackground(Color.WHITE);
        tableCard.add(scroll, BorderLayout.CENTER);

        centerPanel.add(tableCard, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        txtSubtotal.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { actualizarPreviewTotales(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { actualizarPreviewTotales(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { actualizarPreviewTotales(); }
        });
        actualizarPreviewTotales();

        addFacturaListener(this::refrescar);
        refrescar();
    }

    private JPanel crearTarjeta() {
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 216, 226), 1, false),
                BorderFactory.createEmptyBorder(16, 18, 16, 18)
        ));
        return p;
    }

    private JLabel crearLabel(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("SansSerif", Font.BOLD, 13));
        l.setForeground(new Color(23, 28, 38));
        return l;
    }

    private JTextField crearCampoEditable(String textoInicial, int minWidth) {
        JTextField tf = new JTextField(textoInicial);
        tf.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tf.setBackground(Color.WHITE);
        tf.setForeground(new Color(23, 28, 38));
        tf.setEditable(true);
        tf.setEnabled(true);
        tf.setFocusable(true);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(195, 203, 214), 1, false),
                BorderFactory.createEmptyBorder(7, 10, 7, 10)
        ));
        Dimension d = new Dimension(minWidth, 36);
        tf.setMinimumSize(d);
        tf.setPreferredSize(d);
        return tf;
    }

    public static void addFacturaListener(Runnable r) {
        if (r != null && !LISTENERS.contains(r)) {
            LISTENERS.add(r);
        }
    }

    public static List<FacturaRegistro> getFacturasHoy() {
        LocalDate hoy = LocalDate.now();
        List<FacturaRegistro> res = new ArrayList<>();
        for (FacturaRegistro f : FACTURAS) {
            if (f.fechaEmision != null && hoy.equals(f.fechaEmision.toLocalDate())) {
                res.add(f);
            }
        }
        return Collections.unmodifiableList(res);
    }

    public static int getCantidadFacturasHoy() {
        return getFacturasHoy().size();
    }

    public static double getVentasHoy() {
        double sum = 0;
        for (FacturaRegistro f : getFacturasHoy()) {
            sum += f.total;
        }
        return sum;
    }

    private void actualizarPreviewTotales() {
        double sub = parseSubtotal();
        double iva = Math.round(sub * 0.13);
        double tot = sub + iva;
        lblImpuestoCalc.setText("IVA (13%): " + PedidoView.formatearColones(iva));
        lblTotalCalc.setText("Total: " + PedidoView.formatearColones(tot));
    }

    private double parseSubtotal() {
        try {
            String raw = txtSubtotal.getText().trim().replace("₡", "").replace(" ", "").replace(",", "");
            if (raw.isEmpty()) return 0;
            return Math.max(0, Double.parseDouble(raw));
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    private void emitirFactura() {
        String codPed = txtPedido.getText().trim();
        String cliente = txtCliente.getText().trim();
        int mesa = (Integer) spnMesa.getValue();
        String metodo = (String) cmbMetodo.getSelectedItem();
        String detalle = txtDetalle.getText().trim();
        double subtotal = parseSubtotal();

        if (codPed.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa el número de pedido.");
            txtPedido.requestFocus();
            return;
        }
        if (cliente.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa el nombre del cliente.");
            txtCliente.requestFocus();
            return;
        }
        if (subtotal <= 0) {
            JOptionPane.showMessageDialog(this, "Ingresa un subtotal válido mayor a 0.");
            txtSubtotal.requestFocus();
            return;
        }

        double impuestos = Math.round(subtotal * 0.13);
        double total = subtotal + impuestos;
        String numFac = "FAC-" + String.format("%04d", secuenciaFactura++);
        String detFinal = detalle.isEmpty() ? "Consumo en restaurante" : detalle;

        FacturaRegistro reg = new FacturaRegistro(
                numFac, codPed, cliente, mesa,
                metodo != null ? metodo : "Efectivo",
                detFinal, subtotal, impuestos, total, LocalDateTime.now()
        );
        FACTURAS.add(0, reg);

        // Guardar también en Pedidos (y Cocina)
        PedidoView.registrarPedido(new PedidoRegistro(
                codPed,
                cliente,
                mesa,
                mesa > 0 ? "En restaurante" : "Para llevar",
                detFinal,
                subtotal,
                "Recibido",
                LocalDateTime.now()
        ));

        if (mesa > 0) {
            MesaView.setEstadoMesa(mesa, EstadoMesa.LIBRE);
        }

        try {
            Factura model = new Factura();
            model.setIdPago(1);
            model.setIdPedido(extraerNumeroSeguro(codPed, 1));
            model.setSubtotal(subtotal);
            model.setImpuestos(impuestos);
            model.setTotal(total);
            model.setFechaEmision(LocalDateTime.now());
            new FacturaController(new FacturaService(new FacturaDaoImp())).registrarFactura(model);
        } catch (RuntimeException ignored) {
        }

        for (Runnable r : new ArrayList<>(LISTENERS)) {
            r.run();
        }

        JOptionPane.showMessageDialog(
                this,
                "Factura " + numFac + " emitida por " + PedidoView.formatearColones(total) + ".\n"
                        + "El pedido " + codPed + " se guardó en Pedidos y Cocina.\n"
                        + "Pedidos/facturas del día: " + getCantidadFacturasHoy()
        );
        txtPedido.setText("PED-" + (50101 + getCantidadFacturasHoy()));
    }

    private int extraerNumeroSeguro(String texto, int fallback) {
        if (texto == null) return fallback;
        String digits = texto.replaceAll("\\D+", "");
        if (digits.isEmpty()) return fallback;
        try {
            return Integer.parseInt(digits.substring(0, Math.min(digits.length(), 8)));
        } catch (NumberFormatException ex) {
            return fallback;
        }
    }

    private void limpiarFormulario() {
        txtPedido.setText("");
        txtCliente.setText("");
        spnMesa.setValue(0);
        txtDetalle.setText("");
        txtSubtotal.setText("");
        actualizarPreviewTotales();
        txtPedido.requestFocus();
    }

    public void refrescar() {
        lblPedidosDia.setText(String.valueOf(getCantidadFacturasHoy()));
        lblVentasDia.setText(PedidoView.formatearColones(getVentasHoy()));

        facturasModel.setRowCount(0);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("hh:mm a");
        for (FacturaRegistro f : getFacturasHoy()) {
            facturasModel.addRow(new Object[]{
                    f.numeroFactura,
                    f.codigoPedido,
                    f.cliente,
                    f.mesa > 0 ? "Mesa " + f.mesa : "Para llevar",
                    f.metodoPago,
                    PedidoView.formatearColones(f.subtotal),
                    PedidoView.formatearColones(f.impuestos),
                    PedidoView.formatearColones(f.total),
                    f.fechaEmision != null ? f.fechaEmision.format(fmt) : ""
            });
        }
    }
}
