/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;
    
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
/**
 *
 * @author mario
 */
public class MainView extends JFrame {
    private final JPanel pages = new JPanel(new CardLayout());
    private final Map<String,JButton> navigation = new LinkedHashMap<>();
    private final Color dark = new Color(68, 68, 68);
    private final Color light = new Color(218, 218, 218);
    
    public MainView() {
        setTitle("Restaurante SmartOrder"); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1120, 760); 
        setMinimumSize(new Dimension(900, 620)); 
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        JPanel sidebar = new JPanel(); 
        sidebar.setBackground(dark);
        sidebar.setPreferredSize(new Dimension(250, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        
        JLabel brand = new JLabel("Restaurante SmartOrder"); 
        brand.setForeground(Color.WHITE);
        brand.setFont(new Font("SansSerif", Font.PLAIN, 18)); 
        brand.setBorder(BorderFactory.createEmptyBorder(30, 22, 35, 8));
        brand.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(brand);
        
        String[] sections = {"DashBoard", "Usuarios", "Menu", "Mesas", "Pedidos", "Cocina", "Caja"};
        for (String s : sections) {
            JButton b = new JButton(s); 
            b.setHorizontalAlignment(SwingConstants.LEFT);
            b.setForeground(Color.WHITE); 
            b.setBackground(dark); 
            b.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
            b.setFont(new Font("SansSerif", Font.PLAIN, 15));
            b.setFocusPainted(false); 
            b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
            
            b.setLayout(new BorderLayout());
            JLabel arrow = new JLabel("→");
            arrow.setForeground(Color.WHITE);
            arrow.setFont(new Font("SansSerif", Font.BOLD, 16));
            b.add(arrow, BorderLayout.EAST);

            final String name = s;
            b.addActionListener(e -> show(name)); 
            navigation.put(name, b); 
            sidebar.add(b);
        }
        
        JButton exit = new JButton("Cerrar sesión"); 
        exit.setAlignmentX(Component.CENTER_ALIGNMENT);
        exit.setForeground(Color.WHITE);
        exit.setBackground(dark);
        exit.setFocusPainted(false);
        exit.addActionListener(e -> {new LoginView().setVisible(true); dispose();});
        
        sidebar.add(Box.createVerticalGlue()); 
        sidebar.add(exit); 
        sidebar.add(Box.createVerticalStrut(25));
        add(sidebar, BorderLayout.WEST);
        
        pages.add(dashboard(), "DashBoard"); 
        pages.add(usuarios(), "Usuarios");
        pages.add(menu(), "Menu"); 
        pages.add(mesas(), "Mesas");
        pages.add(pedidos(), "Pedidos"); 
        pages.add(cocina(), "Cocina"); 
        pages.add(caja(), "Caja"); 
        
        add(pages, BorderLayout.CENTER); 
        show("DashBoard");
    }
    
    private void show(String name) {
        ((CardLayout)pages.getLayout()).show(pages, name);
        navigation.forEach((s, b) -> {
            JLabel arrow = (JLabel) b.getComponent(0);
            if (s.equals(name)) {
                b.setForeground(Color.BLACK);
                arrow.setText("←");
                arrow.setForeground(Color.BLACK);
            } else {
                b.setForeground(Color.WHITE);
                arrow.setText("→");
                arrow.setForeground(Color.WHITE);
            }
        });
    }
    
    private JPanel page(String title) {
        JPanel p = new JPanel(new BorderLayout(0, 24)); 
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(45, 40, 38, 40));
        JLabel h = new JLabel(title); 
        h.setFont(new Font("SansSerif", Font.BOLD, 28));
        p.add(h, BorderLayout.NORTH); 
        return p;
    }
    
    private JPanel block() {
        JPanel p = new JPanel();
        p.setBackground(light);
        p.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        return p;
    }
    
    private JLabel txt(String s, int size, boolean bold) {
        JLabel l = new JLabel(s);
        l.setFont(new Font("SansSerif", bold ? Font.BOLD : Font.PLAIN, size));
        return l;
    }
    
