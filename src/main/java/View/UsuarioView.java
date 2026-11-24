/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import DaoImp.UsuarioDaoImp;
import Model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author nacho
 */
public class UsuarioView extends JPanel {

    private static final List<Usuario> USUARIOS_SESION = new ArrayList<>();
    private final DefaultTableModel model;

    public UsuarioView() {
        setBackground(new Color(244, 246, 250));
        setBorder(BorderFactory.createEmptyBorder(28, 32, 28, 32));
        setLayout(new BorderLayout(0, 18));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JPanel titleBox = new JPanel();
        titleBox.setOpaque(false);
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Usuarios del Sistema");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(new Color(23, 28, 38));

        JLabel sub = new JLabel("Usuarios guardados en la base de datos y cuentas activas en sesión");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 14));
        sub.setForeground(new Color(100, 110, 128));

        titleBox.add(title);
        titleBox.add(Box.createVerticalStrut(4));
        titleBox.add(sub);
        header.add(titleBox, BorderLayout.WEST);

        JButton btnSync = new JButton("Actualizar desde BD");
        btnSync.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnSync.setBackground(new Color(238, 242, 247));
        btnSync.setForeground(new Color(23, 28, 38));
        btnSync.setFocusPainted(false);
        btnSync.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 206, 216), 1, false),
                BorderFactory.createEmptyBorder(8, 14, 8, 14)
        ));
        btnSync.addActionListener(e -> refrescar());
        header.add(btnSync, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        String[] cols = {"Usuario", "Rol", "Estado"};
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(38);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.setBackground(Color.WHITE);
        table.setGridColor(new Color(224, 229, 236));

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(224, 229, 236), 1, false),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(224, 229, 236), 1, false));
        scroll.getViewport().setBackground(Color.WHITE);
        card.add(scroll, BorderLayout.CENTER);

        add(card, BorderLayout.CENTER);
        refrescar();
    }

    public static void registrarUsuarioEnLista(Usuario u) {
        if (u == null) return;
        String correo = u.getCorreo() != null ? u.getCorreo().trim() : "";
        for (int i = 0; i < USUARIOS_SESION.size(); i++) {
            Usuario ex = USUARIOS_SESION.get(i);
            if (!correo.isEmpty() && ex.getCorreo() != null && correo.equalsIgnoreCase(ex.getCorreo().trim())) {
                USUARIOS_SESION.set(i, u);
                return;
            }
        }
        USUARIOS_SESION.add(u);
    }

    public static String obtenerNombreRol(int idRol) {
        switch (idRol) {
            case 1: return "Administrativo";
            case 2: return "Mesero";
            case 3: return "Cocinero";
            case 4: return "Cajero";
            default: return "Rol #" + idRol;
        }
    }

    public void refrescar() {
        model.setRowCount(0);
        try {
            List<Usuario> listaBD = new UsuarioDaoImp().listar();
            if (listaBD != null) {
                for (Usuario u : listaBD) {
                    registrarUsuarioEnLista(u);
                }
            }
        } catch (RuntimeException ignored) {
            // Si la BD no responde en este instante, muestra los usuarios registrados/autenticados en sesión
        }

        for (Usuario u : USUARIOS_SESION) {
            String nombre = ((u.getNombre() != null ? u.getNombre().trim() : "") + " "
                    + (u.getApellido() != null ? u.getApellido().trim() : "")).trim();
            String correo = u.getCorreo() != null ? u.getCorreo().trim() : "";
            String usuarioTexto = nombre.isEmpty() ? correo : (correo.isEmpty() ? nombre : nombre + " (" + correo + ")");
            String rolTexto = obtenerNombreRol(u.getIdRol());
            String estadoTexto = (u.getEstado() != null && !u.getEstado().trim().isEmpty()) ? u.getEstado().trim() : "Activo";
            model.addRow(new Object[]{usuarioTexto, rolTexto, estadoTexto});
        }
    }
}
