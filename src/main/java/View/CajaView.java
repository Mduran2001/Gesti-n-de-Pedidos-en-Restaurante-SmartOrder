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

public class CajaView extends JPanel {
    public CajaView() {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(45, 40, 38, 40));
        setLayout(new BorderLayout(0, 24));

        JLabel title = new JLabel("Caja");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        add(title, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(1, 3, 10, 0));
        grid.setOpaque(false);

        for (String id : new String[]{"20802", "30802", "40802"}) {
            JPanel card = new JPanel(new BorderLayout(0, 10));
            card.setBackground(new Color(218, 218, 218));
            card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            JLabel lblStatus = new JLabel("Listo");
            lblStatus.setFont(new Font("SansSerif", Font.BOLD, 15));
            card.add(lblStatus, BorderLayout.NORTH);

            JPanel inner = new JPanel();
            inner.setBackground(Color.WHITE);
            inner.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));

            String[] lines = {
                "Pedido #" + id, "Mesa 6", " ", 
                "1 Pizza Peperonni: 7.900", "1 Coca-Cola Zero 600ml: 1.000", " ",
                "Sub-total: 8.900", "IVA 13%: 1.157", " ",
                "Total: 10.057", " ", "Metodo de pago: Tarjeta"
            };

            for (String line : lines) {
                JLabel l = new JLabel(line);
                if (line.startsWith("Total")) {
                    l.setFont(new Font("SansSerif", Font.BOLD, 16));
                } else {
                    l.setFont(new Font("SansSerif", Font.PLAIN, 13));
                }
                inner.add(l);
            }

            JButton pay = new JButton("PROCESAR PAGO");
            pay.setBackground(Color.BLACK);
            pay.setForeground(Color.WHITE);
            pay.setFont(new Font("SansSerif", Font.BOLD, 14));
            pay.setFocusPainted(false);
            pay.setAlignmentX(Component.CENTER_ALIGNMENT);

            inner.add(Box.createVerticalGlue());
            inner.add(pay);
            card.add(inner, BorderLayout.CENTER);

            grid.add(card);
        }
        add(grid, BorderLayout.CENTER);
    }
}
