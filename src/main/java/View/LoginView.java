/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import javax.swing.*;
import java.awt.*;
import Controladores.AutenticacionController;
import Controladores.LoginController;
import Servicios.AutenticacionService;
import Servicios.LoginService;
import DaoImp.AccesoDaoImp;
import DaoImp.UsuarioDaoImp;
import Model.Usuario;

/**
 *
 * @author mario
 */

public class LoginView extends JFrame {

    public LoginView() {
        setTitle("Restaurante SmartOrder - Iniciar sesión");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 480);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel base = new JPanel(new GridBagLayout());
        base.setBackground(Color.WHITE);
        
        JPanel form = new JPanel();
        form.setBackground(Color.WHITE);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        
        JLabel title = new JLabel("Restaurante SmartOrder");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        form.add(title);
        form.add(Box.createVerticalStrut(30));
        
        form.add(label("Usuario"));
        JTextField user = new JTextField(20);
        user.setText("Ingrese su usuario...");
        user.setForeground(Color.GRAY);
        user.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (user.getText().equals("Ingrese su usuario...")) {
                    user.setText("");
                    user.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (user.getText().isEmpty()) {
                    user.setForeground(Color.GRAY);
                    user.setText("Ingrese su usuario...");
                }
            }
        });
        form.add(field(user));
        form.add(Box.createVerticalStrut(15));
        
        form.add(label("Contraseña"));
        JPasswordField pass = new JPasswordField(20);
        pass.setEchoChar((char) 0);
        pass.setText("Ingrese su contraseña...");
        pass.setForeground(Color.GRAY);
        pass.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (String.valueOf(pass.getPassword()).equals("Ingrese su contraseña...")) {
                    pass.setText("");
                    pass.setForeground(Color.BLACK);
                    pass.setEchoChar('•');
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (String.valueOf(pass.getPassword()).isEmpty()) {
                    pass.setForeground(Color.GRAY);
                    pass.setEchoChar((char) 0);
                    pass.setText("Ingrese su contraseña...");
                }
            }
        });
        form.add(field(pass));
        form.add(Box.createVerticalStrut(25));
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton login = new JButton("Iniciar Sesion");
        login.setBackground(Color.BLACK);
        login.setForeground(Color.WHITE);
        login.setFont(new Font("SansSerif", Font.BOLD, 14));
        login.setFocusPainted(false);
        login.setPreferredSize(new Dimension(140, 45));
        login.setMaximumSize(new Dimension(140, 45));
        
        JButton register = new JButton("Registrarse");
        register.setBackground(new Color(230, 230, 230));
        register.setForeground(Color.BLACK);
        register.setFont(new Font("SansSerif", Font.BOLD, 14));
        register.setFocusPainted(false);
        register.setPreferredSize(new Dimension(140, 45));
        register.setMaximumSize(new Dimension(140, 45));

        login.addActionListener(e -> {
            String u = user.getText().trim();
            String p = new String(pass.getPassword());
            if (u.isEmpty() || u.equals("Ingrese su correo...") || p.isEmpty() || p.equals("Ingrese su contraseña...")) {
                JOptionPane.showMessageDialog(this, "Ingrese usuario y contraseña.");
                return;
            }
            try {
                // 1. Verificar credenciales contra la base de datos
                LoginController loginController = new LoginController(
                        new LoginService(new UsuarioDaoImp()));
                Usuario usuario = loginController.iniciarSesion(u, p);
                pass.setText("");
                if (usuario == null) {
                    JOptionPane.showMessageDialog(this, "Credenciales incorrectas o usuario inactivo.");
                    return;
                }
                // 2. Registrar el ingreso (si falla el registro, no bloquea el acceso)
                try {
                    new AutenticacionController(
                            new AutenticacionService(new AccesoDaoImp()))
                            .registrarIngreso(usuario);
                } catch (RuntimeException exRegistro) {
                    exRegistro.printStackTrace();
                }
                new MainView().setVisible(true);
                dispose();
            } catch (RuntimeException ex) {
                Throwable causa = ex.getCause() != null ? ex.getCause() : ex;
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al iniciar sesión: " + causa.getMessage());
            }
        });

        register.addActionListener(e -> {
            new RegistroView(this).setVisible(true);
            this.setVisible(false);
        });

        buttonPanel.add(login);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(register);

        form.add(buttonPanel);
        base.add(form);
        setContentPane(base);
    }

    private JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.PLAIN, 15));
        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        return l;
    }

    private JComponent field(JTextField field) {
        field.setBackground(new Color(230, 230, 230));
        field.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setMaximumSize(new Dimension(300, 45));
        field.setPreferredSize(new Dimension(300, 45));
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        return field;
    }
}