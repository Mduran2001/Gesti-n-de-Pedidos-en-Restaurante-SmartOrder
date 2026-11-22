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

public class LoginView extends JFrame {

    public LoginView() {
        setTitle("Restaurante SmartOrder — Iniciar Sesión");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(460, 490);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel base = new JPanel(new GridBagLayout());
        base.setBackground(new Color(244, 246, 250));

        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 216, 226), 1, false),
                BorderFactory.createEmptyBorder(26, 28, 26, 28)
        ));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(370, 400));

        JLabel title = new JLabel("Restaurante SmartOrder");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(new Color(23, 28, 38));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(title);

        JLabel subtitle = new JLabel("Inicia sesión para continuar");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(new Color(100, 110, 128));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(Box.createVerticalStrut(4));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(24));

        JLabel lblCorreo = new JLabel("Correo electrónico");
        lblCorreo.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblCorreo.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblCorreo);
        card.add(Box.createVerticalStrut(6));

        JTextField user = new JTextField(22);
        user.setFont(new Font("SansSerif", Font.PLAIN, 14));
        user.setMaximumSize(new Dimension(320, 40));
        user.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(195, 203, 214), 1, false),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        user.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(user);
        card.add(Box.createVerticalStrut(14));

        JLabel lblPass = new JLabel("Contraseña");
        lblPass.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblPass.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblPass);
        card.add(Box.createVerticalStrut(6));

        JPasswordField pass = new JPasswordField(22);
        pass.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pass.setMaximumSize(new Dimension(320, 40));
        pass.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(195, 203, 214), 1, false),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        pass.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(pass);
        card.add(Box.createVerticalStrut(24));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton login = new JButton("Iniciar Sesión");
        login.setFont(new Font("SansSerif", Font.BOLD, 13));
        login.setBackground(new Color(37, 99, 235));
        login.setForeground(Color.WHITE);
        login.setFocusPainted(false);
        login.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));

        JButton register = new JButton("Registrarse");
        register.setFont(new Font("SansSerif", Font.BOLD, 13));
        register.setBackground(new Color(238, 242, 247));
        register.setForeground(new Color(23, 28, 38));
        register.setFocusPainted(false);
        register.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 206, 216), 1, false),
                BorderFactory.createEmptyBorder(9, 16, 9, 16)
        ));

        login.addActionListener(e -> {
            String u = user.getText().trim();
            String p = new String(pass.getPassword());
            if (u.isEmpty() || p.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingresa tu correo y contraseña.");
                return;
            }
            try {
                LoginController loginController = new LoginController(
                        new LoginService(new UsuarioDaoImp()));
                Usuario usuario = loginController.iniciarSesion(u, p);
                pass.setText("");
                if (usuario == null) {
                    JOptionPane.showMessageDialog(this, "Credenciales incorrectas o usuario inactivo.");
                    return;
                }
                UsuarioView.registrarUsuarioEnLista(usuario);
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
        buttonPanel.add(register);
        card.add(buttonPanel);

        base.add(card);
        setContentPane(base);
    }
}
