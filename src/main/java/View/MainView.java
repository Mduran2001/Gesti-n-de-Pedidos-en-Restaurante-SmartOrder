/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import View.CajaView.FacturaRegistro;
import View.MesaView.EstadoMesa;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 *
 * @author nacho
 */
public class MainView extends JFrame {

    private final JPanel pages = new JPanel(new CardLayout());
    private final Map<String, JButton> navigation = new LinkedHashMap<>();

    private final JLabel lblKpiPedidosDia = new JLabel();
    private final JLabel lblKpiMesasLibres = new JLabel();
    private final JLabel lblKpiPedidosActivos = new JLabel();
    private final JLabel lblKpiVentasHoy = new JLabel();
    private final JPanel recentOrdersBox = new JPanel();

    private final UsuarioView usuarioView = new UsuarioView();
    private final MenuView menuView = new MenuView();
    private final MesaView mesaView = new MesaView();
    private final PedidoView pedidoView = new PedidoView();
    private final CocinaView cocinaView = new CocinaView();
    private final CajaView cajaView = new CajaView();

    public MainView() {
        setTitle("Restaurante SmartOrder — Panel de Gestión");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1220, 800);
        setMinimumSize(new Dimension(1000, 680));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(244, 246, 250));

        add(buildSidebar(), BorderLayout.WEST);

        pages.add(buildDashboardPage(), "DashBoard");
        pages.add(usuarioView, "Usuarios");
        pages.add(menuView, "Menu");
        pages.add(mesaView, "Mesas");
        pages.add(pedidoView, "Pedidos");
        pages.add(cocinaView, "Cocina");
        pages.add(cajaView, "Caja");

        add(pages, BorderLayout.CENTER);

        MesaView.addMesaListener(this::actualizarDashboard);
        PedidoView.addPedidoListener(this::actualizarDashboard);
        CajaView.addFacturaListener(this::actualizarDashboard);

        actualizarDashboard();
        showSection("DashBoard");
    }

    private JPanel buildSidebar() {
        Color sidebarBg = new Color(24, 28, 36);
        JPanel sidebar = new JPanel();
        sidebar.setBackground(sidebarBg);
        sidebar.setPreferredSize(new Dimension(245, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(24, 14, 20, 14));

        JLabel brand = new JLabel("SmartOrder");
        brand.setForeground(Color.WHITE);
        brand.setFont(new Font("SansSerif", Font.BOLD, 22));
        brand.setAlignmentX(Component.LEFT_ALIGNMENT);
        brand.setBorder(BorderFactory.createEmptyBorder(0, 8, 4, 8));
        sidebar.add(brand);

        JLabel sub = new JLabel("Gestión de Restaurante");
        sub.setForeground(new Color(156, 163, 175));
        sub.setFont(new Font("SansSerif", Font.PLAIN, 13));
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);
        sub.setBorder(BorderFactory.createEmptyBorder(0, 8, 22, 8));
        sidebar.add(sub);

        String[] sections = {"DashBoard", "Usuarios", "Menu", "Mesas", "Pedidos", "Cocina", "Caja"};
        for (String s : sections) {
            JButton b = new JButton(s);
            b.setHorizontalAlignment(SwingConstants.LEFT);
            b.setForeground(new Color(209, 213, 219));
            b.setBackground(sidebarBg);
            b.setFont(new Font("SansSerif", Font.BOLD, 14));
            b.setFocusPainted(false);
            b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
            b.setPreferredSize(new Dimension(215, 44));
            b.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
            b.setAlignmentX(Component.LEFT_ALIGNMENT);

            b.addActionListener(e -> showSection(s));
            navigation.put(s, b);
            sidebar.add(b);
            sidebar.add(Box.createVerticalStrut(6));
        }

        sidebar.add(Box.createVerticalGlue());

        JButton exit = new JButton("Cerrar sesión");
        exit.setHorizontalAlignment(SwingConstants.LEFT);
        exit.setForeground(new Color(248, 113, 113));
        exit.setBackground(sidebarBg);
        exit.setFont(new Font("SansSerif", Font.BOLD, 13));
        exit.setFocusPainted(false);
        exit.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        exit.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
        exit.setAlignmentX(Component.LEFT_ALIGNMENT);
        exit.addActionListener(e -> {
            new LoginView().setVisible(true);
            dispose();
        });
        sidebar.add(exit);

        return sidebar;
    }

    private void showSection(String name) {
        if ("Usuarios".equals(name)) {
            usuarioView.refrescar();
        } else if ("Pedidos".equals(name)) {
            pedidoView.refrescar();
        } else if ("Cocina".equals(name)) {
            cocinaView.refrescar();
        } else if ("Caja".equals(name)) {
            cajaView.refrescar();
        } else if ("Mesas".equals(name)) {
            mesaView.refrescar();
        }
        ((CardLayout) pages.getLayout()).show(pages, name);
        navigation.forEach((key, btn) -> {
            boolean active = key.equals(name);
            btn.setBackground(active ? new Color(41, 48, 63) : new Color(24, 28, 36));
            btn.setForeground(active ? Color.WHITE : new Color(209, 213, 219));
        });
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

    private JPanel buildDashboardPage() {
        JPanel page = new JPanel(new BorderLayout(0, 18));
        page.setBackground(new Color(244, 246, 250));
        page.setBorder(BorderFactory.createEmptyBorder(28, 32, 28, 32));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel title = new JLabel("Dashboard");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(new Color(23, 28, 38));
        JLabel desc = new JLabel("Resumen operativo en tiempo real");
        desc.setFont(new Font("SansSerif", Font.PLAIN, 14));
        desc.setForeground(new Color(100, 110, 128));
        header.add(title, BorderLayout.NORTH);
        header.add(desc, BorderLayout.SOUTH);
        page.add(header, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout(0, 18));
        content.setOpaque(false);

        JPanel kpis = new JPanel(new GridLayout(2, 2, 16, 16));
        kpis.setOpaque(false);
        kpis.add(buildKpiCard("Pedidos / Facturas al día", lblKpiPedidosDia, new Color(37, 99, 235)));
        kpis.add(buildKpiCard("Mesas Libres", lblKpiMesasLibres, new Color(22, 163, 74)));
        kpis.add(buildKpiCard("Pedidos Registrados", lblKpiPedidosActivos, new Color(217, 119, 6)));
        kpis.add(buildKpiCard("Ventas Hoy", lblKpiVentasHoy, new Color(23, 28, 38)));
        content.add(kpis, BorderLayout.NORTH);

        JPanel bottomGrid = new JPanel(new GridLayout(1, 2, 16, 0));
        bottomGrid.setOpaque(false);

        JPanel recentCard = crearTarjeta();
        recentCard.setLayout(new BorderLayout(0, 10));
        JLabel recTitle = new JLabel("Facturas Recientes del Día");
        recTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        recTitle.setForeground(new Color(23, 28, 38));
        recentCard.add(recTitle, BorderLayout.NORTH);

        recentOrdersBox.setOpaque(false);
        recentOrdersBox.setLayout(new BoxLayout(recentOrdersBox, BoxLayout.Y_AXIS));
        recentCard.add(recentOrdersBox, BorderLayout.CENTER);

        JPanel quickCard = crearTarjeta();
        quickCard.setLayout(new GridLayout(3, 1, 10, 10));
        JLabel qaTitle = new JLabel("Accesos Rápidos");
        qaTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        qaTitle.setForeground(new Color(23, 28, 38));
        quickCard.add(qaTitle);

        JButton goMesas = new JButton("Administrar Mesas");
        goMesas.setFont(new Font("SansSerif", Font.BOLD, 13));
        goMesas.setBackground(new Color(238, 242, 247));
        goMesas.setForeground(new Color(23, 28, 38));
        goMesas.setFocusPainted(false);
        goMesas.setBorder(BorderFactory.createLineBorder(new Color(200, 206, 216), 1, false));
        goMesas.addActionListener(e -> showSection("Mesas"));

        JButton goCaja = new JButton("Ingresar Factura en Caja");
        goCaja.setFont(new Font("SansSerif", Font.BOLD, 13));
        goCaja.setBackground(new Color(37, 99, 235));
        goCaja.setForeground(Color.WHITE);
        goCaja.setFocusPainted(false);
        goCaja.setBorder(BorderFactory.createEmptyBorder());
        goCaja.addActionListener(e -> showSection("Caja"));

        quickCard.add(goMesas);
        quickCard.add(goCaja);

        bottomGrid.add(recentCard);
        bottomGrid.add(quickCard);
        content.add(bottomGrid, BorderLayout.CENTER);

        page.add(content, BorderLayout.CENTER);
        return page;
    }

    private JPanel buildKpiCard(String label, JLabel valueLabel, Color accent) {
        JPanel card = crearTarjeta();
        card.setLayout(new BorderLayout(0, 8));
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lbl.setForeground(new Color(100, 110, 128));
        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        valueLabel.setForeground(accent);
        card.add(lbl, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    private void actualizarDashboard() {
        lblKpiPedidosDia.setText(String.valueOf(CajaView.getCantidadFacturasHoy()));
        lblKpiMesasLibres.setText(String.valueOf(MesaView.contarPorEstado(EstadoMesa.LIBRE)));
        lblKpiPedidosActivos.setText(String.valueOf(PedidoView.getPedidos().size()));
        lblKpiVentasHoy.setText(PedidoView.formatearColones(CajaView.getVentasHoy()));

        recentOrdersBox.removeAll();
        int count = 0;
        for (FacturaRegistro f : CajaView.getFacturasHoy()) {
            if (count >= 5) break;
            JLabel row = new JLabel("• " + f.numeroFactura + " (" + f.codigoPedido + ") — " + f.cliente + " · " + PedidoView.formatearColones(f.total));
            row.setFont(new Font("SansSerif", Font.PLAIN, 13));
            row.setForeground(new Color(23, 28, 38));
            row.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
            recentOrdersBox.add(row);
            count++;
        }
        recentOrdersBox.revalidate();
        recentOrdersBox.repaint();
    }
}
