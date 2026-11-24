/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Controladores.MesaController;
import DaoImp.MesaDaoImp;
import Model.Mesa;
import Servicios.MesaService;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
/**
 *
 * @author nacho
 */
public class MesaView extends JPanel {

    public enum EstadoMesa {
        LIBRE("Libre"),
        RESERVADA("Reservada"),
        OCUPADA("Ocupada");

        private final String label;

        EstadoMesa(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

        public static EstadoMesa fromString(String raw) {
            if (raw == null) return LIBRE;
            String n = raw.trim().toUpperCase(Locale.ROOT);
            if (n.contains("OCUP") || n.equals("ROJO")) return OCUPADA;
            if (n.contains("RESER") || n.equals("AMARILLO")) return RESERVADA;
            return LIBRE;
        }
    }

    private static final Map<Integer, EstadoMesa> MESAS = new LinkedHashMap<>();
    private static final Map<Integer, Integer> CAPACIDADES = new LinkedHashMap<>();
    private static final List<Runnable> LISTENERS = new ArrayList<>();

    static {
        Set<Integer> busy = new HashSet<>(Arrays.asList(4, 8, 12, 13, 15, 20, 22));
        Set<Integer> reserved = new HashSet<>(Arrays.asList(9, 16, 17, 21));
        for (int i = 1; i <= 24; i++) {
            CAPACIDADES.put(i, (i % 3 == 0) ? 6 : (i % 2 == 0) ? 4 : 2);
            if (busy.contains(i)) {
                MESAS.put(i, EstadoMesa.OCUPADA);
            } else if (reserved.contains(i)) {
                MESAS.put(i, EstadoMesa.RESERVADA);
            } else {
                MESAS.put(i, EstadoMesa.LIBRE);
            }
        }
    }

    private final JPanel gridPanel = new JPanel(new GridLayout(4, 6, 14, 14));
    private final JLabel lblStats = new JLabel();
    private final JLabel lblInfo = new JLabel("Haz clic en una mesa para cambiar su estado (Verde: Libre, Amarillo: Reservada, Rojo: Ocupada).");
    private EstadoMesa brochaSeleccionada = null;

