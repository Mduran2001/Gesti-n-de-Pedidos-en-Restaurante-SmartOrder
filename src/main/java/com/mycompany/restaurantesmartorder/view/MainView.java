/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantesmartorder.view;
    
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
    private final Color dark = new Color(68,68,68), light = new Color(218,218,218);
    public MainView() {
        setTitle("Restaurante SmartOrder"); setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1120,760); setMinimumSize(new Dimension(900,620)); setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        JPanel sidebar = new JPanel(); sidebar.setBackground(dark);
        sidebar.setPreferredSize(new Dimension(265,0));
        sidebar.setLayout(new BoxLayout(sidebar,BoxLayout.Y_AXIS));
        JLabel brand = new JLabel("Restaurante SmartOrder"); brand.setForeground(Color.WHITE);
        brand.setFont(new Font("SansSerif",Font.BOLD,19)); brand.setBorder(BorderFactory.createEmptyBorder(30,22,35,8));
        sidebar.add(brand);
        String[] sections = {"Dashboard","Usuarios","Menu","Mesas","Pedidos","Cocina","Caja"};
        for (String s:sections) {
            JButton b = new JButton(s + "    ➜"); b.setHorizontalAlignment(SwingConstants.LEFT);
            b.setForeground(Color.WHITE); b.setBackground(dark); b.setBorder(BorderFactory.createEmptyBorder(15,70,15,8));
            b.setFocusPainted(false); b.setMaximumSize(new Dimension(Integer.MAX_VALUE,58));
            b.addActionListener(e -> show(s)); navigation.put(s,b); sidebar.add(b);
        }
        JButton exit = new JButton("Cerrar sesión"); exit.setAlignmentX(Component.CENTER_ALIGNMENT);
        exit.addActionListener(e -> {new LoginView().setVisible(true); dispose();});
        sidebar.add(Box.createVerticalGlue()); sidebar.add(exit); sidebar.add(Box.createVerticalStrut(25));
        add(sidebar,BorderLayout.WEST);
        pages.add(dashboard(),"Dashboard"); pages.add(usuarios(),"Usuarios");
        pages.add(menu(),"Menu"); pages.add(mesas(),"Mesas");
        pages.add(pedidos(),"Pedidos"); pages.add(cocina(),"Cocina"); pages.add(caja(),"Caja");
        add(pages,BorderLayout.CENTER); show("Dashboard");
    }
    private void show(String name) {
        ((CardLayout)pages.getLayout()).show(pages,name);
        navigation.forEach((s,b)->b.setForeground(s.equals(name)?Color.BLACK:Color.WHITE));
    }
    private JPanel page(String title) {
        JPanel p = new JPanel(new BorderLayout(0,24)); p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(55,40,38,40));
        JLabel h = new JLabel(title); h.setFont(new Font("SansSerif",Font.BOLD,31));
        p.add(h,BorderLayout.NORTH); return p;
    }
    private JPanel block() {JPanel p=new JPanel();p.setBackground(light);p.setBorder(BorderFactory.createEmptyBorder(17,17,17,17));return p;}
    private JLabel txt(String s,int size,boolean bold) {JLabel l=new JLabel(s);l.setFont(new Font("SansSerif",bold?Font.BOLD:Font.PLAIN,size));return l;}
    private JPanel dashboard() {
        JPanel p=page("Dashboard"); JPanel center=new JPanel(new BorderLayout(0,10));center.setOpaque(false);
        JPanel stats=new JPanel(new GridLayout(2,2,8,8));
        String[][] data={{"Pedidos","40"},{"Mesas Libres","8"},{"En cocina","5"},{"Ventas","₡85 mil"}};
        for(String[] d:data){JPanel c=block();c.setLayout(new BoxLayout(c,BoxLayout.Y_AXIS));c.add(txt(d[0],16,false));c.add(Box.createVerticalStrut(12));c.add(txt(d[1],29,true));stats.add(c);}
        center.add(stats,BorderLayout.NORTH);
        JPanel recent=block();recent.setLayout(new BoxLayout(recent,BoxLayout.Y_AXIS));
        recent.add(txt("Pedidos Recientes",23,true));
        for(String s:new String[]{"#3495843 - En preparación","#4504956 - Recibido","#0487567 - Listo"}) {recent.add(Box.createVerticalStrut(19));recent.add(txt(s,15,false));}
        center.add(recent,BorderLayout.CENTER);p.add(center);return p;
    }
    private JPanel tablePage(String title,String[] cols,Object[][] rows) {
        JPanel p=page(title);JTable table=new JTable(new DefaultTableModel(rows,cols){public boolean isCellEditable(int r,int c){return false;}});
        table.setRowHeight(57);table.setFont(new Font("SansSerif",Font.PLAIN,15));
        table.getTableHeader().setFont(new Font("SansSerif",Font.BOLD,15));
        table.setBackground(light);table.setGridColor(light);table.setShowGrid(false);
        JScrollPane scroll=new JScrollPane(table);scroll.setBorder(BorderFactory.createEmptyBorder());
        p.add(scroll,BorderLayout.CENTER);return p;
    }
    private JPanel usuarios() {return tablePage("Usuarios",new String[]{"Usuario","Rol","Estado"},new Object[][]{
        {"Ana López","Cocinera","Activo"},{"Carlos Ruiz","Mesero","Inactivo"},
        {"María Soto","Cajera","Activo"},{"Samantha Sánchez","Administrativo","Activo"},
        {"Monserrat Castillo","Mesera","Activo"},{"Mario Durán","Cocinero","Inactivo"},
        {"Andrés Aguilar","Administrativo","Activo"}});}
    private JPanel pedidos() {return tablePage("Pedidos",new String[]{"Pedido","Tipo","Total","Estado"},new Object[][]{
        {"#38453","En restaurante","₡32.000","En preparación"},{"#14843","Para llevar","₡9.000","Recibido"},
        {"#25283","Express","₡15.000","Listo"},{"#04848","En restaurante","₡23.000","En preparación"},
        {"#726283","Express","₡13.000","Listo"},{"#459565","En restaurante","₡30.000","Recibido"},
        {"#174374","Para llevar","₡11.000","En preparación"}});}
    private JPanel menu() {
        JPanel p=page("Menu");JPanel grid=new JPanel(new GridLayout(3,2,7,7));grid.setOpaque(false);
        for(int i=1;i<=6;i++){
            JPanel c=block();c.setLayout(new BorderLayout(15,0));
            JPanel picture=new JPanel();picture.setBackground(new Color(155,155,155));picture.setPreferredSize(new Dimension(90,105));
            c.add(picture,BorderLayout.WEST);
            JPanel info=new JPanel(new GridLayout(2,1));info.setOpaque(false);info.add(txt("Plato "+i,16,false));info.add(txt("Info del plato",15,false));
            c.add(info,BorderLayout.CENTER);grid.add(c);
        }p.add(grid);return p;
    }
    private JPanel mesas() {
        JPanel p=page("Gestión de Mesas"); JPanel content=new JPanel(new BorderLayout(0,35));content.setOpaque(false);
        JPanel legend=new JPanel(new FlowLayout(FlowLayout.LEFT,15,0));legend.setOpaque(false);
        legend.add(txt("🔴 Ocupada     🟡 Reservada     🟢 Libre",16,false));content.add(legend,BorderLayout.NORTH);
        JPanel grid=new JPanel(new GridLayout(4,6,35,35));grid.setOpaque(false);
        Set<Integer> busy=new HashSet<>(Arrays.asList(4,8,12,13,15,20,22));
        Set<Integer> reserved=new HashSet<>(Arrays.asList(9,16,17,21));
        for(int i=1;i<=24;i++){
            final int n=i;JButton b=new JButton(String.valueOf(i));
            b.setBackground(busy.contains(i)?new Color(230,0,0):reserved.contains(i)?new Color(255,236,90):new Color(0,202,65));
            b.setOpaque(true);b.setFont(new Font("SansSerif",Font.BOLD,20));b.setFocusPainted(false);
            b.addActionListener(e->JOptionPane.showMessageDialog(this,"Mesa "+n+" (datos de ejemplo)"));grid.add(b);
        }content.add(grid,BorderLayout.CENTER);p.add(content);return p;
    }
    private JPanel cocina() {
        JPanel p=page("Cocina");JPanel grid=new JPanel(new GridLayout(3,3,12,12));grid.setOpaque(false);
        String[] status={"Recibido","En preparación","Listo","Listo","Listo","Recibido","En preparación","Recibido","En preparación"};
        String[] ids={"20802","30802","40802","90802","42343","14235","23561","42734","92376"};
        String[] food={"1 Pizza Pepperoni","2 Hamburguesas","2 Pastas Carbonara","Filete de pescado","1 Ensalada César","1 orden de papas fritas","4 Piezas de pollo","5 Tacos","2 Hamburguesas"};
        int[] tables={6,4,24,17,20,9,5,14,19};
        for(int i=0;i<9;i++){
            JPanel c=block();c.setLayout(new BoxLayout(c,BoxLayout.Y_AXIS));
            c.add(txt(status[i],14,false));c.add(Box.createVerticalStrut(10));
            JPanel inner=new JPanel();inner.setBackground(Color.WHITE);inner.setLayout(new BoxLayout(inner,BoxLayout.Y_AXIS));
            inner.setBorder(BorderFactory.createEmptyBorder(10,10,10,8));
            inner.add(txt("Pedido #"+ids[i],15,false));inner.add(txt(food[i],12,false));inner.add(txt("Mesa "+tables[i],12,false));
            JButton view=new JButton("Ver pedido...");final String id=ids[i];
            view.addActionListener(e->JOptionPane.showMessageDialog(this,"Pedido #"+id+" (demostración visual)"));inner.add(view);
            c.add(inner);grid.add(c);
        }p.add(grid);return p;
    }
    private JPanel caja() {
        JPanel p=page("Caja");JPanel grid=new JPanel(new GridLayout(1,3,12,0));grid.setOpaque(false);
        for(String id:new String[]{"20802","30802","40802"}){
            JPanel c=block();c.setLayout(new BorderLayout(0,10));c.add(txt("Listo",15,false),BorderLayout.NORTH);
            JPanel inner=new JPanel();inner.setBackground(Color.WHITE);
            inner.setBorder(BorderFactory.createEmptyBorder(14,10,14,10));
            inner.setLayout(new BoxLayout(inner,BoxLayout.Y_AXIS));
            for(String s:new String[]{"Pedido #"+id,"Mesa 6"," ","1 Pizza Pepperoni: ₡7.900","1 Coca-Cola Zero 600ml: ₡1.000"," ","Sub-total: ₡8.900","IVA 13%: ₡1.157","Total: ₡10.057"," ","Método de pago: Tarjeta"}){
                inner.add(txt(s,12,false));inner.add(Box.createVerticalStrut(12));
            }
            JButton pay=new JButton("PROCESAR PAGO");pay.addActionListener(e->JOptionPane.showMessageDialog(this,"Pago de demostración: no se realizó ningún cobro."));
            inner.add(Box.createVerticalGlue());inner.add(pay);c.add(inner);grid.add(c);
        }p.add(grid);return p;
    }
}


