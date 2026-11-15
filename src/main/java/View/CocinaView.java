/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author espin
 */

public class CocinaView extends JPanel {
    public CocinaView() {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(45, 40, 38, 40));
        setLayout(new BorderLayout(0, 24));

        JLabel title = new JLabel("Cocina");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        add(title, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(3, 3, 10, 10));
        grid.setOpaque(false);

        String[] status = {"Recibido", "En preparacion", "Listo", "Listo", "Listo", "Recibido", "En preparacion", "Recibido", "En preparacion"};
        String[] ids = {"20802", "30802", "40802", "90802", "42343", "14235", "23561", "42734", "92376"};
        String[] food = {"1 Pizza Peperonni", "2 Hamburguesas", "2 Pastas Carbonara", "Filet de pescado", "1 Ensalada Caesar", "1 orden de papas fritas", "4 Piezas de pollo", "5 Tacos", "2 Hamburguesas"};
        int[] tables = {6, 4, 24, 17, 20, 9, 5, 14, 19};

        for (int i = 0; i < 9; i++) {
            JPanel card = new JPanel();
            card.setBackground(new Color(218, 218, 218));
            card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

            JLabel lblStatus = new JLabel(status[i]);
            lblStatus.setFont(new Font("SansSerif", Font.BOLD, 14));
            card.add(lblStatus);
            card.add(Box.createVerticalStrut(10));

            JPanel inner = new JPanel();
            inner.setBackground(Color.WHITE);
            inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
            inner.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            inner.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel lblId = new JLabel("Pedido #" + ids[i]);
            lblId.setFont(new Font("SansSerif", Font.BOLD, 15));
            JLabel lblFood = new JLabel(food[i]);
            lblFood.setFont(new Font("SansSerif", Font.PLAIN, 13));
            JLabel lblTable = new JLabel("Mesa " + tables[i]);
            lblTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
            JLabel lblVer = new JLabel("Ver pedido...");
            lblVer.setForeground(Color.GRAY);
            lblVer.setFont(new Font("SansSerif", Font.PLAIN, 12));

            inner.add(lblId);
            inner.add(lblFood);
            inner.add(lblTable);
            inner.add(Box.createVerticalStrut(5));
            inner.add(lblVer);

            card.add(inner);
            grid.add(card);
        }
        add(grid, BorderLayout.CENTER);
    }
}