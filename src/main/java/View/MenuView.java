package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.net.URL;

public class MenuView extends JPanel {

    private final Color COLOR_FONDO = new Color(244, 246, 250);
    private final Color COLOR_TARJETA = Color.WHITE;
    private final Color COLOR_BORDE = new Color(210, 216, 226);
    private final Color COLOR_HUECO_IMG = new Color(235, 238, 244);
    private final Color COLOR_TEXTO = new Color(23, 28, 38);
    private final Color COLOR_TEXTO_SEC = new Color(100, 110, 128);
    private final Color COLOR_ACENTO = new Color(37, 99, 235);

    public MenuView() {
        setBackground(COLOR_FONDO);
        setBorder(new EmptyBorder(28, 32, 28, 32));
        setLayout(new BorderLayout(0, 18));

        JLabel title = new JLabel("Menú del Día");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(COLOR_TEXTO);
        add(title, BorderLayout.NORTH);

        String[][] platos = {
            {"Pizza Pepperoni", "Masa artesanal, salsa de tomate y mozzarella", "₡7,900"},
            {"Hamburguesa Clásica", "Carne 180g, cheddar, vegetales y papas", "₡6,800"},
            {"Pasta Carbonara", "Fettuccine en salsa cremosa con tocineta", "₡7,400"},
            {"Filete de Pescado", "A la plancha con arroz, ensalada y patacones", "₡8,500"},
            {"Ensalada César", "Lechuga romana, pollo a la parrilla y crutones", "₡5,600"},
            {"Casado Típico", "Arroz, frijoles, ensalada, plátano y proteína", "₡6,200"}
        };

        String[] rutasImagenes = {
            "/Imagenes SmartOrder/images (1).jpg",
            "/Imagenes SmartOrder/images (2).jpg",
            "/Imagenes SmartOrder/images (3).jpg",
            "/Imagenes SmartOrder/images (4).jpg",
            "/Imagenes SmartOrder/images (5).jpg",
            "/Imagenes SmartOrder/images.jpg"
        };

        JPanel grid = new JPanel(new GridLayout(3, 2, 14, 14));
        grid.setOpaque(false);

        for (int i = 0; i < platos.length; i++) {
            String[] plato = platos[i];

            JPanel card = new JPanel(new BorderLayout(14, 0));
            card.setBackground(COLOR_TARJETA);
            card.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(COLOR_BORDE, 1, false),
                    new EmptyBorder(14, 14, 14, 14)
            ));

            JLabel lblImagen = new JLabel("IMAGEN", SwingConstants.CENTER);
            lblImagen.setFont(new Font("SansSerif", Font.BOLD, 11));
            lblImagen.setForeground(COLOR_TEXTO_SEC);
            lblImagen.setBackground(COLOR_HUECO_IMG);
            lblImagen.setOpaque(true);
            lblImagen.setPreferredSize(new Dimension(96, 86));
            lblImagen.setBorder(new LineBorder(COLOR_BORDE, 1, false));

            URL imgUrl = getClass().getResource(rutasImagenes[i]);
            if (imgUrl != null) {
                ImageIcon icon = new ImageIcon(imgUrl);
                Image imgEscalada = icon.getImage().getScaledInstance(96, 86, Image.SCALE_SMOOTH);
                lblImagen.setIcon(new ImageIcon(imgEscalada));
                lblImagen.setText("");
            }

            card.add(lblImagen, BorderLayout.WEST);

            JPanel info = new JPanel();
            info.setOpaque(false);
            info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

            JLabel lblNombre = new JLabel(plato[0]);
            lblNombre.setFont(new Font("SansSerif", Font.BOLD, 16));
            lblNombre.setForeground(COLOR_TEXTO);

            JLabel lblDesc = new JLabel("<html>" + plato[1] + "</html>");
            lblDesc.setFont(new Font("SansSerif", Font.PLAIN, 13));
            lblDesc.setForeground(COLOR_TEXTO_SEC);

            JLabel lblPrecio = new JLabel(plato[2]);
            lblPrecio.setFont(new Font("SansSerif", Font.BOLD, 14));
            lblPrecio.setForeground(COLOR_ACENTO);

            info.add(lblNombre);
            info.add(Box.createVerticalStrut(4));
            info.add(lblDesc);
            info.add(Box.createVerticalGlue());
            info.add(lblPrecio);

            card.add(info, BorderLayout.CENTER);
            grid.add(card);
        }

        add(grid, BorderLayout.CENTER);
    }
}