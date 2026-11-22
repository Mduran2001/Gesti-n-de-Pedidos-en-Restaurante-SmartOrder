package com.mycompany.restaurantesmartorder;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import View.LoginView;

public class RestauranteSmartOrder {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ignored) {
            }
            new LoginView().setVisible(true);
        });
    }
}
