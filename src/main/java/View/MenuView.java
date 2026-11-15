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

public class MenuView extends JPanel {
    public MenuView() {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(45, 40, 38, 40));
        setLayout(new BorderLayout(0, 24));

        JLabel title = new JLabel("Menu");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        add(title, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(3, 2, 10, 10));
        grid.setOpaque(false);

        for (int i = 1; i <= 6; i++) {
            JPanel card = new JPanel(new BorderLayout(15, 0));
            card.setBackground(new Color(218, 218, 218)); // Gris claro del Figma
            card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            JPanel picture = new JPanel();
            picture.setBackground(Color.GRAY); // Cuadro gris oscuro
            picture.setPreferredSize(new Dimension(80, 80));
            card.add(picture, BorderLayout.WEST);

            JPanel info = new JPanel(new GridLayout(2, 1));
            info.setOpaque(false);
            
            JLabel lblPlato = new JLabel("Plato " + i);
            lblPlato.setFont(new Font("SansSerif", Font.PLAIN, 16));
            JLabel lblInfo = new JLabel("Info del plato");
            lblInfo.setFont(new Font("SansSerif", Font.PLAIN, 14));
            
            info.add(lblPlato);
            info.add(lblInfo);
            card.add(info, BorderLayout.CENTER);

            grid.add(card);
        }
        add(grid, BorderLayout.CENTER);
    }
}