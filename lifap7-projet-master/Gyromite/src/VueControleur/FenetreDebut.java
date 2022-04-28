/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VueControleur;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import modele.plateau.Jeu;

public class FenetreDebut extends JFrame {

    private int niveau = 1;

    public FenetreDebut() {
        setTitle("Gyromite");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel contentPane = new JPanel();
        JLabel label = new JLabel("Niveau : ");
        contentPane.add(label);
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("# 1");
        comboBox.addItem("# 2");
        comboBox.addItem("# 3");
        contentPane.add(comboBox);
        add(contentPane, BorderLayout.SOUTH);

        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                niveau = comboBox.getSelectedIndex() + 1;
                System.out.println(niveau);
            }
        });

        JPanel panel = new JPanel();
        JLabel text = new JLabel("<html><body>"
                + "<p>1 joueur</p><br>"
                + "<p>Contrôle la direction via EDSF ou ↑↓←→,</p><br>"
                + "<p>Contrôle les Colonnes via G H ou 1 2 sur clavier numérique.</p><br>"
                + "<br>"
                + "<p>2 joueurs</p><br>"
                + "<p>Le premier joueur contrôle la direction via ↑↓←→</p><br>"
                + "<p>Le deuxieme joueur contrôle la direction via EDSF</p><br>"
                + "<p>Les colonnes bleus sont contrôlé par le permier joueur avec 1 sur clavier numérique.</p><br>"
                + "<p>Les colonnes rouges sont contrôlé par le deuxieme joueur avec 2 sur clavier numérique.</p><br>"
                + "<body></html>");
        panel.add(text);

        JButton jb1 = new JButton("1 player");
        panel.add(jb1);

        JButton jb2 = new JButton("2 player");
        panel.add(jb2);
        add(panel, BorderLayout.CENTER);
        jb1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Jeu jeu = new Jeu(200, 300, 5, niveau, 1);
                VueControleurGyromite vc = new VueControleurGyromite(jeu);
                jeu.getOrdonnanceur().addObserver(vc);
                vc.setVisible(true);
                jeu.start(300);
                dispose();
            }
        });
        jb2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Jeu jeu = new Jeu(200, 300, 5, niveau, 2);
                VueControleurGyromite vc = new VueControleurGyromite(jeu);
                jeu.getOrdonnanceur().addObserver(vc);
                vc.setVisible(true);
                jeu.start(300);
                dispose();
            }
        });

        setVisible(true);
    }
}
