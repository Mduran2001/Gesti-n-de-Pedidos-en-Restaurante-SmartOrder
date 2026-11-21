package View;

import javax.swing.*;
import java.awt.*;
import DaoImp.UsuarioDaoImp;
import Model.Usuario;

public class RegistroView extends JFrame {

    private final LoginView loginView;

    public RegistroView(LoginView loginView) {
        this.loginView = loginView;

        setTitle("Restaurante SmartOrder — Registrar Usuario");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(480, 580);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel base = new JPanel(new GridBagLayout());
        base.setBackground(new Color(244, 246, 250));

        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 216, 226), 1, false),
                BorderFactory.createEmptyBorder(24, 28, 24, 28)
        ));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(380, 500));

        JLabel title = new JLabel("Registro de Usuario");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(new Color(23, 28, 38));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(title);
        card.add(Box.createVerticalStrut(18));

        JTextField nombre = addInput(card, "Nombre");
        JTextField apellido = addInput(card, "Apellido");
        JTextField user = addInput(card, "Correo electrónico");

        JLabel lblPass = new JLabel("Contraseña");
        lblPass.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblPass.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblPass);
        card.add(Box.createVerticalStrut(4));

        JPasswordField pass = new JPasswordField(20);
        pass.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pass.setMaximumSize(new Dimension(320, 38));
        pass.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(195, 203, 214), 1, false),
                BorderFactory.createEmptyBorder(7, 10, 7, 10)
        ));
        pass.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(pass);
        card.add(Box.createVerticalStrut(12));

        JLabel lblRol = new JLabel("Rol");
        lblRol.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblRol.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblRol);
        card.add(Box.createVerticalStrut(4));

        JComboBox<String> cmbRol = new JComboBox<>(new String[]{"Administrativo", "Mesero", "Cocinero", "Cajero"});
        cmbRol.setFont(new Font("SansSerif", Font.PLAIN, 14));
        cmbRol.setBackground(Color.WHITE);
        cmbRol.setMaximumSize(new Dimension(320, 38));
        cmbRol.setBorder(BorderFactory.createLineBorder(new Color(195, 203, 214), 1, false));
        cmbRol.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(cmbRol);
        card.add(Box.createVerticalStrut(20));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton register = new JButton("Crear Cuenta");
        register.setFont(new Font("SansSerif", Font.BOLD, 13));
        register.setBackground(new Color(37, 99, 235));
        register.setForeground(Color.WHITE);
        register.setFocusPainted(false);
        register.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));

        JButton back = new JButton("Volver");
        back.setFont(new Font("SansSerif", Font.BOLD, 13));
        back.setBackground(new Color(238, 242, 247));
        back.setForeground(new Color(23, 28, 38));
        back.setFocusPainted(false);
        back.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 206, 216), 1, false),
                BorderFactory.createEmptyBorder(9, 16, 9, 16)
        ));

        register.addActionListener(e -> {
            String nom = nombre.getText().trim();
            String ape = apellido.getText().trim();
            String correo = user.getText().trim();
            String clave = new String(pass.getPassword());

            if (nom.isEmpty() || ape.isEmpty() || correo.isEmpty() || clave.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }

            int idRol = cmbRol.getSelectedIndex() + 1;

            try {
                UsuarioDaoImp dao = new UsuarioDaoImp();

                if (dao.buscarPorCorreo(correo) != null) {
                    JOptionPane.showMessageDialog(this, "Ya existe un usuario con ese correo.");
                    return;
                }

                Usuario nuevo = new Usuario(0, idRol, nom, ape, correo, clave, "Activo");
                if (!dao.insertar(nuevo)) {
                    JOptionPane.showMessageDialog(this, "No se pudo guardar el usuario.");
                    return;
                }
                UsuarioView.registrarUsuarioEnLista(nuevo);
            } catch (RuntimeException ex) {
                Throwable causa = ex.getCause() != null ? ex.getCause() : ex;
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error de base de datos: " + causa.getMessage());
                return;
            }

            JOptionPane.showMessageDialog(this, "Usuario " + correo + " registrado como " + cmbRol.getSelectedItem() + " con éxito.");
            loginView.setVisible(true);
            this.dispose();
        });

        back.addActionListener(e -> {
            loginView.setVisible(true);
            this.dispose();
        });

        buttonPanel.add(register);
        buttonPanel.add(back);
        card.add(buttonPanel);

        base.add(card);
        setContentPane(base);
    }

    private JTextField addInput(JPanel parent, String labelText) {
        JLabel l = new JLabel(labelText);
        l.setFont(new Font("SansSerif", Font.BOLD, 13));
        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        parent.add(l);
        parent.add(Box.createVerticalStrut(4));

        JTextField tf = new JTextField(20);
        tf.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tf.setMaximumSize(new Dimension(320, 38));
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(195, 203, 214), 1, false),
                BorderFactory.createEmptyBorder(7, 10, 7, 10)
        ));
        tf.setAlignmentX(Component.CENTER_ALIGNMENT);
        parent.add(tf);
        parent.add(Box.createVerticalStrut(12));
        return tf;
    }
}
