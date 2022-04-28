/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VueControleur;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JPanel;


class PanelFin extends JPanel {
    private boolean aGagne;
    public PanelFin(boolean a) {
        aGagne = a;

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.insets = new Insets(0, 0, 30, 0);
        gbc.weightx = 0.2;
        gbc.weighty = 1;

        gbc.gridx = 1;

    }

    public void paintComponent(Graphics g) {

        String phrase;
        if (aGagne) {
            phrase = "Vous avez gagné!";
        } else {
            phrase = "Vous avez perdu!";
        }

        Font font = new Font("Courier", Font.BOLD, 20);
        g.setFont(font);
        g.setColor(Color.red);
        g.drawString(phrase, 100, 60);
    }

}