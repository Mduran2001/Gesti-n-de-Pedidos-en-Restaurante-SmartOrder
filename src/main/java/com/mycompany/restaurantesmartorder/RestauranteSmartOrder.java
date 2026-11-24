/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantesmartorder;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import View.LoginView;
/**
 *
 * @author mario
 */
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
