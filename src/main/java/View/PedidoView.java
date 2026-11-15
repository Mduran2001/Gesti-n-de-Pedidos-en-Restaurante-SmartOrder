/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 *
 * @author espin
 */

public class PedidoView extends JPanel {
    public PedidoView() {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(45, 40, 38, 40));
        setLayout(new BorderLayout(0, 24));

        JLabel title = new JLabel("Pedidos");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        add(title, BorderLayout.NORTH);

        String[] cols = {"Pedido", "Tipo", "Total", "Estado"};
        Object[][] rows = {
            {"#38453", "En restaurante", " 32.000", "En preparacion"},
            {"#14843", "Para llevar", " $9.000", "Recibido"},
            {"#25283", "Express", " $15.000", "Listo"},
            {"#04848", "En restaurante", " $23.000", "En preparacion"},
            {"#726283", "Express", "  13.000", "Listo"},
            {"#459565", "En restaurante", "  30.000", "Recibido"},
            {"#174374", "Para llevar", "  11.000", "En preparacion"}
        };

        JTable table = new JTable(new DefaultTableModel(rows, cols) {
            @Override
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
        add(scroll, BorderLayout.CENTER);
    }
}