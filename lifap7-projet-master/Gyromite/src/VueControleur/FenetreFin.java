/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VueControleur;

/**
 *
 * @author fatma
 */
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FenetreFin extends JFrame {

    private int timeUpdate, point, timer, nbBombe, nbBonus, pointMax;

    public FenetreFin(boolean aGagne, int tUpd, int nbB, int t, int p) {
        try {
            timeUpdate = tUpd;
            timer = t;
            nbBombe = nbB;
            point = p;
            URL url = this.getClass().getResource("/point.txt");
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            String s = "";
            while ((s = in.readLine()) != null) {
                pointMax = Integer.parseInt(s);
            }
            inputStream.close();
            byte[] sourceByte = null;
            if (pointMax < point) {
                pointMax = point;
                String sourceString = String.valueOf(pointMax);
                sourceByte = sourceString.getBytes("utf-8");
            }
            String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
            path = java.net.URLDecoder.decode(path, "UTF-8");
            if (sourceByte != null) {
                JarUtil.readJarFile(path, "point.txt");
                JarUtil.writeJarFile(path, "point.txt", sourceByte);
            }
        } catch (IOException ex) {
            Logger.getLogger(FenetreFin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(FenetreFin.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setTitle("Partie Terminée !");
        this.setSize(400, 200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PanelFin panel = new PanelFin(aGagne);
        JButton recommencer = new JButton("Recommencer");
        panel.add(recommencer);

        recommencer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
                    path = java.net.URLDecoder.decode(path, "UTF-8");
                    File file=new File(path);
                    Runtime.getRuntime().exec("java -jar "+file.getPath());
                } catch (IOException ex) {
                    Logger.getLogger(FenetreFin.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.exit(0);
            }
        });
        add(panel, BorderLayout.CENTER);

        JPanel panel2 = new JPanel();
        JLabel pointLable = new JLabel("Score : " + point * 10 + "     Meilleur score : " + pointMax * 10);
        panel2.add(pointLable);

        this.add(panel2, BorderLayout.PAGE_START);

        setVisible(true);
    }

    public int getTimeUpdate() {
        return timeUpdate;
    }

    public int getNbBombe() {
        return nbBombe;
    }

    public int getTimer() {
        return timer;
    }

    public int getPoint() {
        return point;
    }

    public int getNbBonus() {
        return nbBonus;
    }

}
