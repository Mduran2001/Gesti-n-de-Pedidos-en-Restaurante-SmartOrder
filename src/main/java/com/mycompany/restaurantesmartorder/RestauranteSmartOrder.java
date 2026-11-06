/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.restaurantesmartorder;

import javax.swing.SwingUtilities;
import View.LoginView;

/**
 *
 * @author mario
 */
public class RestauranteSmartOrder {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));

    }
}
