/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author espin
 */

public class MesaView extends JPanel {
    public MesaView() {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(45, 40, 38, 40));
        setLayout(new BorderLayout(0, 20));

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        header.setOpaque(false);
        JLabel title = new JLabel("Gestion de Mesas     ");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        header.add(title);

        JLabel legend = new JLabel("Ocupada     Reservada     Libre");
        legend.setFont(new Font("SansSerif", Font.BOLD, 15));
        header.add(legend);
        add(header, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(4, 6, 15, 15));
        grid.setOpaque(false);

        // Lógica de colores según el Frame 5
        Set<Integer> busy = new HashSet<>(Arrays.asList(4, 8, 12, 13, 15, 20, 22));
        Set<Integer> reserved = new HashSet<>(Arrays.asList(9, 16, 17, 21));

        for (int i = 1; i <= 24; i++) {
            JButton b = new JButton(String.valueOf(i));
            b.setBackground(busy.contains(i) ? new Color(220, 0, 0) : reserved.contains(i) ? new Color(255, 230, 80) : new Color(0, 200, 50));
            b.setForeground(Color.BLACK);
            b.setFont(new Font("SansSerif", Font.BOLD, 18));
            b.setOpaque(true);
            b.setFocusPainted(false);
            b.setBorder(BorderFactory.createEmptyBorder());
            grid.add(b);
        }
        add(grid, BorderLayout.CENTER);
    }
}