    private JPanel dashboard() {
        JPanel p = page("Dashboard"); 
        JPanel center = new JPanel(new BorderLayout(0, 20)); 
        center.setOpaque(false);
        
        center.add(txt("Resumen del restaurante", 18, false), BorderLayout.NORTH);

        JPanel stats = new JPanel(new GridLayout(2, 2, 8, 8));
        stats.setOpaque(false);
        String[][] data = {{"Pedidos", "40"}, {"Mesas Libres", "8"}, {"En cocina", "5"}, {"Ventas", " 85 mil"}};
        
        for (String[] d : data) {
            JPanel c = block();
            c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
            c.add(txt(d[0], 16, false));
            c.add(Box.createVerticalStrut(8));
            c.add(txt(d[1], 24, true));
            stats.add(c);
        }
        
        JPanel midPanel = new JPanel(new BorderLayout());
        midPanel.setOpaque(false);
        midPanel.add(stats, BorderLayout.NORTH);
        
        JPanel recent = block();
        recent.setLayout(new BoxLayout(recent, BoxLayout.Y_AXIS));
        recent.add(txt("Pedidos Recientes", 20, true));
        recent.add(Box.createVerticalStrut(15));
        for (String s : new String[]{"#3495843 En preparacion", "#4504956 Recibido", "#0487567- Listo"}) {
            recent.add(txt(s, 15, false));
            recent.add(Box.createVerticalStrut(8));
        }
        
        midPanel.add(recent, BorderLayout.CENTER);
        center.add(midPanel, BorderLayout.CENTER);
        
        p.add(center); 
        return p;
    }
    
