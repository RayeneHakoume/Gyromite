package VueControleur;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import modele.deplacements.*;
import modele.plateau.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;


/**
 * Cette classe a deux fonctions : (1) Vue : proposer une représentation
 * graphique de l'application (cases graphiques, etc.) (2) Controleur : écouter
 * les évènements clavier et déclencher le traitement adapté sur le modèle
 * (flèches direction Pacman, etc.))
 *
 */
public class VueControleurGyromite extends JFrame implements Observer {
    
    private Jeu jeu; // référence sur une classe de modèle : permet d'accéder aux données du modèle pour le rafraichissement, permet de communiquer les actions clavier (ou souris)

    private int sizeX; // taille de la grille affichée
    private int sizeY;

    // icones affichées dans la grille
    private ImageIcon icoHero;
    private ImageIcon icoVide;
    private ImageIcon icoMur;
    private ImageIcon icoColonneB;
    private ImageIcon icoColonneR;
    private ImageIcon icoCorde;
    private ImageIcon icoBomb;
    private ImageIcon icoSmick;
    private ImageIcon icoBonus;
    
    JLabel timerLabel, scoreLabel, viesLabel, botLabel, bombeLabel;
    private JLabel[][] tabJLabel; // cases graphique (au moment du rafraichissement, chaque case va être associée à une icône, suivant ce qui est présent dans le modèle)

    public VueControleurGyromite(Jeu _jeu) {
        sizeX = jeu.SIZE_X;
        sizeY = _jeu.SIZE_Y;
        jeu = _jeu;
        
        chargerLesIcones();
        placerLesComposantsGraphiques();
        ajouterEcouteurClavier();
        
    }
    
    public VueControleurGyromite(int sizeX_, int sizeY_) {
        
        sizeX = sizeX_;
        sizeY = sizeY_;
        
        chargerLesIcones();
        //placerLesComposantsGraphiques();
        ajouterEcouteurClavier();
    }
    
    public void setJeu(Jeu jeu_) {
        this.jeu = jeu_;
        placerLesComposantsGraphiques();
    }
    
