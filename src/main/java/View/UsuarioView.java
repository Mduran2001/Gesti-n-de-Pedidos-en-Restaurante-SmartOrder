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

public class UsuarioView extends JPanel {
    public UsuarioView() {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(45, 40, 38, 40));
        setLayout(new BorderLayout(0, 24));

        JLabel title = new JLabel("Usuarios");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        add(title, BorderLayout.NORTH);

        String[] cols = {"Usuario", "Rol", "Estado"};
        Object[][] rows = {
            {"Ana López", "Cocinera", "Activo"},
            {"Carlos Ruiz", "Mesero", "Inactivo Ruiz"}, // Tal como sale en el Figma
            {"María Soto", "Cajera", "Activo"},
            {"Samantha Sanchez", "Administrativo", "Activo"},
            {"Monserrat Castillo", "Mesera", "Activo"},
            {"Mario Duran", "Cocinero", "Inactivo"},
            {"Andres Aguilar", "Administrativo", "Activo"}
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