    private JPanel tablePage(String title, String[] cols, Object[][] rows) {
        JPanel p = page(title);
        JTable table = new JTable(new DefaultTableModel(rows, cols) {
            public boolean isCellEditable(int r, int c) { return false; }
        });
        table.setRowHeight(40);
        table.setFont(new Font("SansSerif", Font.PLAIN, 15));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
        table.setBackground(Color.WHITE);
        table.setGridColor(Color.LIGHT_GRAY);
        
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);
        p.add(scroll, BorderLayout.CENTER); 
        return p;
    }

    private JPanel usuarios() {
        return tablePage("Usuarios", new String[]{"Usuario", "Rol", "Estado"}, new Object[][]{
        {"Ana López", "Cocinera", "Activo"}, {"Carlos Ruiz", "Mesero", "Inactivo Ruiz"},
        {"María Soto", "Cajera", "Activo"}, {"Samantha Sanchez", "Administrativo", "Activo"},
        {"Monserrat Castillo", "Mesera", "Activo"}, {"Mario Duran", "Cocinero", "Inactivo"},
        {"Andres Aguilar", "Administrativo", "Activo"}});
    }
    
    private JPanel pedidos() {
        return tablePage("Pedidos", new String[]{"Pedido", "Tipo", "Total", "Estado"}, new Object[][]{
        {"#38453", "En restaurante", " 32.000", "En preparacion"}, {"#14843", "Para llevar", " $9.000", "Recibido"},
        {"#25283", "Express", " $15.000", "Listo"}, {"#04848", "En restaurante", " $23.000", "En preparacion"},
        {"#726283", "Express", "  13.000", "Listo"}, {"#459565", "En restaurante", "  30.000", "Recibido"},
        {"#174374", "Para llevar", "  11.000", "En preparacion"}});
    }
    
    private JPanel menu() {
        JPanel p = page("Menu");
        JPanel grid = new JPanel(new GridLayout(3, 2, 10, 10)); 
        grid.setOpaque(false);
        for(int i=1; i<=6; i++){
            JPanel c = block(); 
            c.setLayout(new BorderLayout(15, 0));
            
            JPanel picture = new JPanel();
            picture.setBackground(Color.GRAY);
            picture.setPreferredSize(new Dimension(80, 80));
            c.add(picture, BorderLayout.WEST);
            
            JPanel info = new JPanel(new GridLayout(2, 1)); 
            info.setOpaque(false);
            info.add(txt("Plato " + i, 16, false));
            info.add(txt("Info del plato", 14, false));
            c.add(info, BorderLayout.CENTER);
            
            grid.add(c);
        }
        p.add(grid); 
        return p;
    }
    
    private JPanel mesas() {
        JPanel p = page("Gestion de Mesas"); 
        JPanel content = new JPanel(new BorderLayout(0, 20)); 
        content.setOpaque(false);
        
        JPanel legend = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0)); 
        legend.setOpaque(false);
        legend.add(txt("Ocupada", 15, true));
        legend.add(txt("Reservada", 15, true));
        legend.add(txt("Libre", 15, true));
        content.add(legend, BorderLayout.NORTH);
        
        JPanel grid = new JPanel(new GridLayout(4, 6, 15, 15)); 
        grid.setOpaque(false);
        Set<Integer> busy = new HashSet<>(Arrays.asList(4, 8, 12, 13, 15, 20, 22));
        Set<Integer> reserved = new HashSet<>(Arrays.asList(9, 16, 17, 21));
        
        for(int i=1; i<=24; i++){
            JButton b = new JButton(String.valueOf(i));
            b.setBackground(busy.contains(i) ? new Color(220, 0, 0) : reserved.contains(i) ? new Color(255, 230, 80) : new Color(0, 200, 50));
            b.setOpaque(true);
            b.setForeground(Color.BLACK);
            b.setFont(new Font("SansSerif", Font.BOLD, 18));
            b.setFocusPainted(false);
            b.setBorder(BorderFactory.createEmptyBorder());
            grid.add(b);
        }
        content.add(grid, BorderLayout.CENTER); 
        p.add(content); 
        return p;
    }
    
    private JPanel cocina() {
        JPanel p = page("Cocina");
        JPanel grid = new JPanel(new GridLayout(3, 3, 10, 10)); 
        grid.setOpaque(false);
        String[] status = {"Recibido", "En preparacion", "Listo", "Listo", "Listo", "Recibido", "En preparacion", "Recibido", "En preparacion"};
        String[] ids = {"20802", "30802", "40802", "90802", "42343", "14235", "23561", "42734", "92376"};
        String[] food = {"1 Pizza Peperonni", "2 Hamburguesas", "2 Pastas Carbonara", "Filet de pescado", "1 Ensalada Caesar", "1 orden de papas fritas", "4 Piezas de pollo", "5 Tacos", "2 Hamburguesas"};
        int[] tables = {6, 4, 24, 17, 20, 9, 5, 14, 19};
        
        for(int i=0; i<9; i++){
            JPanel c = block(); 
            c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
            
            c.add(txt(status[i], 14, true));
            c.add(Box.createVerticalStrut(10));
            
            JPanel inner = new JPanel();
            inner.setBackground(Color.WHITE);
            inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
            inner.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            inner.add(txt("Pedido #" + ids[i], 15, true));
            inner.add(txt(food[i], 13, false));
            inner.add(txt("Mesa " + tables[i], 13, false));
            
            JLabel lblVer = new JLabel("Ver pedido...");
            lblVer.setForeground(Color.GRAY);
            lblVer.setFont(new Font("SansSerif", Font.PLAIN, 12));
            inner.add(Box.createVerticalStrut(5));
            inner.add(lblVer);
            
            c.add(inner);
            grid.add(c);
        }
        
        p.add(grid); 
        return p;
    }
    
    private JPanel caja() {
        JPanel p = page("Caja");
        JPanel grid = new JPanel(new GridLayout(1, 3, 10, 0)); 
        grid.setOpaque(false);
        
        for(String id : new String[]{"20802", "30802", "40802"}){
            JPanel c = block(); 
            c.setLayout(new BorderLayout(0, 10));
            c.add(txt("Listo", 15, true), BorderLayout.NORTH);
            
            JPanel inner = new JPanel();
            inner.setBackground(Color.WHITE);
            inner.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
            
            inner.add(txt("Pedido #" + id, 14, false));
            inner.add(txt("Mesa 6", 14, false));
            inner.add(Box.createVerticalStrut(10));
            inner.add(txt("1 Pizza Peperonni: 7.900", 13, false));
            inner.add(txt("1 Coca-Cola Zero 600ml: 1.000", 13, false));
            inner.add(Box.createVerticalStrut(10));
            inner.add(txt("Sub-total: 8.900", 13, false));
            inner.add(txt("IVA 13%: 1.157", 13, false));
            inner.add(Box.createVerticalStrut(10));
            inner.add(txt("Total: 10.057", 16, true));
            inner.add(Box.createVerticalStrut(10));
            inner.add(txt("Metodo de pago: Tarjeta", 13, false));
            
            JButton pay = new JButton("PROCESAR PAGO");
            pay.setBackground(Color.LIGHT_GRAY); 
            pay.setFocusPainted(false);
            pay.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            pay.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            inner.add(Box.createVerticalGlue());
            inner.add(pay);
            c.add(inner);
            grid.add(c);
        }
        p.add(grid); 
        return p;
    }
}