    private void ajouterEcouteurClavier() {
        addKeyListener(new KeyAdapter() { // new KeyAdapter() { ... } est une instance de classe anonyme, il s'agit d'un objet qui correspond au controleur dans MVC
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {  // on regarde quelle touche a été pressée
                    case KeyEvent.VK_LEFT:
                        Controle4Directions.getInstance().setDirectionCourante(Direction.gauche, 1);
                        
                        break;
                    case KeyEvent.VK_RIGHT:
                        Controle4Directions.getInstance().setDirectionCourante(Direction.droite, 1);
                        break;
                    case KeyEvent.VK_DOWN:
                        Controle4Directions.getInstance().setDirectionCourante(Direction.bas, 1);
                        
                        break;
                    case KeyEvent.VK_UP:
                        Controle4Directions.getInstance().setDirectionCourante(Direction.haut, 1);
                        break;
                    case KeyEvent.VK_S:
                        if (jeu.nbHero == 1) {
                            Controle4Directions.getInstance().setDirectionCourante(Direction.gauche, 1);
                        } else if (jeu.nbHero == 2) {
                            Controle4Directions.getInstance().setDirectionCourante(Direction.gauche, 2);
                        }
                        break;
                    case KeyEvent.VK_F:
                        if (jeu.nbHero == 1) {
                            Controle4Directions.getInstance().setDirectionCourante(Direction.droite, 1);
                        } else if (jeu.nbHero == 2) {
                            Controle4Directions.getInstance().setDirectionCourante(Direction.droite, 2);
                        }
                        break;
                    case KeyEvent.VK_D:
                        if (jeu.nbHero == 1) {
                            Controle4Directions.getInstance().setDirectionCourante(Direction.bas, 1);
                        } else if (jeu.nbHero == 2) {
                            Controle4Directions.getInstance().setDirectionCourante(Direction.bas, 2);
                        }
                        break;
                    case KeyEvent.VK_E:
                        if (jeu.nbHero == 1) {
                            Controle4Directions.getInstance().setDirectionCourante(Direction.haut, 1);
                        } else if (jeu.nbHero == 2) {
                            Controle4Directions.getInstance().setDirectionCourante(Direction.haut, 2);
                        }
                        break;
                    case KeyEvent.VK_NUMPAD1:
                        ColonneBlue.getInstance().setColonneColor(Color.blue);
                        break; //blue
                    case KeyEvent.VK_NUMPAD2:
                        if (jeu.nbHero == 1) {
                            ColonneRed.getInstance().setColonneColor(Color.red);
                        } else if (jeu.nbHero == 2) {
                        }
                        break; //red
                    case KeyEvent.VK_G:
                        if (jeu.nbHero == 1) {
                            ColonneBlue.getInstance().setColonneColor(Color.blue);
                        } else if (jeu.nbHero == 2) {
                        }
                        break; //blue
                    case KeyEvent.VK_H:
                        ColonneRed.getInstance().setColonneColor(Color.red);
                        break; //red  
                }
            }
            
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_NUMPAD1:
                        if (jeu.nbHero == 1) {
                            ColonneBlue.getInstance().inverseColonne(Color.blue);
                        } else if (jeu.nbHero == 2) {
                            ColonneBlue.getInstance().inverseColonne(Color.blue);
                        }
                        break; //blue
                    case KeyEvent.VK_NUMPAD2:
                        if (jeu.nbHero == 1) {
                            ColonneRed.getInstance().inverseColonne(Color.red);
                        } else if (jeu.nbHero == 2) {
                        }
                        break; //red
                    case KeyEvent.VK_G:
                        if (jeu.nbHero == 1) {
                            ColonneBlue.getInstance().inverseColonne(Color.blue);
                        } else if (jeu.nbHero == 2) {
                        }
                        break; //blue
                    case KeyEvent.VK_H:
                        if (jeu.nbHero == 1) {
                            ColonneRed.getInstance().inverseColonne(Color.red);
                        } else if (jeu.nbHero == 2) {
                            ColonneRed.getInstance().inverseColonne(Color.red);
                        }
                        break; //red
                }
            }
        });
    }
    
    private void chargerLesIcones() {
        icoHero = chargerIcone("Images/Prof.png");
        icoVide = chargerIcone("Images/Vide.png");
        icoColonneR = chargerIcone("Images/ColonneR.png");
        icoColonneB = chargerIcone("Images/ColonneB.png");
        icoMur = chargerIcone("Images/Mur.png");
        icoCorde = chargerIcone("Images/Corde.png");
        icoBomb = chargerIcone("Images/Bomb.png");
        icoSmick = chargerIcone("Images/Smick.png");
        icoBonus = chargerIcone("Images/Bonus.png");
    }
    
    private ImageIcon chargerIcone(String urlIcone) {
        ImageIcon icon = new ImageIcon(this.getClass().getResource("/" + urlIcone));
        Image image = icon.getImage();
        return SwingUtil.createAutoAdjustIcon(image, false);
    }
    
    private void placerLesComposantsGraphiques() {
        setTitle("Gyromite");
        setSize(800, 500);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        //Point 

        c.gridx = 2;
        c.gridy = 0;
        c.weightx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, 5, 0, 20);
        scoreLabel = new JLabel("Score : ");
        
        panel.add(scoreLabel, c);

        //Bot
        c.gridx = 3;
        c.gridy = 0;
        c.weightx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, 5, 0, 20);
        botLabel = new JLabel("Ennemis Restant: ");
        
        panel.add(botLabel, c);

        //Bombes
        c.gridx = 4;
        c.gridy = 0;
        c.weightx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, 5, 0, 20);
        bombeLabel = new JLabel("Bombes Restant: ");
        
        panel.add(bombeLabel, c);

        //Vies
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.EAST;
        c.insets = new Insets(0, 5, 0, 20);
        
        viesLabel = new JLabel("Vies : ");
        panel.add(viesLabel, c);

        // Timer 
        c.gridx = 0;
        c.insets = new Insets(0, 5, 0, 20);
        timerLabel = new JLabel(Integer.toString(jeu.getTimer()));
        panel.add(timerLabel, c);

        //setResizable(false);    //Il n'est pas permis de redimensionner la fenêtre.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // permet de terminer l'application à la fermeture de la fenêtre
        JComponent grilleJLabels = new JPanel(new GridLayout(sizeY, sizeX)); // grilleJLabels va contenir les cases graphiques et les positionner sous la forme d'une grille
        // Grille//
        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 1;
        c.weightx = 1;
        c.gridwidth = 10;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        
        tabJLabel = new JLabel[sizeX][sizeY];
        
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                JLabel jlab = new JLabel();
                tabJLabel[x][y] = jlab; // on conserve les cases graphiques dans tabJLabel pour avoir un accès pratique à celles-ci (voir mettreAJourAffichage() )
                grilleJLabels.add(jlab);
            }
        }
        panel.add(grilleJLabels, c);
        
        add(panel);
        
    }
    
    private void afficherTimer() {
        int timer = jeu.getTimer();
        Color couleur;
        
        if (timer < 15) {
            couleur = Color.red;
        } else {
            couleur = Color.black;
        }
        
        timerLabel.setText(Integer.toString(jeu.getTimer() / 60) + ":" + Integer.toString(jeu.getTimer() % 60));
        timerLabel.setForeground(couleur);
    }
    
    private void afficherComposant() {
        afficherTimer();
        
        scoreLabel.setText("Score : " + jeu.getNbPoint() * 10);
        if (jeu.nbHero == 1) {
            viesLabel.setText("Vies P1 : " + jeu.getNbVies1());
        } else if (jeu.getNbHeroInit() == 2) {
            viesLabel.setText("Vies P1 : " + jeu.getNbVies1() + "   Vies P2 : " + jeu.getNbVies2());
        }
        
        botLabel.setText("Ennemis : " + jeu.getNbBot());
        
        bombeLabel.setText("Bombes : " + jeu.getNbBombe());
        
    }

    /**
     * Il y a une grille du côté du modèle ( jeu.getGrille() ) et une grille du
     * côté de la vue (tabJLabel)
     */
    private void mettreAJourAffichage() {
        
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                if (jeu.getGrille()[x][y] instanceof Heros) { // si la grille du modèle contient un Pacman, on associe l'icône Pacman du côté de la vue
                    tabJLabel[x][y].setIcon(icoHero);
                } else if (jeu.getGrille()[x][y] instanceof Bot) { // si la grille du modèle contient un Pacman, on associe l'icône Pacman du côté de la vue
                    tabJLabel[x][y].setIcon(icoSmick);
                } else if (jeu.getGrille()[x][y] instanceof Mur) {
                    tabJLabel[x][y].setIcon(icoMur);
                } else if (jeu.getGrille()[x][y] instanceof Colonne) {
                    if (((Colonne) jeu.getGrille()[x][y]).getColonneColor() == Color.blue) {
                        tabJLabel[x][y].setIcon(icoColonneB);
                    } else if (((Colonne) jeu.getGrille()[x][y]).getColonneColor() == Color.red) {
                        tabJLabel[x][y].setIcon(icoColonneR);
                    }
                } else if (jeu.getGrille()[x][y] instanceof Corde) {
                    tabJLabel[x][y].setIcon(icoCorde);
                } else if (jeu.getGrille()[x][y] instanceof Bombe) {
                    tabJLabel[x][y].setIcon(icoBomb);
                } else if (jeu.getGrille()[x][y] instanceof Bonus) {
                    tabJLabel[x][y].setIcon(icoBonus);
                } else {
                    tabJLabel[x][y].setIcon(icoVide);
                }
            }
        }
        afficherComposant();
        
    }
    
    @Override
    public void update(Observable o, Object arg) {
        
        mettreAJourAffichage();
        
        if (jeu.aGagner() || jeu.aTermine()) {
            dispose();
        }

        /* SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        mettreAJourAffichage();
              
                }}); 
         */
    }
}

    
//}
