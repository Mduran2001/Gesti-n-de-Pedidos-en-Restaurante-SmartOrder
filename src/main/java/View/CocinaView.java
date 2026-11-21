package View;

import View.PedidoView.PedidoRegistro;

import javax.swing.*;
import java.awt.*;

public class CocinaView extends JPanel {

    private final JPanel grid;

    public CocinaView() {
        setBackground(new Color(244, 246, 250));
        setBorder(BorderFactory.createEmptyBorder(28, 32, 28, 32));
        setLayout(new BorderLayout(0, 18));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel title = new JLabel("Cocina — Comandas en Tiempo Real");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(new Color(23, 28, 38));
        JLabel sub = new JLabel("Revisa el N° de pedido, mesa y detalle, y actualiza el estado a En preparación o Listo.");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 14));
        sub.setForeground(new Color(100, 110, 128));
        header.add(title, BorderLayout.NORTH);
        header.add(sub, BorderLayout.SOUTH);
        add(header, BorderLayout.NORTH);

        grid = new JPanel(new GridLayout(0, 3, 14, 14));
        grid.setOpaque(false);

        JScrollPane scroll = new JScrollPane(grid);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(new Color(244, 246, 250));
        add(scroll, BorderLayout.CENTER);

        PedidoView.addPedidoListener(this::refrescar);
        refrescar();
    }

    public void refrescar() {
        grid.removeAll();
        for (PedidoRegistro p : PedidoView.getPedidos()) {
            JPanel card = new JPanel(new BorderLayout(0, 10));
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(210, 216, 226), 1, false),
                    BorderFactory.createEmptyBorder(16, 16, 16, 16)
            ));

            JPanel topRow = new JPanel(new BorderLayout());
            topRow.setOpaque(false);

            JLabel lblEstado = new JLabel(p.estado.toUpperCase());
            lblEstado.setFont(new Font("SansSerif", Font.BOLD, 13));
            if ("Listo".equalsIgnoreCase(p.estado)) {
                lblEstado.setForeground(new Color(22, 163, 74));
            } else if ("En preparación".equalsIgnoreCase(p.estado)) {
                lblEstado.setForeground(new Color(217, 119, 6));
            } else {
                lblEstado.setForeground(new Color(37, 99, 235));
            }

            JLabel lblMesa = new JLabel(p.mesa > 0 ? "Mesa #" + p.mesa : p.tipo);
            lblMesa.setFont(new Font("SansSerif", Font.BOLD, 13));
            lblMesa.setForeground(new Color(23, 28, 38));

            topRow.add(lblEstado, BorderLayout.WEST);
            topRow.add(lblMesa, BorderLayout.EAST);
            card.add(topRow, BorderLayout.NORTH);

            JPanel body = new JPanel();
            body.setOpaque(false);
            body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

            JLabel lblCod = new JLabel("Pedido: " + p.codigo);
            lblCod.setFont(new Font("SansSerif", Font.BOLD, 16));
            lblCod.setForeground(new Color(23, 28, 38));

            JLabel lblCli = new JLabel("Cliente: " + p.cliente);
            lblCli.setFont(new Font("SansSerif", Font.PLAIN, 13));
            lblCli.setForeground(new Color(100, 110, 128));

            JLabel lblDet = new JLabel("<html><b>Detalle:</b> " + p.detalle + "</html>");
            lblDet.setFont(new Font("SansSerif", Font.PLAIN, 13));
            lblDet.setForeground(new Color(23, 28, 38));

            body.add(lblCod);
            body.add(Box.createVerticalStrut(4));
            body.add(lblCli);
            body.add(Box.createVerticalStrut(8));
            body.add(lblDet);
            card.add(body, BorderLayout.CENTER);

            JPanel btnRow = new JPanel(new GridLayout(1, 2, 8, 0));
            btnRow.setOpaque(false);

            JButton btnPrep = new JButton("En preparación");
            btnPrep.setFont(new Font("SansSerif", Font.BOLD, 12));
            btnPrep.setFocusPainted(false);
            btnPrep.setBackground(new Color(254, 243, 199));
            btnPrep.setForeground(new Color(146, 64, 14));
            btnPrep.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(217, 119, 6), 1, false),
                    BorderFactory.createEmptyBorder(8, 8, 8, 8)
            ));
            btnPrep.setEnabled(!"En preparación".equalsIgnoreCase(p.estado));
            btnPrep.addActionListener(e -> PedidoView.actualizarEstadoPedido(p.codigo, "En preparación"));

            JButton btnListo = new JButton("Marcar Listo");
            btnListo.setFont(new Font("SansSerif", Font.BOLD, 12));
            btnListo.setFocusPainted(false);
            btnListo.setBackground(new Color(220, 252, 231));
            btnListo.setForeground(new Color(20, 83, 45));
            btnListo.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(22, 163, 74), 1, false),
                    BorderFactory.createEmptyBorder(8, 8, 8, 8)
            ));
            btnListo.setEnabled(!"Listo".equalsIgnoreCase(p.estado));
            btnListo.addActionListener(e -> PedidoView.actualizarEstadoPedido(p.codigo, "Listo"));

            btnRow.add(btnPrep);
            btnRow.add(btnListo);
            card.add(btnRow, BorderLayout.SOUTH);

            grid.add(card);
        }
        grid.revalidate();
        grid.repaint();
    }
}
