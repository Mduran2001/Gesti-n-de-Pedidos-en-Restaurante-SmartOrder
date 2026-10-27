/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantesmartorder.view;

import javax.swing.*;
import java.awt.*;
/**
 *
 * @author mario
 */

public class LoginView extends JFrame {
    public LoginView() {
        setTitle("Restaurante SmartOrder - Iniciar sesión");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1050, 720);
        setLocationRelativeTo(null);
        JPanel base = new JPanel(new GridBagLayout());
        base.setBackground(Color.WHITE);
        JPanel form = new JPanel();
        form.setBackground(Color.WHITE);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Restaurante SmartOrder");
        title.setFont(new Font("SansSerif", Font.BOLD, 25));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(title); form.add(Box.createVerticalStrut(28));
        form.add(label("Usuario"));
        JTextField user = new JTextField(22);
        form.add(field(user)); form.add(Box.createVerticalStrut(20));
        form.add(label("Contraseña"));
        JPasswordField pass = new JPasswordField(22);
        form.add(field(pass)); form.add(Box.createVerticalStrut(25));
        JButton login = new JButton("Iniciar Sesión");
        login.setBackground(Color.BLACK); login.setForeground(Color.WHITE);
        login.setFocusPainted(false); login.setAlignmentX(Component.CENTER_ALIGNMENT);
        login.setPreferredSize(new Dimension(190, 46)); login.setMaximumSize(new Dimension(190, 46));
        login.addActionListener(e -> {
            if (user.getText().trim().isEmpty() || pass.getPassword().length == 0) {
                JOptionPane.showMessageDialog(this,"Ingrese usuario y contraseña."); return;
            }
            new MainView().setVisible(true); dispose();
        });
        form.add(login); base.add(form); setContentPane(base);
    }
    private JLabel label(String text) {
        JLabel l = new JLabel(text); l.setFont(new Font("SansSerif",Font.PLAIN,15));
        l.setAlignmentX(Component.LEFT_ALIGNMENT); return l;
    }
    private JComponent field(JTextField field) {
        field.setBackground(new Color(220,220,220)); field.setBorder(BorderFactory.createEmptyBorder(10,12,10,12));
        field.setMaximumSize(new Dimension(300,48)); field.setPreferredSize(new Dimension(300,48));
        field.setAlignmentX(Component.LEFT_ALIGNMENT); return field;
    }
}