    public MesaView() {
        setBackground(new Color(244, 246, 250));
        setBorder(BorderFactory.createEmptyBorder(28, 32, 28, 32));
        setLayout(new BorderLayout(0, 18));

        JPanel topCard = crearTarjeta();
        topCard.setLayout(new BorderLayout(16, 12));

        JPanel titleRow = new JPanel(new BorderLayout());
        titleRow.setOpaque(false);
        JLabel title = new JLabel("Gestión de Mesas");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(new Color(23, 28, 38));
        titleRow.add(title, BorderLayout.WEST);

        lblStats.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblStats.setForeground(new Color(100, 110, 128));
        titleRow.add(lblStats, BorderLayout.EAST);
        topCard.add(titleRow, BorderLayout.NORTH);

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        controls.setOpaque(false);
        ButtonGroup bg = new ButtonGroup();

        JToggleButton btnSelector = crearToggle("Elegir estado al clic", Color.WHITE, new Color(180, 188, 200));
        btnSelector.setSelected(true);
        btnSelector.addActionListener(e -> {
            brochaSeleccionada = null;
            lblInfo.setText("Modo selector: haz clic en una mesa para elegir Verde (Libre), Amarillo (Reservada) o Rojo (Ocupada).");
        });

        JToggleButton btnVerde = crearToggle("Verde = Libre", new Color(220, 252, 231), new Color(22, 163, 74));
        btnVerde.addActionListener(e -> {
            brochaSeleccionada = EstadoMesa.LIBRE;
            lblInfo.setText("Brocha Verde (Libre): haz clic sobre cualquier mesa para marcarla como Libre.");
        });

        JToggleButton btnAmarillo = crearToggle("Amarillo = Reservada", new Color(254, 249, 195), new Color(202, 138, 4));
        btnAmarillo.addActionListener(e -> {
            brochaSeleccionada = EstadoMesa.RESERVADA;
            lblInfo.setText("Brocha Amarilla (Reservada): haz clic sobre cualquier mesa para marcarla como Reservada.");
        });

        JToggleButton btnRojo = crearToggle("Rojo = Ocupada", new Color(254, 226, 226), new Color(220, 38, 38));
        btnRojo.addActionListener(e -> {
            brochaSeleccionada = EstadoMesa.OCUPADA;
            lblInfo.setText("Brocha Roja (Ocupada): haz clic sobre cualquier mesa para marcarla como Ocupada.");
        });

        bg.add(btnSelector);
        bg.add(btnVerde);
        bg.add(btnAmarillo);
        bg.add(btnRojo);

        controls.add(btnSelector);
        controls.add(btnVerde);
        controls.add(btnAmarillo);
        controls.add(btnRojo);

        JButton btnSync = new JButton("Cargar desde BD");
        btnSync.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnSync.setBackground(new Color(238, 242, 247));
        btnSync.setForeground(new Color(23, 28, 38));
        btnSync.setFocusPainted(false);
        btnSync.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 206, 216), 1, false),
                BorderFactory.createEmptyBorder(7, 14, 7, 14)
        ));
        btnSync.addActionListener(e -> cargarMesasDesdeBD());
        controls.add(btnSync);

        topCard.add(controls, BorderLayout.CENTER);
        lblInfo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblInfo.setForeground(new Color(100, 110, 128));
        topCard.add(lblInfo, BorderLayout.SOUTH);

        add(topCard, BorderLayout.NORTH);

        JPanel boardCard = crearTarjeta();
        boardCard.setLayout(new BorderLayout());
        gridPanel.setOpaque(false);
        boardCard.add(gridPanel, BorderLayout.CENTER);
        add(boardCard, BorderLayout.CENTER);

        addMesaListener(this::refrescar);
        refrescar();
    }

    private JPanel crearTarjeta() {
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 216, 226), 1, false),
                BorderFactory.createEmptyBorder(16, 18, 16, 18)
        ));
        return p;
    }

    private JToggleButton crearToggle(String text, Color bg, Color borderColor) {
        JToggleButton tb = new JToggleButton(text);
        tb.setFont(new Font("SansSerif", Font.BOLD, 13));
        tb.setBackground(bg);
        tb.setForeground(new Color(23, 28, 38));
        tb.setFocusPainted(false);
        tb.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 2, false),
                BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));
        return tb;
    }

    public static void addMesaListener(Runnable r) {
        if (r != null && !LISTENERS.contains(r)) {
            LISTENERS.add(r);
        }
    }

    public static void setEstadoMesa(int numeroMesa, EstadoMesa estado) {
        if (MESAS.containsKey(numeroMesa) && estado != null) {
            MESAS.put(numeroMesa, estado);
            for (Runnable r : new ArrayList<>(LISTENERS)) {
                r.run();
            }
        }
    }

    public static int contarPorEstado(EstadoMesa estado) {
        int c = 0;
        for (EstadoMesa e : MESAS.values()) {
            if (e == estado) c++;
        }
        return c;
    }

    public void refrescar() {
        gridPanel.removeAll();
        for (Map.Entry<Integer, EstadoMesa> entry : MESAS.entrySet()) {
            int num = entry.getKey();
            EstadoMesa est = entry.getValue();
            int cap = CAPACIDADES.getOrDefault(num, 4);

            JButton btn = new JButton();
            btn.setLayout(new BorderLayout(0, 2));
            btn.setFocusPainted(false);
            btn.setOpaque(true);

            Color bg;
            Color border;
            Color fg;
            if (est == EstadoMesa.OCUPADA) {
                bg = new Color(254, 226, 226);
                border = new Color(220, 38, 38);
                fg = new Color(127, 29, 29);
            } else if (est == EstadoMesa.RESERVADA) {
                bg = new Color(254, 249, 195);
                border = new Color(202, 138, 4);
                fg = new Color(113, 63, 18);
            } else {
                bg = new Color(220, 252, 231);
                border = new Color(22, 163, 74);
                fg = new Color(20, 83, 45);
            }

            btn.setBackground(bg);
            btn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(border, 2, false),
                    BorderFactory.createEmptyBorder(8, 8, 8, 8)
            ));

            JLabel lblNum = new JLabel("Mesa " + num, SwingConstants.CENTER);
            lblNum.setFont(new Font("SansSerif", Font.BOLD, 15));
            lblNum.setForeground(fg);

            JLabel lblSub = new JLabel(est.getLabel() + " · " + cap + "p", SwingConstants.CENTER);
            lblSub.setFont(new Font("SansSerif", Font.BOLD, 11));
            lblSub.setForeground(fg);

            btn.add(lblNum, BorderLayout.CENTER);
            btn.add(lblSub, BorderLayout.SOUTH);
            btn.addActionListener(e -> clickMesa(num, est));
            gridPanel.add(btn);
        }

        lblStats.setText("Libres: " + contarPorEstado(EstadoMesa.LIBRE)
                + "  |  Reservadas: " + contarPorEstado(EstadoMesa.RESERVADA)
                + "  |  Ocupadas: " + contarPorEstado(EstadoMesa.OCUPADA));

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private void clickMesa(int numMesa, EstadoMesa actual) {
        if (brochaSeleccionada != null) {
            setEstadoMesa(numMesa, brochaSeleccionada);
            lblInfo.setText("Mesa " + numMesa + " marcada como " + brochaSeleccionada.getLabel() + ".");
            return;
        }
        String[] opciones = {"Verde — Libre", "Amarillo — Reservada", "Rojo — Ocupada"};
        int def = actual == EstadoMesa.LIBRE ? 0 : (actual == EstadoMesa.RESERVADA ? 1 : 2);
        int sel = JOptionPane.showOptionDialog(
                this,
                "Selecciona el estado para la Mesa #" + numMesa + "\nEstado actual: " + actual.getLabel(),
                "Cambiar Estado Mesa #" + numMesa,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[def]
        );
        if (sel == 0) setEstadoMesa(numMesa, EstadoMesa.LIBRE);
        else if (sel == 1) setEstadoMesa(numMesa, EstadoMesa.RESERVADA);
        else if (sel == 2) setEstadoMesa(numMesa, EstadoMesa.OCUPADA);
    }

    private void cargarMesasDesdeBD() {
        try {
            List<Mesa> lista = new MesaController(new MesaService(new MesaDaoImp())).listarMesas();
            if (lista != null && !lista.isEmpty()) {
                for (Mesa m : lista) {
                    int n = m.getNumero() > 0 ? m.getNumero() : m.getIdMesa();
                    if (n >= 1 && n <= 24) {
                        setEstadoMesa(n, EstadoMesa.fromString(m.getEstado()));
                    }
                }
                JOptionPane.showMessageDialog(this, "Mesas sincronizadas desde BD (" + lista.size() + " registros).");
            } else {
                JOptionPane.showMessageDialog(this, "No hay mesas registradas en la BD.");
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo consultar la BD: " + ex.getMessage());
        }
    }
}
