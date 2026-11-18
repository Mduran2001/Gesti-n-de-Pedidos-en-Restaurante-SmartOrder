/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.MatteBorder;
import DaoImp.UsuarioDaoImp;
import Model.Usuario;

/**
 *
 * @author espin
 */

public class RegistroView extends JFrame {
    
    private LoginView loginView;

    public RegistroView(LoginView loginView) {
        this.loginView = loginView;
        
        setTitle("Restaurante SmartOrder - Registrar Usuario");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 550);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel base = new JPanel(new GridBagLayout());
        base.setBackground(Color.WHITE);
        
        JPanel form = new JPanel();
        form.setBackground(Color.WHITE);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        
        JLabel title = new JLabel("Registro de Usuario");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        form.add(title);
        form.add(Box.createVerticalStrut(25));
        
        form.add(label("Nombre Completo"));
        JTextField nombre = new JTextField(20);
        form.add(field(nombre));
        form.add(Box.createVerticalStrut(15));

        form.add(label("Usuario"));
        JTextField user = new JTextField(20);
        form.add(field(user));
        form.add(Box.createVerticalStrut(15));
        
        form.add(label("Contraseña"));
        JPasswordField pass = new JPasswordField(20);
        form.add(field(pass));
        form.add(Box.createVerticalStrut(15));

        form.add(label("Rol"));
        JComboBox<String> cmbRol = new JComboBox<>(new String[]{"Administrativo", "Mesero", "Cocinero", "Cajero"});
        cmbRol.setBackground(new Color(230, 230, 230));
        cmbRol.setFont(new Font("SansSerif", Font.PLAIN, 14));
        cmbRol.setMaximumSize(new Dimension(300, 45));
        cmbRol.setPreferredSize(new Dimension(300, 45));
        cmbRol.setAlignmentX(Component.CENTER_ALIGNMENT);
        form.add(cmbRol);
        form.add(Box.createVerticalStrut(25));
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton register = new JButton("Crear Cuenta");
        register.setBackground(Color.BLACK);
        register.setForeground(Color.WHITE);
        register.setFont(new Font("SansSerif", Font.BOLD, 14));
        register.setFocusPainted(false);
        register.setPreferredSize(new Dimension(140, 45));
        register.setMaximumSize(new Dimension(140, 45));
        
        JButton back = new JButton("Volver");
        back.setBackground(new Color(230, 230, 230));
        back.setForeground(Color.BLACK);
        back.setFont(new Font("SansSerif", Font.BOLD, 14));
        back.setFocusPainted(false);
        back.setPreferredSize(new Dimension(140, 45));
        back.setMaximumSize(new Dimension(140, 45));

        register.addActionListener(e -> {
            String nombreCompleto = nombre.getText().trim();
            String correo = user.getText().trim();
            String clave = new String(pass.getPassword());

            if (nombreCompleto.isEmpty() || correo.isEmpty() || clave.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }

            // Separar "Nombre Completo" en nombre y apellido
            String[] partes = nombreCompleto.split("\\s+", 2);
            String nom = partes[0];
            String ape = partes.length > 1 ? partes[1] : "-";

            // El orden del combo debe coincidir con los id_rol de tu tabla "rol"
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
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(back);

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
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        field.setMaximumSize(new Dimension(300, 45));
        field.setPreferredSize(new Dimension(300, 45));
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        return field;
    }
}